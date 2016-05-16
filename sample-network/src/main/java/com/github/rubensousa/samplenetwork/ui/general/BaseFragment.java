package com.github.rubensousa.samplenetwork.ui.general;

import android.os.Bundle;


import com.github.rubensousa.amvp.view.MvpSupportFragment;

import icepick.Icepick;


public abstract class BaseFragment<V extends Base.View<P>, P extends Base.Presenter<V>>
        extends MvpSupportFragment<V, P> implements Base.View<P> {

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
