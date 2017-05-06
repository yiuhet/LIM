package com.example.yiuhet.lim.presenter.imp1;

import com.example.yiuhet.lim.BasePresenter;
import com.example.yiuhet.lim.presenter.SplashPresenter;
import com.example.yiuhet.lim.view.SplashView;
import com.hyphenate.chat.EMClient;

/**
 * Created by yiuhet on 2017/5/3.
 */

public class SplashPresenterImpl extends BasePresenter<SplashView> implements SplashPresenter {

    public SplashView mSplashView;

    public SplashPresenterImpl(SplashView splashView) {
        mSplashView = splashView;
    }

    @Override
    public void checkLoginStatus() {
        if (EMClient.getInstance().isLoggedInBefore() && EMClient.getInstance().isConnected()) {
            mSplashView.onLoggedIn();
        } else {
            mSplashView.onNotLogin();
        }
    }
}
