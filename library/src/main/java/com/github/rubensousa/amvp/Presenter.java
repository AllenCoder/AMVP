package com.github.rubensousa.amvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface Presenter<T extends MvpView> {

    /**
     * Called right after the presenter is created.
     * This happens before attachView, so View will be null.
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
    void attachView(T view);

    /**
     * Should be called when the fragment or activity is going to be recreated
     * onPause
     * onDestroy
     * onActivityResult
     */
    void detachView();

    /**
     * This is directly tied to fragment's and activity's onSaveInstanceState
     *
     * @param outState Bundle in which to place your saved state.
     */
    void onSaveInstanceState(@NonNull Bundle outState);

    /**
     * Called after attachView and when all views have been initialized.
     * To avoid NPEs, this should be called from the fragment's onViewStateRestored
     * or activity's onPostCreate
     *
     * @param savedInstanceState Previous saved state
     */
    void onViewStateRestored(@NonNull Bundle savedInstanceState);

    /**
     * Should be called when this presenter isn't being used anymore
     * It's directly tied to the activity onBackPressed and finish methods
     */
    void onDestroy();

    @Nullable
    T getView();
}