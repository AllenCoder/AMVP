package com.github.rubensousa.samplenetwork.ui.userlist;


import com.github.rubensousa.samplenetwork.model.User;
import com.github.rubensousa.samplenetwork.ui.general.BaseView;

import java.util.ArrayList;

public interface MainView extends BaseView<MainPresenter> {

    void showRefreshing(boolean refreshing);

    void setUsers(ArrayList<User> users);

    void addUsers(ArrayList<User> users);
}
