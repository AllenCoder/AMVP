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

package com.github.rubensousa.amvp.cache;


import android.support.annotation.Nullable;

import com.github.rubensousa.amvp.MvpPresenter;

import java.util.LinkedHashMap;
import java.util.Map;

public class PresenterCache {

    private static PresenterCache sPresenterCache;

    private Map<String, MvpPresenter<?>> mCache;

    protected PresenterCache() {
        mCache = new LinkedHashMap<>();
    }

    public static PresenterCache getInstance() {
        if (sPresenterCache == null) {
            synchronized (PresenterCache.class) {
                sPresenterCache = new PresenterCache();
            }
        }
        return sPresenterCache;
    }

    public synchronized void cache(String key, MvpPresenter<?> presenter) {
        MvpPresenter old = mCache.put(key, presenter);
        if (old != null && old != presenter) {
            old.onDestroy();
        }
    }

    public synchronized void remove(String key) {
        remove(key, true);
    }

    public synchronized void remove(String key, boolean destroy) {

        MvpPresenter presenter = mCache.remove(key);

        if (destroy && presenter != null) {
            presenter.onDestroy();
        }
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public <P extends MvpPresenter<?>> P get(String key) {
        MvpPresenter presenter = mCache.get(key);

        if (presenter == null) {
            return null;
        }

        try {
            return (P) presenter;
        } catch (ClassCastException e) {
            return null;
        }
    }

    public synchronized void clear(boolean destroy) {
        if (destroy) {
            for (MvpPresenter p : mCache.values()) {
                p.onDestroy();
            }
        }

        mCache.clear();
    }

}
