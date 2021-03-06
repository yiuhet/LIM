package com.example.yiuhet.lim.ui.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yiuhet.lim.MVPBaseActivity;
import com.example.yiuhet.lim.R;
import com.example.yiuhet.lim.presenter.imp1.LoginPresenterImp1;
import com.example.yiuhet.lim.view.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends MVPBaseActivity<LoginView, LoginPresenterImp1> implements LoginView {

    @BindView(R.id.btn_sign_in)
    Button mSignInbtn;
    @BindView(R.id.edt_password)
    EditText mPasswordedt;
    @BindView(R.id.edt_username)
    EditText mUsernameedt;
    @BindView(R.id.imageView)
    ImageView mImageView;
    @BindView(R.id.tx_sign_up)
    TextView mTxSignUp;

    @Override
    protected void init() {
        super.init();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        ButterKnife.bind(this);
        mPasswordedt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    startLogin();
                    return true;
                }
                return false;
            }
        });
    }


    private void startLogin() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED) {
            login();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    private void login() {
        mPresenter.login(mUsernameedt.getText().toString().trim(), mPasswordedt.getText().toString().trim());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenterImp1 createPresenter() {
        return new LoginPresenterImp1(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
                login();
            } else {
                toast(getString(R.string.permission_miss));
            }
        }
    }

    @Override
    public void onUserNameError() {
        mUsernameedt.setError(getString(R.string.username_error));
    }

    @Override
    public void onPasswordError() {
        mPasswordedt.setError(getString(R.string.password_error));
    }

    @Override
    public void onStartLogin() {

    }

    @Override
    public void onLoginSuccess() {
        hideProgress();
        startActivity(MainActivity.class);
    }

    @Override
    public void onLoginFailed(String error) {
        hideProgress();
        Looper.prepare();
        toast("登陆失败,失败原因：" + error.toString());
        Looper.loop();
    }


    @Override
    public void showLoading() {
        showProgress("loading...");
        //mProgressBar.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.btn_sign_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in:
                startLogin();
                break;

        }
    }


    @OnClick(R.id.tx_sign_up)
    public void onViewClicked() {
        startActivity(RegisterActivity.class,false);
    }

}
