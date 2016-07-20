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

public interface MvpPresenter<V extends MvpView> {

    /**
     * Called right after the presenter is created.
     * This happens before onViewAttach, so View will be null.
     *
     * @param savedInstanceState Bundle that contains the previous saved state
     */
    void onCreate(@Nullable Bundle savedInstanceState);

    /**
     * Should be called only when the fragment or activity's views aren't null
     * Life cycle callbacks that should call this:
     * onPostCreate
     * onViewStateRestored
     * onResume
     * onActivityResult
     * @param view View to be attached
     */
    void onViewAttach(V view);

    /**
     * Should be called when the fragment or activity is going to be paused/destroyed
     * onPause
     * onDestroy
     */
    void onViewDetach();

    /**
     * This is directly tied to fragment's and activity's onSaveInstanceState
     *
     * @param outState Bundle in which to place your saved state.
     */
    void onSaveInstanceState(@NonNull Bundle outState);

    /**
     * Called after onViewAttach and when all views have been initialized.
     * To avoid NPEs due to null views, this should be called from the fragment's onViewStateRestored
     * or activity's onPostCreate
     *
     * @param savedInstanceState Previous saved state
     */
    void onViewStateRestored(@Nullable Bundle savedInstanceState);

    /**
     * Should be called when this presenter isn't being used anymore
     * It's directly tied to the activity onBackPressed and finish methods
     */
    void onDestroy();


    boolean isViewAttached();

    /**
     * @return View attached to Presenter
     */
    @Nullable
    V getView();
}