package com.github.rubensousa.samplenetwork.ui.general;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.rubensousa.amvp.AbstractPresenterImpl;

import java.util.LinkedHashMap;
import java.util.Map;

import icepick.Icepick;


public abstract class BasePresenterImpl<V extends Base.View> extends AbstractPresenterImpl<V>
        implements Base.Presenter<V>, MvpCallback.OnFinishedListener {

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
        boolean alreadyAttached = isViewAttached();
        super.attachView(view);
        if (!alreadyAttached) {
            for (MvpCallback callback : mMvpCallbacks.values()) {
                callback.attachView(view);
            }
        }
    }

    @Override
    public void detachView() {
        boolean alreadyDettached = !isViewAttached();
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
        // Remove callback registered with the same key
        MvpCallback old = mMvpCallbacks.get(key);

        if (old != null) {
            old.onDestroy();
            mMvpCallbacks.remove(key);
        }

        mvpCallback.setKey(key);
        mvpCallback.setOnFinishedListener(this);
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

    @Override
    public void onFinished(MvpCallback mvpCallback) {
        synchronized (this) {
            mMvpCallbacks.remove(mvpCallback.getKey());
        }
    }
}
