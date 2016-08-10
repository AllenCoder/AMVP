/*
 * Copyright 2016 Rúben Sousa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.rubensousa.amvp;


import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.github.rubensousa.amvp.cache.PresenterCache;
import com.github.rubensousa.amvp.utils.EspressoIdlingResource;
import com.github.rubensousa.amvp.view.TestActivity;
import com.github.rubensousa.amvp.utils.AndroidTestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ActivityTest {

    @Rule
    public ActivityTestRule<TestActivity> mTestRule
            = new ActivityTestRule<>(TestActivity.class, true, false);

    @Before
    public void register() {
        mTestRule.launchActivity(new Intent());
        AndroidTestUtils.registerIdlingResource();
    }

    @Test
    public void presenterCreation() {
        TestActivity activity = mTestRule.getActivity();

        assertTrue(activity.getPresenter() != null);

        // Check if the presenter was cached
        assertTrue(PresenterCache.getInstance().get(activity.getPresenterKey()) != null);
    }

    @Test
    public void presenterCache() {

        TestActivity activity = mTestRule.getActivity();

        // Test activity recreation by launching a new one,
        // since otherwise we're stuck with the same instance,
        // and createPresenter would still be true
        Intent intent = activity.getIntent();
        mTestRule.launchActivity(intent);

        // The app will be busy now and we make it idle again on onCreate
        EspressoIdlingResource.increment();
        activity = mTestRule.getActivity();

        // onCreate was called

        // check if presenter wasn't created
        assertTrue(!activity.createdPresenter());

        // check if presenter isn't null and was fetched from the cache
        assertTrue(activity.getPresenter() != null);
    }

    @After
    public void clean() {
        AndroidTestUtils.unregisterIdlingResource();
    }
}
