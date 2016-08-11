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

package rubensousa.github.com.sampledi.data.network.api;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rubensousa.github.com.sampledi.data.network.NetworkRequest;
import rubensousa.github.com.sampledi.utils.EspressoIdlingResource;

public class RetrofitRequest<T> extends NetworkRequest<T, Throwable> implements Callback<T> {

    private Call<T> mCall;
    private Callback<T> mCallback;
    private Response<T> mResponse;
    private Throwable mError;

    public RetrofitRequest(Call<T> call, Callback<T> callback) {
        mCall = call;
        mCallback = callback;
    }

    public RetrofitRequest(Callback<T> callback) {
        this(null, callback);
    }

    @Override
    public void sendResult() {
        if (mCallback != null) {
            if (mResponse != null) {
                mCallback.onResponse(mCall, mResponse);
                mResponse = null;
                mCallback = null;
            }

            if (mError != null) {
                if (!mCall.isCanceled()) {
                    mCallback.onFailure(mCall, mError);
                }
                mError = null;
                mCallback = null;
            }
        }

        if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
            EspressoIdlingResource.decrement();
        }
    }

    @Override
    public T getResponse() {
        return mResponse != null ? mResponse.body() : null;
    }

    @Override
    public Throwable getError() {
        return mError;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        mResponse = response;
        mCall = call;
        finish();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        mCall = call;
        mError = t;
        finish();
    }

    public Call<T> getCall() {
        return mCall;
    }

    public void setCall(Call<T> call) {
        mCall = call;
    }

    @Override
    public void start() {
        super.start();
        if (mCall != null) {
            mCall.enqueue(this);
            EspressoIdlingResource.increment();
        }
    }

    public void cancel() {
        super.cancel();
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
