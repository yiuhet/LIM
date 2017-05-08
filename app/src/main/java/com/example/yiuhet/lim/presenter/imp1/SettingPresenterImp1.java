package com.example.yiuhet.lim.presenter.imp1;

import com.example.yiuhet.lim.BasePresenter;
import com.example.yiuhet.lim.presenter.SettingPresenter;
import com.example.yiuhet.lim.utils.ThreadUtils;
import com.example.yiuhet.lim.view.SettingView;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * Created by yiuhet on 2017/5/8.
 */

public class SettingPresenterImp1 extends BasePresenter<SettingView> implements SettingPresenter {

    private SettingView mSettingView;

    public SettingPresenterImp1(SettingView mSettingView) {
        this.mSettingView = mSettingView;
    }

    @Override
    public void logout() {

        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSettingView.onLogoutSuccess();
                    }
                });
            }

            @Override
            public void onError(int code, String error) {
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSettingView.onLogoutFailed();
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {
            }
        });
    }
}
