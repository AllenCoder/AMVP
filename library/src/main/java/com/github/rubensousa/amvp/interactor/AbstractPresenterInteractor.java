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

package com.github.rubensousa.amvp.interactor;


import com.github.rubensousa.amvp.AbstractPresenter;
import com.github.rubensousa.amvp.MvpView;

public abstract class AbstractPresenterInteractor<V extends MvpView, I extends MvpInteractor>
        extends AbstractPresenter<V> implements MvpPresenterInteractor<V, I> {

    private I mInteractor;

    public AbstractPresenterInteractor() {
        mInteractor = createInteractor();
    }

    @Override
    public I getInteractor() {
        return mInteractor;
    }

    @Override
    public void setInteractor(I interactor) {
        mInteractor = interactor;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mInteractor != null) {
            mInteractor.onDestroy();
        }
    }
}
