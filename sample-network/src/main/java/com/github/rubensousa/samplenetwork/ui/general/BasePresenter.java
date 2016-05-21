package com.github.rubensousa.samplenetwork.ui.general;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.rubensousa.amvp.interactor.AbstractPresenterInteractor;


import icepick.Icepick;


public abstract class BasePresenter<V extends Base.View, I extends Base.Interactor>
        extends AbstractPresenterInteractor<V, I> implements Base.Presenter<V, I> {

    @Override
    public void onViewAttach(V view) {
        super.onViewAttach(view);
        if (getInteractor() != null) {
            getInteractor().setViewAttached(true);
        }
    }

    @Override
    public void onViewDetach() {
        super.onViewDetach();
        if (getInteractor() != null) {
            getInteractor().setViewAttached(false);
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
}
