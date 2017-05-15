package com.example.yiuhet.lim.ui.activity;

import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;

import com.example.yiuhet.lim.MVPBaseActivity;
import com.example.yiuhet.lim.R;
import com.example.yiuhet.lim.presenter.RegisterPresenter;
import com.example.yiuhet.lim.presenter.imp1.RegisterPresenterImp1;
import com.example.yiuhet.lim.view.RegisterView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends MVPBaseActivity<RegisterView, RegisterPresenterImp1> implements RegisterView {

    @BindView(R.id.edt_new_username)
    EditText mNewUsernameedt;
    @BindView(R.id.edt_new_password)
    EditText mNewPasswordedt;
    @BindView(R.id.edt_new_password2)
    EditText mNewPassword2edt;
    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() {
        super.init();
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_register;
    }

    @Override
    protected RegisterPresenterImp1 createPresenter() {
        return new RegisterPresenterImp1(this);
    }

    @Override
    public void showLoading() {
        showProgress("Loading...");
    }

    @Override
    public void onRegisterError(String error) {
        hideProgress();
        toast("登陆失败,失败原因：" + error.toString());
    }

    @Override
    public void onRegisterSuccess() {
        hideProgress();
        toast("注册成功");
        startActivity(LoginActivity.class);
    }

    @Override
    public void onUserNameError() {
        mNewUsernameedt.setError(getString(R.string.username_error));
    }

    @Override
    public void onPasswordError() {
        mNewPasswordedt.setError(getString(R.string.password_error));
    }

    @Override
    public void onPassword2Error() {
        mNewPassword2edt.setError(getString(R.string.password_different));
    }

    @Override
    public void onRegisterUserExit() {
        hideProgress();
        mNewUsernameedt.setError(getString(R.string.username_used));
    }


    @OnClick(R.id.button)
    public void onViewClicked() {
        mPresenter.register(mNewUsernameedt.getText().toString().trim(),
                            mNewPasswordedt.getText().toString().trim(),
                            mNewPassword2edt.getText().toString().trim());
    }
}
