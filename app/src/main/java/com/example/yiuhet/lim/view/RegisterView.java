package com.example.yiuhet.lim.view;

/**
 * Created by yiuhet on 2017/5/4.
 */

public interface RegisterView {
    void showLoading();

    void onRegisterError(String error);

    void onRegisterSuccess();

    void onUserNameError();

    void onPasswordError();

    void onPassword2Error();

    void onRegisterUserExit();
}
