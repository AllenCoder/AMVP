package com.github.rubensousa.amvpsample.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;

import com.github.rubensousa.amvp.AbstractPresenterImpl;

import java.util.Random;


public class MainPresenterImpl extends AbstractPresenterImpl<MainView> implements MainPresenter {


    private Random mRandom;
    private MainView mView;

    public MainPresenterImpl() {
        mRandom = new Random();
    }

    @Override
    public void onViewStateRestored(@NonNull Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void attachView(MainView view) {
        super.attachView(view);
        mView = view;
    }

    @Override
    public void detachView() {
        super.detachView();
        mView = null;
    }

    @Override
    public void generateNumber() {
        if (mView != null) {
            mView.showText((mRandom.nextInt(10) + 1) + "");
        }
    }

}
