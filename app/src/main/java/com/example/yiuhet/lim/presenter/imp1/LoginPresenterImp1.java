package com.example.yiuhet.lim.presenter.imp1;

import com.example.yiuhet.lim.BasePresenter;
import com.example.yiuhet.lim.presenter.LoginPresenter;
import com.example.yiuhet.lim.utils.CheckUtils;
import com.example.yiuhet.lim.view.LoginView;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * Created by yiuhet on 2017/5/3.
 */

public class LoginPresenterImp1 extends BasePresenter<LoginView> implements LoginPresenter {

    public LoginView mLoginView;

    public LoginPresenterImp1(LoginView loginView) {
        mLoginView = loginView;
    }


    @Override
    public void login(String userName, String password) {
        if (CheckUtils.checkUserName(userName)) {
            if (CheckUtils.checkPassword(password)) {
                mLoginView.onStartLogin();
                mLoginView.showLoading();
                startLogin(userName,password);
            } else {
                mLoginView.onPasswordError();
            }
        } else {
            mLoginView.onUserNameError();
        }
    }

    private void startLogin(String userName, String password) {
        EMClient.getInstance().login(userName, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                mLoginView.onLoginSuccess();
            }

            @Override
            public void onError(int code, String error) {
                mLoginView.onLoginFailed(error);
            }

            @Override
            public void onProgress(int progress, String status) {
            }
        });
    }

}
