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

package com.github.rubensousa.amvp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract class AbstractPresenter<V extends MvpView> implements MvpPresenter<V> {

    private V mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onViewAttach(V view) {
        mView = view;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onViewDetach() {
        mView = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    @Nullable
    @Override
    public V getView() {
        return mView;
    }

    @Override
    public boolean isViewAttached() {
        return mView != null;
    }
}
