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

package com.github.rubensousa.amvp.activity;


import android.os.Bundle;

import com.github.rubensousa.amvp.MvpPresenter;
import com.github.rubensousa.amvp.dialogfragment.TestDialogFragment;
import com.github.rubensousa.amvp.utils.ActivityCache;
import com.github.rubensousa.amvp.utils.EspressoIdlingResource;
import com.github.rubensousa.amvp.view.MvpAppCompatActivity;
import com.github.rubensousa.amvp.fragment.TestFragment;


public class TestActivity extends MvpAppCompatActivity {

    public static final String CREATE_FRAGMENT = "create_fragment";
    public static final String CREATE_DIALOG_FRAGMENT = "create_dialog_fragment";

    private boolean mCreatedPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            if (getIntent().getBooleanExtra(CREATE_FRAGMENT, false)) {
                getSupportFragmentManager().beginTransaction()
                        .add(new TestFragment(), TestFragment.TAG)
                        .commit();
            } else if (getIntent().getBooleanExtra(CREATE_DIALOG_FRAGMENT, false)) {
                getSupportFragmentManager().beginTransaction()
                        .add(new TestDialogFragment(), TestDialogFragment.TAG)
                        .commit();
            }
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ActivityCache.add(getPresenterKey(), this);
        if (savedInstanceState != null) {
            if (!EspressoIdlingResource.getIdlingResource(getPresenterKey()).isIdleNow()) {
                EspressoIdlingResource.getIdlingResource(getPresenterKey()).decrement();
            }
        }
    }

    @Override
    public MvpPresenter createPresenter() {
        mCreatedPresenter = true;
        return new ActivityPresenter();
    }

    public boolean createdPresenter() {
        return mCreatedPresenter;
    }
}
