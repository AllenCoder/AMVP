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

package rubensousa.github.com.sampledi.ui.userlist;


import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rubensousa.github.com.sampledi.data.model.User;
import rubensousa.github.com.sampledi.data.network.api.GithubUserService;
import rubensousa.github.com.sampledi.data.network.api.RetrofitRequest;
import rubensousa.github.com.sampledi.ui.base.BaseInteractor;

public class UserInteractor extends BaseInteractor implements UserContract.Interactor {

    public static final String TASK_REFRESH = "refresh";
    public static final String TASK_LOAD = "load";

    private GithubUserService mService;
    private OnLoadListener mLoadListener;

    @Inject
    public UserInteractor(GithubUserService service) {
        mService = service;
    }

    @Override
    public void refresh(OnLoadListener listener) {
        if (isRequestPending(TASK_REFRESH)) {
            return;
        }

        mLoadListener = listener;

        RetrofitRequest<ArrayList<User>> request
                = new RetrofitRequest<>(mService.getUsers(0), new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if (response.isSuccessful()) {
                    mLoadListener.onLoadSuccess(response.body());
                } else {
                    mLoadListener.onLoadError();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                mLoadListener.onLoadError();
            }
        });

        attachNetworkRequest(TASK_REFRESH, request);
        request.start();
    }

    @Override
    public void load(OnLoadListener loadListener) {
        if (isRequestPending(TASK_LOAD)) {
            return;
        }

        mLoadListener = loadListener;

        RetrofitRequest<ArrayList<User>> request
                = new RetrofitRequest<>(mService.getUsers(0), new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                if (response.isSuccessful()) {
                    mLoadListener.onLoadSuccess(response.body());
                } else {
                    mLoadListener.onLoadError();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                mLoadListener.onLoadError();
            }
        });

        attachNetworkRequest(TASK_LOAD, request);

        request.start();
    }
}
