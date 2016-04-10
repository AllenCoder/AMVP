package com.github.rubensousa.amvp.cache;


import android.support.annotation.Nullable;

import com.github.rubensousa.amvp.Presenter;

import java.util.LinkedHashMap;
import java.util.Map;

public class PresenterCache {

    private static PresenterCache sPresenterCache;

    private Map<String, Presenter<?>> mCache;

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

    public synchronized void cache(String key, Presenter<?> presenter) {
        mCache.put(key, presenter);
    }

    public synchronized void remove(String key) {
        mCache.remove(key);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public <P extends Presenter<?>> P get(String key) {
        Presenter presenter = mCache.get(key);

        if (presenter == null) {
            return null;
        }

        try {
            return (P) presenter;
        } catch (ClassCastException e) {
            return null;
        }
    }

    public synchronized void clear() {
        mCache.clear();
    }


}
