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

package rubensousa.github.com.sampledi.ui.base;


import com.github.rubensousa.amvp.MvpPresenter;
import com.github.rubensousa.amvp.MvpView;
import com.github.rubensousa.amvp.interactor.MvpInteractor;

import rubensousa.github.com.sampledi.data.network.NetworkRequest;


public interface Base {

    interface View<P extends Presenter> extends MvpView<P> {
        void showProgressDialog();

        void hideProgressDialog();
    }

    interface Presenter<V extends View> extends MvpPresenter<V> {

    }

    interface Interactor extends MvpInteractor {

        void setViewAttached(boolean attached);

        void cancelRequest(String key);

        void attachNetworkRequest(String key, NetworkRequest request);

        boolean isRequestPending(String key);

        boolean isRequestFinished(String key);
    }
}
