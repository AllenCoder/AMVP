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

package com.github.rubensousa.samplenetwork.ui.general;


import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseInteractor<P extends Base.Presenter> implements Base.Interactor<P>,
        RetrofitRequest.OnFinishedListener {

    private Map<String, RetrofitRequest> mRequests;
    private P mPresenter;
    private boolean mViewAttached;

    public BaseInteractor() {
        mRequests = new LinkedHashMap<>();
    }

    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(P presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setViewAttached(boolean attached) {
        if (mViewAttached == attached) {
            return;
        }
        mViewAttached = true;
        for (RetrofitRequest request : mRequests.values()) {
            request.setViewAttached(mViewAttached);
        }
    }

    @Override
    public void cancelRequest(String key) {
        RetrofitRequest request = mRequests.get(key);
        if (request != null) {
            request.cancel();
        }
    }

    @Override
    public void attachRetrofitRequest(String key, RetrofitRequest request) {
        request.setViewAttached(true);
        request.setKey(key);
        request.setOnFinishedListener(this);
        RetrofitRequest old = mRequests.put(key, request);
        if (old != null) {
            old.cancel();
        }
    }

    @Override
    public boolean isRequestPending(String key) {
        RetrofitRequest request = mRequests.get(key);
        return request != null && request.isEnqueued();
    }

    @Override
    public boolean isRequestFinished(String key) {
        RetrofitRequest request = mRequests.get(key);
        return request != null && request.isFinished();
    }

    @Override
    public void onFinished(RetrofitRequest request) {
        synchronized (this) {
            mRequests.remove(request.getKey());
        }
    }
}
