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


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitRequest<T> implements Callback<T> {

    private boolean mCallFinished;
    private boolean mCallEnqueued;
    private String mKey;
    private boolean mViewAttached;
    private Call<T> mCall;
    private Callback<T> mCallback;
    private Response<T> mResponse;
    private Throwable mThrowable;
    private OnFinishedListener mListener;


    public RetrofitRequest(Callback<T> callback) {
        mCallback = callback;
    }

    public void setOnFinishedListener(OnFinishedListener finishedListener) {
        mListener = finishedListener;
    }

    public void setViewAttached(boolean attached) {
        mViewAttached = attached;
        if (mViewAttached && mCallFinished) {

            if (mResponse != null) {
                onResponse(mCall, mResponse);
            }

            if(mThrowable != null){
                onFailure(mCall,mThrowable);
            }
        }
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        mCallFinished = true;
        mCallEnqueued = false;
        mCall = call;

        if (mViewAttached) {
            if (mCallback != null) {
                mCallback.onResponse(call, response);
            }

            if (mListener != null) {
                mListener.onFinished(this);
            }
            mResponse = null;
        }else{
            mResponse = response;
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        mCallFinished = true;
        mCallEnqueued = false;
        mCall = call;

        if (mViewAttached) {
            if (mCallback != null) {
                mCallback.onFailure(call, t);
            }

            if (mListener != null) {
                mListener.onFinished(this);
            }
            mThrowable = null;
        }else{
            mThrowable = t;
        }
    }

    public void setKey(String key) {
        mKey = key;
    }

    public boolean isFinished() {
        return mCallFinished;
    }

    public boolean isEnqueued() {
        return mCallEnqueued;
    }

    public String getKey() {
        return mKey;
    }

    public Call<T> getCall() {
        return mCall;
    }

    public void start(Call<T> call) {
        mCall = call;
        mCallEnqueued = true;
        mCall.enqueue(this);
    }

    public void cancel() {
        mCall.cancel();
    }

    public interface OnFinishedListener {
        void onFinished(RetrofitRequest request);
    }
}
