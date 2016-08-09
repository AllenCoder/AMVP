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

import rubensousa.github.com.sampledi.data.model.User;
import rubensousa.github.com.sampledi.ui.base.Base;

public interface UserContract {

    interface View extends Base.View<Presenter> {
        void showRefreshing(boolean refreshing);

        void setUsers(ArrayList<User> users);
    }

    interface Presenter extends Base.Presenter<View> {
        void refresh();

        void load();
    }

    interface Interactor extends Base.Interactor {

        interface OnLoadListener {
            void onLoadSuccess(ArrayList<User> users);

            void onLoadError();
        }

        void refresh(OnLoadListener listener);

        void load(OnLoadListener loadListener);
    }

}
