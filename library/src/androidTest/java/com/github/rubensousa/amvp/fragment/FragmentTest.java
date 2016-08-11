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

package com.github.rubensousa.amvp.fragment;


import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.github.rubensousa.amvp.cache.PresenterCache;
import com.github.rubensousa.amvp.utils.ActivityCache;
import com.github.rubensousa.amvp.utils.AndroidTestUtils;
import com.github.rubensousa.amvp.utils.SimpleCountingIdlingResource;
import com.github.rubensousa.amvp.activity.TestActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class FragmentTest {

    private SimpleCountingIdlingResource mActivityIdlingResource;
    private SimpleCountingIdlingResource mFragmentIdlingResource;

    @Rule
    public ActivityTestRule<TestActivity> mTestRule
            = new ActivityTestRule<>(TestActivity.class, true, false);

    @Before
    public void register() {
        Intent intent = new Intent();
        intent.putExtra(TestActivity.CREATE_FRAGMENT, true);
        mTestRule.launchActivity(intent);
        mActivityIdlingResource = AndroidTestUtils.getIdlingResource(mTestRule);
    }

    @Test
    public void presenterCreation() {
        TestActivity activity = mTestRule.getActivity();

        TestFragment fragment = (TestFragment) activity.getSupportFragmentManager()
                .findFragmentByTag(TestFragment.TAG);

        // Check if the presenter was created
        assertTrue(fragment.getPresenter() != null);

        // Check if the presenter was cached
        assertTrue(PresenterCache.getInstance().get(fragment.getPresenterKey()) != null);
    }

    @Test
    public void presenterCaching() {

        final TestActivity activity = mTestRule.getActivity();

        // Test fragment recreation by recreating the activity
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                activity.recreate();
                mActivityIdlingResource.increment();
            }
        });

        // Wait for activity recreation
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        // Get the recreated activity
        TestActivity recreatedActivity
                = (TestActivity) ActivityCache.get(activity.getPresenterKey());

        TestFragment fragment = (TestFragment) recreatedActivity.getSupportFragmentManager()
                .findFragmentByTag(TestFragment.TAG);

        // fragment's onCreate was called

        // check if presenter wasn't created
        assertTrue(!fragment.createdPresenter());

        // check if presenter isn't null and was fetched from the cache
        assertTrue(fragment.getPresenter() != null);
    }

    @Test
    public void presenterDestroy() {
        // Fragment presenters should be destroyed and uncached if the activity is finishing

        TestActivity activity = mTestRule.getActivity();

        TestFragment fragment = (TestFragment) activity.getSupportFragmentManager()
                .findFragmentByTag(TestFragment.TAG);

        activity.finish();

        // We must wait until fragment's onPause is called
        mFragmentIdlingResource = AndroidTestUtils.getIdlingResource(fragment);
        mFragmentIdlingResource.increment();

        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        assertTrue(PresenterCache.getInstance().get(fragment.getPresenterKey()) == null);
    }

    @After
    public void clean() {
        if (mFragmentIdlingResource != null) {
            Espresso.unregisterIdlingResources(mActivityIdlingResource);
        }

        if (mActivityIdlingResource != null) {
            Espresso.unregisterIdlingResources(mActivityIdlingResource);
        }

        ActivityCache.clear();
    }
}
