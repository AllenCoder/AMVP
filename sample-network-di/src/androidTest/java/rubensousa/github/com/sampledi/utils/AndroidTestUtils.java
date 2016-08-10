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

package rubensousa.github.com.sampledi.utils;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.github.rubensousa.amvp.cache.PresenterCache;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import rubensousa.github.com.sampledi.ui.base.BaseActivity;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;


public class AndroidTestUtils {

    public static Matcher<View> withItemText(final String itemText) {
        if (itemText == null) {
            throw new IllegalArgumentException("itemText can't be null");
        }

        if (TextUtils.isEmpty(itemText)) {
            throw new IllegalArgumentException("itemText can't be empty");
        }

        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View item) {
                return allOf(isDescendantOfA(isAssignableFrom(RecyclerView.class)),
                        withText(itemText)).matches(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is isDescendantOfA RV with text " + itemText);
            }
        };
    }

    public static <T extends BaseActivity> void uncachePresenter(ActivityTestRule<T> rule) {
        PresenterCache.getInstance().remove(rule.getActivity().getPresenterKey());
    }

    public static <T extends BaseActivity> void unregisterIdlingResource(ActivityTestRule<T> rule) {
        Espresso.unregisterIdlingResources(rule.getActivity().getCountingIdlingResource());
    }

    public static <T extends BaseActivity> void registerIdlingResource(ActivityTestRule<T> rule) {
        Espresso.registerIdlingResources(rule.getActivity().getCountingIdlingResource());
    }
}
