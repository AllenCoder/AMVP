package com.github.rubensousa.amvp;


public interface MvpView<P extends Presenter> {

    /**
     * Get key used to cache the presenter
     * @return string that identifies the presenter
     */
    String getPresenterKey();

    P getPresenter();

    P createPresenter();

    /**
     * Called when the presenter is ready to be used after being fetched from the cache
     * or created for the first time
     *
     * @param presenter presenter created or fetched from the cache
     */
    void setPresenter(P presenter);
}
