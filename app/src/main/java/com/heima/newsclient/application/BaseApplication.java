package com.heima.newsclient.application;

import android.app.Application;
import android.os.Handler;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * @author SparkJzp
 * @date 2016/11/13
 * @describe 构造一个Application基类，方便得到全局对象，来使用
 */

public class BaseApplication extends Application {
    private static RequestQueue mQueue;
    private static BaseApplication mContext;
    private static Handler mMainThreadHandler;
    private static Thread mMainThread;
    private static int mMainThreadId;
    @Override
    public void onCreate() {
        super.onCreate();
        mQueue= Volley.newRequestQueue(getApplicationContext());
        this.mContext=this;
        this.mMainThreadHandler=new Handler();
        this.mMainThread=Thread.currentThread();
        this.mMainThreadId=android.os.Process.myTid();


    }
    public static BaseApplication getmContext(){
        return mContext;
    }
    public static Handler getMainThreadHandler(){
        return mMainThreadHandler;
    }
    public static Thread getMainThread(){
        return mMainThread;
    }
    public static int getMainThreadId(){
        return mMainThreadId;
    }
    public static RequestQueue getHttpQueue(){
        return mQueue;
    }
}
