package com.github.rubensousa.samplenetwork.ui.general;

import android.support.annotation.Nullable;

import com.github.rubensousa.amvp.Presenter;
import java.util.Map;

public interface BasePresenter<V extends BaseView> extends Presenter<V> {

    void attachMvpCallback(String key, MvpCallback mvpCallback);

    Map<String, MvpCallback> getMvpCallbacks();

    @Nullable
    MvpCallback getMvpCallback(String key);

    boolean isTaskFinished(String key);

    boolean isTaskExecuting(String key);
}
