package com.example.yiuhet.lim.presenter.imp1;

import android.util.Log;

import com.example.yiuhet.lim.BasePresenter;
import com.example.yiuhet.lim.model.User;
import com.example.yiuhet.lim.presenter.RegisterPresenter;
import com.example.yiuhet.lim.utils.CheckUtils;
import com.example.yiuhet.lim.utils.ThreadUtils;
import com.example.yiuhet.lim.view.RegisterView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by yiuhet on 2017/5/4.
 */

public class RegisterPresenterImp1 extends BasePresenter<RegisterView> implements RegisterPresenter {

    public RegisterView mRegisterView;
    public RegisterPresenterImp1(RegisterView registerView) {
        mRegisterView = registerView;
    }

    @Override
    public void register(String userName, String password, String pwdConfirm) {
        if (CheckUtils.checkUserName(userName)) {
            if (CheckUtils.checkPassword(password)) {
                if (password.equals(pwdConfirm)) {
                    mRegisterView.showLoading();
                    registerBmob(userName,password);
                } else {
                    mRegisterView.onPassword2Error();
                }
            } else {
                mRegisterView.onPasswordError();
            }
        } else {
            mRegisterView.onUserNameError();
        }
    }

    private void registerBmob(final String userName, final String password) {
        User user = new User(userName,password);
        user.signUp(new SaveListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    registerEaseMob(userName,password);
                    Log.d("ppap.","注册快要成功了！！！");
                } else {
                    toastRegisterFailed(e);
                    Log.d("ppap.","注册shibai！！！");
                }
            }


        });
    }

    private void registerEaseMob(final String userName, final String password) {
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(userName,password);
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRegisterView.onRegisterSuccess();
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    ThreadUtils.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRegisterView.onRegisterError(e.toString());
                        }
                    });
                }
            }
        });
    }


    private void toastRegisterFailed(BmobException e) {
        if (e.getErrorCode() == 202) {
            mRegisterView.onRegisterUserExit();
        } else {
            mRegisterView.onRegisterError(e.toString());
        }
    }
}
