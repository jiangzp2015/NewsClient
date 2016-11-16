package com.heima.newsclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.heima.newsclient.R;
import com.heima.newsclient.adapter.GuideAdapter;
import com.heima.newsclient.global.Constant;
import com.heima.newsclient.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SparkJzp
 * @date 2016/11/11
 * @describe 导航页
 */

public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private static final String TAG="GuideActivity";
    private ViewPager mViewPager;
    private ImageView mIvPoint;
    private LinearLayout mLinearLayout;
    private int[] mImages = {R.drawable.guide01, R.drawable.guide02, R.drawable.guide03, R.drawable.guide04};
    private int mPointSize;
    private int mDx;
    private ImageView mIvStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initData();
        initListener();
    }

    public void initView() {
        mIvStart = (ImageView) findViewById(R.id.iv_start);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_guide);
        mIvPoint = (ImageView) findViewById(R.id.iv_point);
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_root);

    }

    public void initData() {
        initPager();
        initPoint();
    }

    private void initPoint() {
        mPointSize = getResources().getDimensionPixelSize(R.dimen.point_size);
        for (int i = 0; i < mImages.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(R.drawable.point_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mPointSize, mPointSize);
            if (i != 0) {
                params.leftMargin = mPointSize;
            }
            mLinearLayout.addView(iv,params);
        }
        mLinearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mDx = mLinearLayout.getChildAt(1).getLeft() - mLinearLayout.getChildAt(0).getLeft();
                mLinearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        Log.d(TAG, "initPoint: mPointSize====" + mPointSize);
    }

    /**
     * 初始化pager
     */
    private void initPager() {
        List<View> list = new ArrayList<>();
        for (int i = 0; i < mImages.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(mImages[i]);
            list.add(iv);
        }
        GuideAdapter adapter = new GuideAdapter(list);
        mViewPager.setAdapter(adapter);
    }

    public void initListener() {
        mViewPager.addOnPageChangeListener(this);
        mIvStart.setOnClickListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mIvPoint.getLayoutParams();
        params.leftMargin = (int) (mDx * positionOffset) + position * mDx;
        mIvPoint.setLayoutParams(params);
    }

    @Override
    public void onPageSelected(int position) {
        mIvStart.setVisibility(position == mImages.length - 1 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "进入主界面", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(GuideActivity.this, HomeActivity.class));
        finish();
        PrefUtils.putBoolean(this,true, Constant.KEY_FINISH_GUIDE);

    }




}
