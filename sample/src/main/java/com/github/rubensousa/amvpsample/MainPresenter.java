package com.github.rubensousa.amvpsample;

import com.github.rubensousa.amvp.AbstractPresenter;

import java.util.Random;


public class MainPresenter extends AbstractPresenter<Main.View> implements Main.Presenter {

    private Random mRandom;

    public MainPresenter() {
        mRandom = new Random();
    }

    @Override
    public void generateNumber() {
        if (getView() != null) { //isViewAttached() doesn't suppress the warning
            getView().showText((mRandom.nextInt(10) + 1) + "");
        }
    }

}
