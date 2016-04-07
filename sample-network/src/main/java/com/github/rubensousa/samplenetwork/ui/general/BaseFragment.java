package com.github.rubensousa.samplenetwork.ui.general;

import android.os.Bundle;


import com.github.rubensousa.amvp.view.MvpSupportFragment;

import icepick.Icepick;


public abstract class BaseFragment<V extends BaseView<P>, P extends BasePresenter<V>>
        extends MvpSupportFragment<V, P> implements BaseView<P> {

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

}
