package com.github.rubensousa.samplenetwork.ui.userlist;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.rubensousa.samplenetwork.R;
import com.github.rubensousa.samplenetwork.model.User;
import com.github.rubensousa.samplenetwork.ui.general.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView,
        SwipeRefreshLayout.OnRefreshListener, Toolbar.OnMenuItemClickListener {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private MainPresenter mPresenter;
    private UserAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.app_name);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(this);
        mAdapter = new UserAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mAdapter.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mAdapter.restoreState(savedInstanceState);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            mAdapter.deleteAll();
            return true;
        }
        return false;
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenterImpl();
    }

    @Override
    public void setPresenter(MainPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setUsers(ArrayList<User> users) {
        mAdapter.setUsers(users);
    }

    @Override
    public void addUsers(ArrayList<User> users) {
        mAdapter.addUsers(users);
    }

    @Override
    public void showRefreshing(final boolean refreshing) {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (swipeRefreshLayout != null) {
                    swipeRefreshLayout.setRefreshing(refreshing);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
    }
}
