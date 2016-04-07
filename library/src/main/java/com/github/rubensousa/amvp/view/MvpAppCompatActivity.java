package com.github.rubensousa.amvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.github.rubensousa.amvp.MvpView;
import com.github.rubensousa.amvp.Presenter;
import com.github.rubensousa.amvp.cache.PresenterCache;
import com.github.rubensousa.amvp.cache.PresenterSupportLoader;

public abstract class MvpAppCompatActivity<V extends MvpView<P>, P extends Presenter<V>>
        extends AppCompatActivity implements MvpView<P>, LoaderManager.LoaderCallbacks<P> {

    private PresenterCache mPresenterCache;
    private P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenterCache = PresenterCache.getInstance();
        if (mPresenterCache.getCachingMethod() == PresenterCache.CACHE_LOADERS) {
            getSupportLoaderManager().initLoader(PresenterSupportLoader.LOADER_ID, null, this);
        } else {
            mPresenter = mPresenterCache.get(getPresenterKey());
            if (mPresenter == null) {
                mPresenter = createPresenter();
                mPresenter.onCreate(savedInstanceState);
                mPresenterCache.cache(getPresenterKey(), mPresenter);
            }
            onPresenterPrepared(mPresenter);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mPresenter != null) {
            mPresenter.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mPresenter != null && savedInstanceState != null) {
            mPresenter.onViewStateRestored(savedInstanceState);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public String getPresenterKey() {
        return getClass().getSimpleName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Loader<P> onCreateLoader(int id, Bundle args) {
        return new PresenterSupportLoader<>(this, (V) this);
    }

    @Override
    public void onLoadFinished(Loader<P> loader, P data) {
        mPresenter = data;
        if (mPresenter != null) {
            onPresenterPrepared(mPresenter);
        }
    }

    @Override
    public void onLoaderReset(Loader<P> loader) {
        mPresenter = null;
    }

    @Override
    public void finish() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenterCache.remove(getPresenterKey());
        }
        super.finish();
    }

    @Override
    public void onBackPressed() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenterCache.remove(getPresenterKey());
        }
        super.onBackPressed();
    }
}
