/*
 * Copyright 2016 Rúben Sousa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.rubensousa.amvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.github.rubensousa.amvp.MvpView;
import com.github.rubensousa.amvp.Presenter;
import com.github.rubensousa.amvp.cache.PresenterCache;


public abstract class MvpSupportFragment<V extends MvpView<P>, P extends Presenter<V>> extends Fragment
        implements MvpView<P> {

    private PresenterCache mPresenterCache;
    private P mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenterCache = PresenterCache.getInstance();

        // Presenter may be not null if setRetainInstance(true) was called
        if (mPresenter != null) {
            mPresenterCache.cache(getPresenterKey(), mPresenter);
        } else {
            mPresenter = mPresenterCache.get(getPresenterKey());
            if (mPresenter == null) {
                mPresenter = createPresenter();
                mPresenter.onCreate(savedInstanceState);
                mPresenterCache.cache(getPresenterKey(), mPresenter);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mPresenter != null) {
            mPresenter.onSaveInstanceState(outState);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
            mPresenter.onViewStateRestored(savedInstanceState);
        }
    }

    /**
     * Since onDestroy() isn't guaranteed to be called,
     * we check if the current activity is finishing
     */
    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.detachView();
            if (getActivity().isFinishing()) {
                mPresenter.onDestroy();
                mPresenterCache.remove(getPresenterKey());
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPresenter != null) {
            mPresenter.detachView();
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
}
