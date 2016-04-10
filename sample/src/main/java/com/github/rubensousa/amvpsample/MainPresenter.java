package com.github.rubensousa.amvpsample;

import com.github.rubensousa.amvp.Presenter;


public interface MainPresenter extends Presenter<MainView> {
    void generateNumber();
}
