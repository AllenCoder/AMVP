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

package rubensousa.github.com.sampledi;

import android.app.Application;

import rubensousa.github.com.sampledi.data.di.interactor.InteractorComponent;
import rubensousa.github.com.sampledi.data.network.DaggerNetworkComponent;
import rubensousa.github.com.sampledi.data.network.NetworkComponent;
import rubensousa.github.com.sampledi.data.network.NetworkModule;
import rubensousa.github.com.sampledi.data.network.api.DaggerGithubComponent;
import rubensousa.github.com.sampledi.data.network.api.GithubComponent;
import rubensousa.github.com.sampledi.data.di.interactor.DaggerInteractorComponent;
import rubensousa.github.com.sampledi.data.di.presenter.DaggerPresenterComponent;
import rubensousa.github.com.sampledi.data.di.presenter.PresenterComponent;


public class App extends Application {

    static InteractorComponent sInteractorComponent;
    static GithubComponent sGithubComponent;
    static NetworkComponent sNetworkComponent;
    private PresenterComponent mPresenterComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        sNetworkComponent = DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule(NetworkComponent.BASE_URL))
                .build();

        sGithubComponent = DaggerGithubComponent.builder()
                .networkComponent(sNetworkComponent)
                .build();

        sInteractorComponent = DaggerInteractorComponent.builder()
                .githubComponent(sGithubComponent)
                .build();

        mPresenterComponent = DaggerPresenterComponent.builder()
                .interactorComponent(sInteractorComponent)
                .build();
    }

    public PresenterComponent getPresenterComponent() {
        return mPresenterComponent;
    }

}
