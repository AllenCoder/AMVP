package com.github.rubensousa.amvpsample;


import android.app.Application;

import com.github.rubensousa.amvp.cache.PresenterCache;

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PresenterCache.init(PresenterCache.CACHE_SINGLETON);
    }
}
