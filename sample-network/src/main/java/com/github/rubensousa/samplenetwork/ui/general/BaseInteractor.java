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


import com.github.rubensousa.amvp.interactor.AbstractInteractor;
import com.github.rubensousa.samplenetwork.network.NetworkRequest;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseInteractor extends AbstractInteractor
        implements Base.Interactor, NetworkRequest.OnFinishedListener {

    private Map<String, NetworkRequest> mRequests;
    private boolean mViewAttached;

    public BaseInteractor() {
        mRequests = new LinkedHashMap<>();
    }

    @Override
    public void setViewAttached(boolean attached) {
        mViewAttached = attached;
        for (NetworkRequest request : mRequests.values()) {
            request.setViewAttached(mViewAttached);
        }
    }

    @Override
    public void cancelRequest(String key) {
        NetworkRequest request = mRequests.get(key);
        if (request != null) {
            request.cancel();
        }
    }

    @Override
    public void attachNetworkRequest(String key, NetworkRequest request) {
        request.setViewAttached(true);
        request.setKey(key);
        request.setOnFinishedListener(this);
        NetworkRequest old = mRequests.put(key, request);
        if (old != null) {
            old.cancel();
        }
    }

    @Override
    public boolean isRequestPending(String key) {
        NetworkRequest request = mRequests.get(key);
        return request != null && request.isExecuting();
    }

    @Override
    public boolean isRequestFinished(String key) {
        NetworkRequest request = mRequests.get(key);
        return request != null && request.isFinished();
    }

    @Override
    public void onFinished(NetworkRequest request) {
        synchronized (this) {
            mRequests.remove(request.getKey());
        }
    }
}
