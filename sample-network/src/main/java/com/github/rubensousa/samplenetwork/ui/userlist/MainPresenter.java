package com.github.rubensousa.samplenetwork.ui.userlist;


import android.os.Bundle;
import android.support.annotation.Nullable;


import com.github.rubensousa.samplenetwork.model.User;
import com.github.rubensousa.samplenetwork.ui.general.BasePresenter;

import java.util.ArrayList;

import icepick.State;

public class MainPresenter extends BasePresenter<Main.View, Main.Interactor>
        implements Main.Presenter{

    @State
    boolean loading;

    @State
    boolean refreshing;

    @Override
    public Main.Interactor createInteractor() {
        return new MainInteractor();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            // onViewStateRestored will be called after
            loading = true;
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (loading) load();
        if (refreshing) refresh();
    }

    @Override
    public void load() {
        loading = true;

        if (isViewAttached()) {
            getView().showRefreshing(true);
        }

        getInteractor().load(new Main.Interactor.OnLoadListener() {
            @Override
            public void onLoadSuccess(ArrayList<User> users) {
                loading = false;
                getView().showRefreshing(false);
                getView().setUsers(users);
            }

            @Override
            public void onLoadError() {
                loading = false;
                getView().showRefreshing(false);
            }
        });
    }

    @Override
    public void refresh() {
        refreshing = true;

        if (isViewAttached()) {
            getView().showRefreshing(true);
        }

        getInteractor().refresh(new Main.Interactor.OnRefreshListener() {
            @Override
            public void onRefreshSuccess(ArrayList<User> users) {
                refreshing = false;
                getView().showRefreshing(false);
                getView().setUsers(users);
            }

            @Override
            public void onRefreshError() {
                refreshing = false;
                getView().showRefreshing(false);
            }
        });
    }
}
