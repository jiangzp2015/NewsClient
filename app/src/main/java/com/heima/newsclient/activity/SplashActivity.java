package com.heima.newsclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

import com.heima.newsclient.R;
import com.heima.newsclient.global.Constant;
import com.heima.newsclient.utils.PrefUtils;

/**
 * Created by SparkJzp on 2016/11/11 12:54
 * Describe : 闪屏界面
 */

public class SplashActivity extends Activity implements Animation.AnimationListener {

    private View mRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        showAnimation();
    }

    /**
     *闪屏动画
     */
    private void showAnimation() {
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rAnimation = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );
        ScaleAnimation sAnimation = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation aAnimation = new AlphaAnimation(0, 1);

        set.addAnimation(rAnimation);
        set.addAnimation(sAnimation);
        set.addAnimation(aAnimation);
        set.setDuration(1800);
        set.setInterpolator(new BounceInterpolator());
        mRoot.startAnimation(set);
        set.setAnimationListener(this);
    }

    public void initView() {
        mRoot = findViewById(R.id.rl_root);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        //判断是否第一次进入
        boolean isFinished = PrefUtils.getBoolean(this, Constant.KEY_FINISH_GUIDE);
        if (!isFinished) {
            startActivity(new Intent(this, GuideActivity.class));
        } else {
            startActivity(new Intent(this, HomeActivity.class));
        }
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
