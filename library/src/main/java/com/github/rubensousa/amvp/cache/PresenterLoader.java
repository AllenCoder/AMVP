package com.github.rubensousa.amvp.cache;

import android.content.Context;
import android.content.Loader;

import com.github.rubensousa.amvp.MvpView;
import com.github.rubensousa.amvp.Presenter;


public class PresenterLoader<V extends MvpView<P>, P extends Presenter<V>> extends Loader<P> {

    public static final int LOADER_ID = 1780;
    private V mView;
    private P mPresenter;

    public PresenterLoader(Context context, V mvpView) {
        super(context);
        mView = mvpView;
    }

    @Override
    protected void onStartLoading() {

        if (mPresenter != null) {
            deliverResult(mPresenter);
            return;
        }

        forceLoad();
    }

    @Override
    protected void onForceLoad() {
        mPresenter = mView.createPresenter();
        deliverResult(mPresenter);
    }

    @Override
    protected void onReset() {
        mPresenter = null;
    }
}
