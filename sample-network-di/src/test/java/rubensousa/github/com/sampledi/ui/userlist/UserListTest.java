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

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import rubensousa.github.com.sampledi.data.model.User;
import rubensousa.github.com.sampledi.utils.MockUtils;


public class UserListTest {

    @Mock
    private UserContract.Interactor mInteractor;

    @Mock
    private UserContract.View mView;

    @Captor
    private ArgumentCaptor<UserContract.Interactor.OnLoadListener> mLoadCaptor;

    private UserContract.Presenter mPresenter;

    @Before
    public void setupPresenter() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new UserPresenter(mInteractor);

        // Attach view or the tests will fail!
        mPresenter.onViewAttach(mView);
    }

    @Test
    public void load() {
        mPresenter.load();

        // Check if we told the view to show the progress
        Mockito.verify(mView).showRefreshing(true);

        // Check if we asked the interactor to load users
        Mockito.verify(mInteractor).load(mLoadCaptor.capture());

        // Return mock users in the callback
        ArrayList<User> users = MockUtils.getUsers();
        mLoadCaptor.getValue().onLoadSuccess(users);

        // Check if the view stopped showing refresh
        Mockito.verify(mView).showRefreshing(false);

        // Check if users were added to the view
        Mockito.verify(mView).setUsers(users);
    }

    @Test
    public void refresh() {
        mPresenter.refresh();

        Mockito.verify(mView).showRefreshing(true);
        Mockito.verify(mInteractor).refresh(mLoadCaptor.capture());

        ArrayList<User> users = MockUtils.getUsers();
        mLoadCaptor.getValue().onLoadSuccess(users);

        Mockito.verify(mView).showRefreshing(false);
        Mockito.verify(mView).setUsers(users);
    }

}
