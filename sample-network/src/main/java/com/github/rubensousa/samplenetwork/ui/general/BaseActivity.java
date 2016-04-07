package com.github.rubensousa.samplenetwork.ui.general;

import android.app.ProgressDialog;
import android.os.Bundle;


import com.github.rubensousa.amvp.view.MvpAppCompatActivity;

import icepick.Icepick;

public abstract class BaseActivity<V extends BaseView<P>, P extends BasePresenter<V>>
        extends MvpAppCompatActivity<V, P> implements BaseView<P> {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog = ProgressDialog.show(this, null, "Loading...", true);
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
