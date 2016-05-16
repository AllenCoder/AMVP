/*
 * Copyright 2016 Rúben Sousa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.rubensousa.samplenetwork.ui.userlist;

import com.github.rubensousa.samplenetwork.model.User;
import com.github.rubensousa.samplenetwork.network.RetrofitModule;
import com.github.rubensousa.samplenetwork.network.RetrofitService;
import com.github.rubensousa.samplenetwork.ui.general.BaseInteractor;
import com.github.rubensousa.samplenetwork.ui.general.RetrofitRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainInteractor extends BaseInteractor<Main.Presenter> implements Main.Interactor {

    public static final String TASK_REFRESH = "refresh";
    public static final String TASK_LOAD = "load";

    private RetrofitService mService;
    private OnRefreshListener mRefreshListener;
    private OnLoadListener mLoadListener;

    public MainInteractor() {
        mService = RetrofitModule.getService();
    }

    @Override
    public void refresh(OnRefreshListener listener) {
        if (isRequestPending(TASK_REFRESH)) {
            return;
        }

        mRefreshListener = listener;

        RetrofitRequest<ArrayList<User>> request
                = new RetrofitRequest<>(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if (mRefreshListener != null) {
                    if (response.isSuccessful()) {
                        mRefreshListener.onRefreshSuccess(response.body());
                    } else {
                        mRefreshListener.onRefreshError();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                if (mRefreshListener != null) {
                    mRefreshListener.onRefreshError();
                }
            }
        });

        attachRetrofitRequest(TASK_REFRESH, request);
        request.start(mService.getUsers(0));
    }

    @Override
    public void load(OnLoadListener loadListener) {
        if (isRequestPending(TASK_LOAD)) {
            return;
        }

        mLoadListener = loadListener;

        RetrofitRequest<ArrayList<User>> request
                = new RetrofitRequest<>(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if (mLoadListener != null) {
                    if (response.isSuccessful()) {
                        mLoadListener.onLoadSuccess(response.body());
                    } else {
                        mLoadListener.onLoadError();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                if (mLoadListener != null) {
                    mLoadListener.onLoadError();
                }
            }
        });

        attachRetrofitRequest(TASK_LOAD, request);
        request.start(mService.getUsers(0));
    }
}
