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

package com.github.rubensousa.amvp.utils;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import com.github.rubensousa.amvp.cache.PresenterCache;
import com.github.rubensousa.amvp.view.TestActivity;


public class AndroidTestUtils {

    public static void uncachePresenter(ActivityTestRule<TestActivity> rule) {
        PresenterCache.getInstance().remove(rule.getActivity().getPresenterKey());
    }

    public static void unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(EspressoIdlingResource.getIdlingResource());
    }

    public static void registerIdlingResource() {
        Espresso.registerIdlingResources(EspressoIdlingResource.getIdlingResource());
    }
}
