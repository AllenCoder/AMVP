package com.github.rubensousa.samplenetwork.ui.general;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.rubensousa.amvp.AbstractPresenterImpl;


import icepick.Icepick;


public abstract class BasePresenter<V extends Base.View, I extends Base.Interactor>
        extends AbstractPresenterImpl<V> implements Base.Presenter<V, I> {

    private I mInteractor;

    @SuppressWarnings("unchecked")
    public BasePresenter() {
        mInteractor = createInteractor();
        if (mInteractor != null) {
            mInteractor.setPresenter(this);
        }
    }

    @Override
    public void onViewAttach(V view) {
        super.onViewAttach(view);
        if (mInteractor != null) {
            mInteractor.setViewAttached(true);
        }
    }

    @Override
    public void onViewDetach() {
        super.onViewDetach();
        if (mInteractor != null) {
            mInteractor.setViewAttached(false);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    // Override to avoid warnings
    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public V getView() {
        return super.getView();
    }

    @Override
    public I getInteractor() {
        return mInteractor;
    }

    @Override
    public I createInteractor() {
        return null;
    }
}
