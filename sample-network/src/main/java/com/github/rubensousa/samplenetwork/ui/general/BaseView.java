package com.github.rubensousa.samplenetwork.ui.general;


import com.github.rubensousa.amvp.MvpView;

public interface BaseView<P extends BasePresenter> extends MvpView<P> {
    void showProgressDialog();

    void hideProgressDialog();
}
