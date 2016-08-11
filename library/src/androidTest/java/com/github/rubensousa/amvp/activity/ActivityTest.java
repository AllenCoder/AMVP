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

package com.github.rubensousa.amvp.activity;


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

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ActivityTest {

    private SimpleCountingIdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<TestActivity> mTestRule
            = new ActivityTestRule<>(TestActivity.class, true, false);

    @Before
    public void setup() {
        mTestRule.launchActivity(new Intent());
        mIdlingResource = AndroidTestUtils.getIdlingResource(mTestRule);
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void presenterCreation() {
        TestActivity activity = mTestRule.getActivity();

        // Check if presenter was created
        assertTrue(activity.getPresenter() != null);

        // Check if the presenter was cached
        assertTrue(PresenterCache.getInstance().get(activity.getPresenterKey()) != null);
    }

    @Test
    public void presenterCaching() {

        final TestActivity activity = mTestRule.getActivity();

        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                activity.recreate();
                mIdlingResource.increment();
            }
        });

        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        // onPostCreate was called, so we can get the recreated activity
        TestActivity recreatedActivity
                = (TestActivity) ActivityCache.get(activity.getPresenterKey());

        // check if presenter wasn't created
        assertTrue(!recreatedActivity.createdPresenter());

        // check if presenter isn't null and was fetched from the cache
        assertNotNull(recreatedActivity.getPresenter());
    }

    @Test
    public void presenterStateSaving() {
        final TestActivity activity = mTestRule.getActivity();
        ActivityPresenter presenter = (ActivityPresenter) activity.getPresenter();

        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                activity.recreate();
                mIdlingResource.increment();
            }
        });

        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        assertNotNull(presenter.getSavedState());
    }

    @Test
    public void presenterStateRestoring() {
        final TestActivity activity = mTestRule.getActivity();
        ActivityPresenter presenter = (ActivityPresenter) activity.getPresenter();

        int id = 5;

        // Set an id to be saved
        presenter.setId(id);

        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                activity.recreate();
                mIdlingResource.increment();
            }
        });

        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        // Check if the id was restored
        assertEquals(presenter.getId(), id);
    }

    @Test
    public void presenterDestruction() {
        TestActivity activity = mTestRule.getActivity();
        ActivityPresenter presenter = (ActivityPresenter) activity.getPresenter();
        activity.finish();

        // Check if it was destroyed
        assertTrue(presenter.isDestroyed());

        // Check if it doesn't exist in the cache
        assertNull(PresenterCache.getInstance().get(activity.getPresenterKey()));
    }

    @After
    public void clean() {
        Espresso.unregisterIdlingResources(mIdlingResource);
    }
}
