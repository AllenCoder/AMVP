package com.github.rubensousa.samplenetwork.ui.userlist;


import com.github.rubensousa.samplenetwork.ui.general.BasePresenter;

public interface MainPresenter extends BasePresenter<MainView> {

    void refresh();

    void load();

    void loadMore(int offset);
}
