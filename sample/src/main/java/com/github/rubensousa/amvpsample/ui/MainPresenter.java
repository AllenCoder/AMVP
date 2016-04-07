package com.github.rubensousa.amvpsample.ui;

import com.github.rubensousa.amvp.Presenter;


public interface MainPresenter extends Presenter<MainView> {
    void generateNumber();
}
