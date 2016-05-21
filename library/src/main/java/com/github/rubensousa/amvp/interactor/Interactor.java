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

package com.github.rubensousa.amvp.interactor;


/**
 * An Interactor should be used to decouple business logic from the Presenter.
 * @param <P> Presenter to be attached to the Interactor
 */
public interface Interactor<P extends PresenterInteractor> {

    P getPresenter();

    void setPresenter(P presenter);

    /**
     * Called when the presenter is destroyed
     * May be useful to cancel any pending tasks
     */
    void onDestroy();
}