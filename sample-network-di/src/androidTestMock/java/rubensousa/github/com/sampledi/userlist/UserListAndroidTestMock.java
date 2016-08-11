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
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import rubensousa.github.com.sampledi.R;
import rubensousa.github.com.sampledi.ui.userlist.UserActivity;
import rubensousa.github.com.sampledi.utils.AndroidTestUtils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserListAndroidTestMock {


    @Rule
    public ActivityTestRule<UserActivity> mActivityTestRule =
            new ActivityTestRule<>(UserActivity.class, false, false);

    @Before
    public void setup(){
        mActivityTestRule.launchActivity(new Intent());
        AndroidTestUtils.registerIdlingResource(mActivityTestRule);
    }

    @Test
    public void load() {
        // Since we're mocking the interactor, the activity will load the items immediately
        // and we can check if the items are showing right away
        onView(AndroidTestUtils.withItemText("Google")).check(matches(isDisplayed()));
        onView(AndroidTestUtils.withItemText("Facebook")).check(matches(isDisplayed()));
    }

    @Test
    public void delete() {
        onView(withId(R.id.action_delete)).perform(ViewActions.click());
        onView(AndroidTestUtils.withItemText("Google")).check(doesNotExist());
        onView(AndroidTestUtils.withItemText("Facebook")).check(doesNotExist());
    }

    @Test
    public void refresh() {
        // Call delete to make sure the items are set again
        onView(withId(R.id.action_delete)).perform(ViewActions.click());
        onView(withId(R.id.swipeRefreshLayout)).perform(ViewActions.swipeDown());
        onView(AndroidTestUtils.withItemText("Google")).check(matches(isDisplayed()));
        onView(AndroidTestUtils.withItemText("Facebook")).check(matches(isDisplayed()));
    }

    @After
    public void clearResources(){
        AndroidTestUtils.unregisterIdlingResource(mActivityTestRule);
    }

}
