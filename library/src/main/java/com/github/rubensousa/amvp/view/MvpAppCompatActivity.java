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
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.github.rubensousa.amvp.MvpView;
import com.github.rubensousa.amvp.Presenter;
import com.github.rubensousa.amvp.cache.PresenterCache;

public abstract class MvpAppCompatActivity<V extends MvpView<P>, P extends Presenter<V>>
        extends AppCompatActivity implements MvpView<P>{

    private PresenterCache mPresenterCache;
    private P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenterCache = PresenterCache.getInstance();
        mPresenter = mPresenterCache.get(getPresenterKey());
        if (mPresenter == null) {
            mPresenter = createPresenter();
            mPresenter.onCreate(savedInstanceState);
            mPresenterCache.cache(getPresenterKey(), mPresenter);
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
