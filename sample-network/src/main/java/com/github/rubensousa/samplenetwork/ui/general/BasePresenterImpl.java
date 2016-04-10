package com.github.rubensousa.samplenetwork.ui.general;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.rubensousa.amvp.AbstractPresenterImpl;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import icepick.Icepick;


public abstract class BasePresenterImpl<V extends BaseView> extends AbstractPresenterImpl<V>
        implements BasePresenter<V> {

    private Map<String, MvpCallback> mMvpCallbacks;

    public BasePresenterImpl() {
        mMvpCallbacks = new LinkedHashMap<>();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void attachView(V view) {
        boolean alreadyAttached = getView() != null;
        super.attachView(view);
        if (!alreadyAttached) {
            for (MvpCallback callback : mMvpCallbacks.values()) {
                callback.attachView(view);
            }
        }
    }

    @Override
    public void detachView() {
        boolean alreadyDettached = getView() == null;
        super.detachView();
        if (!alreadyDettached) {
            for (MvpCallback callback : mMvpCallbacks.values()) {
                callback.detachView();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (MvpCallback callback : mMvpCallbacks.values()) {
            callback.onDestroy();
        }
    }

    @Override
    public void attachMvpCallback(String key, MvpCallback mvpCallback) {
        Iterator<MvpCallback> iterator = mMvpCallbacks.values().iterator();
        while (iterator.hasNext()) {
            MvpCallback callback = iterator.next();
            String callbackKey = callback.getKey();
            // Remove callbacks registered with the same key or that already finished
            if (callback.isCallFinished() || (callbackKey != null && callbackKey.equals(key))) {
                callback.onDestroy();
                iterator.remove();
            }
        }
        mvpCallback.setKey(key);
        mMvpCallbacks.put(key, mvpCallback);
    }

    @Override
    public Map<String, MvpCallback> getMvpCallbacks() {
        return mMvpCallbacks;
    }

    @Override
    public MvpCallback getMvpCallback(String key) {
        return mMvpCallbacks.get(key);
    }

    @Override
    public boolean isTaskFinished(String key) {
        MvpCallback callback = mMvpCallbacks.get(key);
        return callback != null && callback.isCallFinished();
    }

    @Override
    public boolean isTaskExecuting(String key) {
        MvpCallback callback = mMvpCallbacks.get(key);
        return callback != null && callback.isCallEnqueued() && !callback.isCallFinished();
    }
}
