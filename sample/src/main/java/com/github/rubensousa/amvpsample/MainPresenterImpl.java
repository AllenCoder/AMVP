package com.github.rubensousa.amvpsample;

import com.github.rubensousa.amvp.AbstractPresenterImpl;

import java.util.Random;


public class MainPresenterImpl extends AbstractPresenterImpl<MainView> implements MainPresenter {


    private Random mRandom;

    public MainPresenterImpl() {
        mRandom = new Random();
    }

    @Override
    public void generateNumber() {
        if (getView() != null) {
            getView().showText((mRandom.nextInt(10) + 1) + "");
        }
    }

}
