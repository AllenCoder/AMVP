package com.github.rubensousa.amvp.cache;


import android.support.annotation.Nullable;

import com.github.rubensousa.amvp.Presenter;

import java.util.LinkedHashMap;
import java.util.Map;

public class PresenterCache {

    public static final int CACHE_LOADERS = 0;
    public static final int CACHE_SINGLETON = 1;

    private static PresenterCache sPresenterCache;

    private Map<String, Presenter<?>> mCache;
    private int mCachingMethod = CACHE_SINGLETON;

    protected PresenterCache(int method) {
        mCache = new LinkedHashMap<>();
        mCachingMethod = method;
    }

    public synchronized static PresenterCache init(int method) {
        if (method != CACHE_LOADERS && method != CACHE_SINGLETON) {
            throw new IllegalArgumentException("Use CACHE_LOADERS or CACHE_SINGLETON");
        }
        sPresenterCache = new PresenterCache(method);
        return sPresenterCache;
    }

    public static PresenterCache getInstance() {
        if (sPresenterCache == null) {
            synchronized (PresenterCache.class) {
                sPresenterCache = new PresenterCache(CACHE_SINGLETON);
            }
        }
        return sPresenterCache;
    }

    public int getCachingMethod() {
        return mCachingMethod;
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
