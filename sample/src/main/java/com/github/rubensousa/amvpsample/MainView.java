package com.github.rubensousa.amvpsample;


import com.github.rubensousa.amvp.MvpView;

public interface MainView extends MvpView<MainPresenter> {

    void showText(String text);
}
