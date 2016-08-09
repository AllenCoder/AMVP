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

package rubensousa.github.com.sampledi.ui.base.di;


import rubensousa.github.com.sampledi.data.network.DaggerNetworkBaseComponent;
import rubensousa.github.com.sampledi.data.network.NetworkBaseComponent;
import rubensousa.github.com.sampledi.data.network.NetworkModule;
import rubensousa.github.com.sampledi.data.network.api.DaggerGithubBaseComponent;
import rubensousa.github.com.sampledi.data.network.api.GithubBaseComponent;
import rubensousa.github.com.sampledi.ui.base.di.interactor.DaggerInteractorProdComponent;
import rubensousa.github.com.sampledi.ui.base.di.interactor.InteractorProdComponent;
import rubensousa.github.com.sampledi.ui.base.di.presenter.DaggerPresenterProdComponent;
import rubensousa.github.com.sampledi.ui.base.di.presenter.PresenterProdComponent;

public class FlavorComponent {

    public static PresenterProdComponent createPresenterComponent() {
        NetworkBaseComponent networkComponent = DaggerNetworkBaseComponent.builder()
                .networkModule(new NetworkModule(NetworkBaseComponent.BASE_URL))
                .build();

        GithubBaseComponent githubComponent = DaggerGithubBaseComponent.builder()
                .networkBaseComponent(networkComponent)
                .build();

        InteractorProdComponent interactorComponent = DaggerInteractorProdComponent.builder()
                .githubBaseComponent(githubComponent)
                .build();

        return DaggerPresenterProdComponent.builder()
                .interactorProdComponent(interactorComponent)
                .build();
    }
}
