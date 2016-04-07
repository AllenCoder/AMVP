package com.github.rubensousa.amvpsample.ui;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.github.rubensousa.amvp.view.MvpAppCompatActivity;
import com.github.rubensousa.amvpsample.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MvpAppCompatActivity<MainView, MainPresenter> implements MainView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.textView)
    TextView textView;

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        textView.setFreezesText(true);
        setSupportActionBar(toolbar);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.button)
    public void onGenerateClick() {
        mPresenter.generateNumber();
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenterImpl();
    }

    @Override
    public void onPresenterPrepared(MainPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showText(String text) {
        textView.setText(text);
        ViewCompat.animate(textView).cancel();
        ViewCompat.animate(textView).scaleX(1.5f).scaleY(1.5f)
                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(View view) {
                        super.onAnimationEnd(view);
                        if (textView != null) {
                            ViewCompat.animate(textView).scaleX(1f).scaleY(1f).setListener(null);
                        }
                    }
                });
    }
}
