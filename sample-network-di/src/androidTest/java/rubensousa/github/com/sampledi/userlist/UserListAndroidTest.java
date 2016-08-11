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

package rubensousa.github.com.sampledi.userlist;


import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import rubensousa.github.com.sampledi.R;
import rubensousa.github.com.sampledi.ui.userlist.UserActivity;
import rubensousa.github.com.sampledi.utils.EspressoIdlingResource;
import rubensousa.github.com.sampledi.utils.SimpleCountingIdlingResource;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserListAndroidTest {
    private SimpleCountingIdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<UserActivity> mActivityTestRule =
            new ActivityTestRule<>(UserActivity.class, true, false);

    @Before
    public void setup() {
        mActivityTestRule.launchActivity(new Intent());
        mIdlingResource = (SimpleCountingIdlingResource) EspressoIdlingResource.getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void load() {
        UserActivity activity = mActivityTestRule.getActivity();

        SwipeRefreshLayout swipeRefreshLayout
                = (SwipeRefreshLayout) activity.findViewById(R.id.swipeRefreshLayout);

        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView);

        // Check if swipe refresh layout stopped refreshing
        assertTrue(!swipeRefreshLayout.isRefreshing());

        // Check if the recycler view was loaded with items
        assertTrue(recyclerView.getAdapter().getItemCount() > 0);
    }

    @Test
    public void refresh() {
        UserActivity activity = mActivityTestRule.getActivity();

        SwipeRefreshLayout swipeRefreshLayout
                = (SwipeRefreshLayout) activity.findViewById(R.id.swipeRefreshLayout);

        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView);

        // delete items
        onView(withId(R.id.action_delete)).perform(ViewActions.click());

        // request refresh
        onView(withId(R.id.swipeRefreshLayout)).perform(ViewActions.swipeDown());

        // Check if swipe refresh layout stopped refreshing
        assertTrue(!swipeRefreshLayout.isRefreshing());

        // Check if the recycler view was loaded with items
        assertTrue(recyclerView.getAdapter().getItemCount() > 0);
    }

    @After
    public void clearResources() {
        Espresso.unregisterIdlingResources(mIdlingResource);
    }


}
