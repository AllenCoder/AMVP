package com.github.rubensousa.amvp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract class AbstractPresenterImpl<V extends MvpView> implements Presenter<V> {

    private V mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public void onViewStateRestored(@NonNull Bundle savedInstanceState) {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Nullable
    @Override
    public V getView() {
        return mView;
    }
}
