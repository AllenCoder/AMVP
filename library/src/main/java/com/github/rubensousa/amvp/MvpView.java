package com.github.rubensousa.amvp;


public interface MvpView<P extends Presenter> {

    /**
     * Get key used to cache the presenter
     * @return string that identifies the presenter
     */
    String getPresenterKey();

    P getPresenter();

    P createPresenter();
}
