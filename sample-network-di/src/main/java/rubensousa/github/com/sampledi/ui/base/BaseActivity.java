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

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;

import com.github.rubensousa.amvp.view.MvpAppCompatActivity;

import icepick.Icepick;
import rubensousa.github.com.sampledi.ui.base.di.FlavorComponent;
import rubensousa.github.com.sampledi.ui.base.di.presenter.PresenterComponent;
import rubensousa.github.com.sampledi.utils.EspressoIdlingResource;

public abstract class BaseActivity<V extends Base.View<P>, P extends Base.Presenter<V>>
        extends MvpAppCompatActivity<V, P> implements Base.View<P> {

    private ProgressDialog mProgressDialog;
    private PresenterComponent mPresenterComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenterComponent = FlavorComponent.createPresenterComponent();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenterComponent = null;
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog = ProgressDialog.show(this, null, "Loading...", true);
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public PresenterComponent getPresenterComponent() {
        return mPresenterComponent;
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }

}
