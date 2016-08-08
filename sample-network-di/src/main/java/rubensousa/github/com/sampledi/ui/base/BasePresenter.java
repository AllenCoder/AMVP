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

package rubensousa.github.com.sampledi.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.rubensousa.amvp.AbstractPresenter;

import icepick.Icepick;


public abstract class BasePresenter<V extends Base.View> extends AbstractPresenter<V>
        implements Base.Presenter<V> {


    private Base.Interactor mInteractor;

    public BasePresenter(Base.Interactor interactor) {
        mInteractor = interactor;
    }

    @Override
    public void onViewAttach(V view) {
        super.onViewAttach(view);
        if (mInteractor != null) {
            mInteractor.setViewAttached(true);
        }
    }

    @Override
    public void onViewDetach() {
        super.onViewDetach();
        if (mInteractor != null) {
            mInteractor.setViewAttached(false);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    // Override to avoid warnings
    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public V getView() {
        return super.getView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mInteractor != null) {
            mInteractor.onDestroy();
        }
    }
}
