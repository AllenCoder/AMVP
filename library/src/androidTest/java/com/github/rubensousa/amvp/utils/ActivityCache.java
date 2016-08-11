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

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

// Used to get the new activity reference after the previous one has been recreated
public class ActivityCache {

    private static Map<String, Activity> sCache = new HashMap<>();

    public static Activity get(String key){
        return sCache.get(key);
    }

    public static void add(String key, Activity activity) {
        sCache.put(key, activity);
    }

    public static void clear() {
        sCache.clear();
    }

}
