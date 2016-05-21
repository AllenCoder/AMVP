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

package com.github.rubensousa.samplenetwork.network;


public abstract class NetworkRequest<T, E> {

    private OnFinishedListener mFinishedListener;
    private String mKey;
    private boolean mViewAttached;
    private boolean mCanceled;
    private boolean mExecuting;
    private boolean mFinished;

    public NetworkRequest() {

    }

    public abstract T getResponse();

    public abstract E getError();

    public abstract void sendResult();

    public void setOnFinishedListener(OnFinishedListener listener) {
        mFinishedListener = listener;
    }

    public void finish() {
        mExecuting = false;
        mFinished = true;
        if (mViewAttached) {
            sendResult();
            if (mFinishedListener != null) {
                mFinishedListener.onFinished(this);
            }
        }
    }

    public void setViewAttached(boolean attached) {
        mViewAttached = attached;
        if (mViewAttached && mFinished) {
            finish();
        }
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public void start() {
        mExecuting = true;
        mFinished = false;
    }

    public void cancel() {
        mCanceled = true;
        mExecuting = false;
        mFinished = true;
    }

    public boolean isExecuting() {
        return mExecuting;
    }

    public boolean isFinished() {
        return mFinished;
    }

    public boolean isCanceled() {
        return mCanceled;
    }

    public interface OnFinishedListener {
        void onFinished(NetworkRequest request);
    }

}
