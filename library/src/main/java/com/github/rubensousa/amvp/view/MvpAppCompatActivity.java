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
import android.support.v7.app.AppCompatActivity;

import com.github.rubensousa.amvp.MvpPresenter;
import com.github.rubensousa.amvp.MvpView;
import com.github.rubensousa.amvp.cache.PresenterCache;
import com.github.rubensousa.amvp.delegate.MvpDelegate;
import com.github.rubensousa.amvp.delegate.MvpDelegateCallbacks;
import com.github.rubensousa.amvp.delegate.MvpDelegateImpl;

import java.util.List;

public abstract class MvpAppCompatActivity<V extends MvpView<P>, P extends MvpPresenter<V>>
        extends AppCompatActivity implements MvpView<P>, MvpDelegateCallbacks<V, P> {

    private MvpDelegate<V, P> mDelegate;
    private P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate = new MvpDelegateImpl<>(this);
        mPresenter = mDelegate.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mDelegate.onSaveInstanceState(outState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDelegate.onViewStateRestored(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDelegate.detachView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDelegate.attachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mDelegate.attachView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDelegate.attachView();
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
        mDelegate.destroyPresenter();
        super.finish();
    }

    @Override
    public void onBackPressed() {
        mDelegate.destroyPresenter();
        super.onBackPressed();
    }

    @SuppressWarnings("unchecked")
    @Override
    public V getMvpView() {
        return (V) this;
    }
}
