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

package com.github.rubensousa.amvp.delegate;

import android.os.Bundle;

import com.github.rubensousa.amvp.MvpPresenter;
import com.github.rubensousa.amvp.MvpView;
import com.github.rubensousa.amvp.cache.PresenterCache;


public class MvpDelegateImpl<V extends MvpView<P>, P extends MvpPresenter<V>> implements MvpDelegate<V, P> {

    private PresenterCache mPresenterCache;
    private MvpDelegateCallbacks<V, P> mCallbacks;
    private P mPresenter;
    private V mView;

    public MvpDelegateImpl(MvpDelegateCallbacks<V, P> callbacks) {
        mCallbacks = callbacks;
        mView = mCallbacks.getMvpView();
        mPresenterCache = PresenterCache.getInstance();
    }

    @Override
    public P onCreate(Bundle savedInstanceState) {
        String key = mView.getPresenterKey();
        mPresenter = mPresenterCache.get(key);

        if (mPresenter == null) {
            mPresenter = mCallbacks.createPresenter();
            if (mPresenter != null) {
                mPresenter.onCreate(savedInstanceState);
                mPresenterCache.cache(key, mPresenter);
            }
        } else {
            mPresenterCache.cache(key, mPresenter);
        }

        return mPresenter;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mPresenter != null) {
            mPresenter.onSaveInstanceState(outState);
            if (mPresenter.isViewAttached()) {
                mPresenter.onViewDetach();
            }
        }
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        if (mPresenter != null) {
            if (!mPresenter.isViewAttached()) {
                mPresenter.onViewAttach(mView);
            }
            mPresenter.onViewStateRestored(savedInstanceState);
        }
    }

    @Override
    public void attachView() {
        if (mPresenter != null && !mPresenter.isViewAttached()) {
            mPresenter.onViewAttach(mView);
        }
    }

    @Override
    public void detachView() {
        if (mPresenter != null && mPresenter.isViewAttached()) {
            mPresenter.onViewDetach();
        }
    }

    @Override
    public void destroyPresenter() {
        if (mPresenter != null) {
            mPresenterCache.remove(mView.getPresenterKey());
        }
    }
}
