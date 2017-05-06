package com.example.yiuhet.lim.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.example.yiuhet.lim.MVPBaseActivity;
import com.example.yiuhet.lim.R;
import com.example.yiuhet.lim.presenter.imp1.SplashPresenterImpl;
import com.example.yiuhet.lim.view.SplashView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

/**
 * Created by yiuhet on 2017/5/3.
 */

public class SplashActivity extends MVPBaseActivity<SplashView, SplashPresenterImpl> implements SplashView {

    private static final int DELAY = 2000;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void init() {
        super.init();
        mPresenter.checkLoginStatus();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected SplashPresenterImpl createPresenter() {
        return new SplashPresenterImpl(this);
    }

    @Override
    public void onNotLogin() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(LoginActivity.class);
            }
        },DELAY);
    }

    @Override
    public void onLoggedIn() {
        startActivity(MainActivity.class);
    }
}
