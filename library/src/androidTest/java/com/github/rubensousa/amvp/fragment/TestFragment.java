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

package com.github.rubensousa.amvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.github.rubensousa.amvp.MvpPresenter;
import com.github.rubensousa.amvp.utils.AndroidTestUtils;
import com.github.rubensousa.amvp.view.MvpSupportFragment;
import com.github.rubensousa.amvp.dialogfragment.TestDialogFragment;


public class TestFragment extends MvpSupportFragment {

    public static final String TAG = "testFragment";

    private boolean mCreatePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            if (!AndroidTestUtils.getIdlingResource(this).isIdleNow()) {
                AndroidTestUtils.getIdlingResource(this).decrement();
            }
        }
    }

    @Override
    public MvpPresenter createPresenter() {
        mCreatePresenter = true;
        return new FragmentPresenter();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!AndroidTestUtils.getIdlingResource(this).isIdleNow()) {
            AndroidTestUtils.getIdlingResource(this).decrement();
        }
    }

    public boolean createdPresenter() {
        return mCreatePresenter;
    }
}
