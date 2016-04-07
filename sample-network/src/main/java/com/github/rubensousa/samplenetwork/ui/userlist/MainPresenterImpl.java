package com.github.rubensousa.samplenetwork.ui.userlist;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.github.rubensousa.samplenetwork.model.User;
import com.github.rubensousa.samplenetwork.network.RetrofitModule;
import com.github.rubensousa.samplenetwork.network.RetrofitService;
import com.github.rubensousa.samplenetwork.ui.general.BasePresenterImpl;
import com.github.rubensousa.samplenetwork.ui.general.MvpCallback;

import java.util.ArrayList;

import icepick.State;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenterImpl extends BasePresenterImpl<MainView> implements MainPresenter {

    public static final String TASK_REFRESH = "refresh";
    public static final String TASK_LOAD = "load";

    @State
    boolean loading;

    @State
    boolean refreshing;

    private RetrofitService mService;

    public MainPresenterImpl() {
        mService = RetrofitModule.getService();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            load();
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onViewStateRestored(@NonNull Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        /**
         * Check if some tasks were executing and didn't finish before the view was attached
         * If not, restart them
         */
        if (loading) {
            getView().showRefreshing(true);
            if (!isTaskExecuting(TASK_LOAD)) {
                load();
            }
        }

        if (refreshing) {
            getView().showRefreshing(true);
            if (!isTaskExecuting(TASK_REFRESH)) {
                refresh();
            }
        }
    }

    @Override
    public void load() {
        loading = true;

        MvpCallback<ArrayList<User>> mvpCallback
                = new MvpCallback<>(getView(), new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                // This callback only gets called if view isn't null
                MainView view = getView();
                view.showRefreshing(false);

                if (response.isSuccessful()) {
                    view.setUsers(response.body());
                }
                loading = false;
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                // This callback only gets called if view isn't null
                getView().showRefreshing(false);
                loading = false;
            }
        });

        // Attach this callback to receive life cycle events
        attachMvpCallback(TASK_LOAD, mvpCallback);

        // Start the call by passing it to mvpCallback
        mvpCallback.enqueue(mService.getUsers(0));
    }

    @Override
    public void refresh() {
        refreshing = true;

        MvpCallback<ArrayList<User>> mvpCallback
                = new MvpCallback<>(getView(), new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                MainView view = getView();
                view.showRefreshing(false);

                if (response.isSuccessful()) {
                    view.setUsers(response.body());
                }
                refreshing = false;
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                getView().showRefreshing(false);
                refreshing = false;
            }
        });

        attachMvpCallback(TASK_REFRESH, mvpCallback);
        mvpCallback.enqueue(mService.getUsers(0));
    }


    @Override
    public void loadMore(int offset) {

    }

}
