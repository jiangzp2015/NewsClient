package com.heima.newsclient.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;

import com.heima.newsclient.BaseApplication;

/**
 * @author SparkJzp
 * @date 2016/11/13
 * @describe UI 方面的工具类
 */

public class UIUtils {
    public static Context getContext() {
        return BaseApplication.getmContext();
    }

    public static Handler getHandler() {
        return BaseApplication.getMainThreadHandler();
    }

    public static Thread getMainThread() {
        return BaseApplication.getMainThread();
    }

    public static long getMainThreadId() {
        return BaseApplication.getMainThreadId();
    }

    //在主线程延迟执行Runnable
    public static boolean postDelayed(Runnable runnable, long delatedTime) {
        return getHandler().postDelayed(runnable, delatedTime);
    }

    //在主线程执行Runnable
    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    //在主线程Looper中移除Runnable
    public static void removeCallbacks(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }
    //获取填充
    public static View inflate(int resId){
        return LayoutInflater.from(getContext()).inflate(resId,null);
    }
    //获取资源
    public static Resources getResources(){
        return getContext().getResources();
    }
    /** 获取文字 */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /** 获取文字数组 */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }
    /** 获取dimen */
    public static int getDimens(int resId){
        return getResources().getDimensionPixelSize(resId);
    }
    /** 获取drawable */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Drawable getDrawable(int resId){
        return getResources().getDrawable(resId,getContext().getTheme());
    }
    /** 获取颜色 */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static int getColor(int resId) {
        return getResources().getColor(resId,getContext().getTheme());
    }
    //判断当前是否为主线程
    public static boolean isRunInMainThread(){
        return android.os.Process.myTid()==getMainThreadId();
    }
    public static void runInMainThread(Runnable runnable){
        if (isRunInMainThread()){
            runnable.run();
        }else {
            post(runnable);
        }
    }

}
