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

package com.github.rubensousa.amvp.dialogfragment;

import com.github.rubensousa.amvp.MvpPresenter;
import com.github.rubensousa.amvp.utils.AndroidTestUtils;
import com.github.rubensousa.amvp.view.MvpAppCompatDialogFragment;


public class TestDialogFragment extends MvpAppCompatDialogFragment {

    public static final String TAG = "testDialogFragment";

    private boolean mCreatedPresenter;

    @Override
    public void onPause() {
        super.onPause();
        if (!AndroidTestUtils.getIdlingResource(this).isIdleNow()) {
            AndroidTestUtils.getIdlingResource(this).decrement();
        }
    }

    @Override
    public MvpPresenter createPresenter() {
        mCreatedPresenter = true;
        return new DialogPresenter();
    }

    public boolean createdPresenter(){
        return mCreatedPresenter;
    }

}
