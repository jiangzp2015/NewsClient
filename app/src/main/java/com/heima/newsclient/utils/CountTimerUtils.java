package com.heima.newsclient.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * @author SparkJzp
 * @date 2016/11/16
 * @describe 倒计时工具类, 开启倒计时start，取消倒计时cancel
 */

public class CountTimerUtils {
    private int mCount;

    private int mCountdownInterval;

    private static final int WHAT = 0;

    public interface OnCountDownListener {

        void onCountDown(int count);

    }

    private Handler mHandler;

    private Context mContext;

    public CountTimerUtils(Context mContext, final OnCountDownListener mListener) {
        this.mContext = mContext;
        mHandler = new Handler(mContext.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == WHAT) {
                    mListener.onCountDown(mCount);
                    if (mListener != null && mCount > 0) {
                        mCount--;
                        mHandler.sendEmptyMessageDelayed(WHAT, mCountdownInterval);
                    }
                }
            }
        };
    }

    /**
     * 开始倒计时
     *
     * @param count             总次数
     * @param countDownInterval 每次倒计时的间隔（毫秒为单位）
     */
    public void start(int count, int countDownInterval) {
        this.mCount = count;
        this.mCountdownInterval = countDownInterval;
        this.mHandler.removeMessages(WHAT);
        mHandler.sendEmptyMessageDelayed(WHAT, mCountdownInterval);
    }

    /**
     * 取消计时操作
     */
    public void cancel() {
        mHandler.removeMessages(WHAT);
    }
}
