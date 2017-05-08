package com.example.yiuhet.lim.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.example.yiuhet.lim.MVPBaseActivity;
import com.example.yiuhet.lim.R;
import com.example.yiuhet.lim.presenter.imp1.SplashPresenterImpl;
import com.example.yiuhet.lim.view.SplashView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yiuhet on 2017/5/3.
 */

public class SplashActivity extends MVPBaseActivity<SplashView, SplashPresenterImpl> implements SplashView {

    private static final int ANIM_TIME = 2000;

    private static final float SCALE_END = 1.15F;

    private static final int[] Imgs={
            R.drawable.welcomimg1,R.drawable.welcomimg2,
            R.drawable.welcomimg3,R.drawable.welcomimg4,
            R.drawable.welcomimg5, R.drawable.welcomimg6,
            R.drawable.welcomimg7,R.drawable.welcomimg8,
            R.drawable.welcomimg9,R.drawable.welcomimg10,
            R.drawable.welcomimg11,R.drawable.welcomimg12,};

    private static final int DELAY = 1000;
    @BindView(R.id.iv_entry)
    ImageView mIvEntry;
    private Handler mHandler = new Handler();


    @Override
    protected void init() {
        super.init();
        ButterKnife.bind(this);
        Random random = new Random(SystemClock.elapsedRealtime());//SystemClock.elapsedRealtime() 从开机到现在的毫秒数（手机睡眠(sleep)的时间也包括在内）
        mIvEntry.setImageResource(Imgs[random.nextInt(Imgs.length)]);

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
                //startActivity(LoginActivity.class);
                startAnim(LoginActivity.class);
            }
        }, DELAY);
    }

    @Override
    public void onLoggedIn() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //startActivity(MainActivity.class);
                startAnim(MainActivity.class);
            }
        }, DELAY);
    }


    private void startAnim(final Class activity) {

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mIvEntry, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mIvEntry, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIM_TIME).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter()
        {

            @Override
            public void onAnimationEnd(Animator animation)
            {
                startActivity(activity);
            }
        });
    }
}
