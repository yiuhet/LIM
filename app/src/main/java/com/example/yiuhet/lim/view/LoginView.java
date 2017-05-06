package com.example.yiuhet.lim.view;

/**
 * Created by yiuhet on 2017/5/3.
 */

public interface LoginView {
    void onUserNameError();
    void onPasswordError();
    void onStartLogin();

    void onLoginSuccess();
    void onLoginFailed(String error);

    void showLoading();

}
