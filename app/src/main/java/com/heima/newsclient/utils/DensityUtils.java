package com.heima.newsclient.utils;

import android.content.Context;

/**
 * @author SparkJzp
 * @date 2016/11/13
 * @describe dp转px px转dp
 */

public class DensityUtils {
    public static int dp2px(Context context,float dp){
        float density = context.getResources().getDisplayMetrics().density;
        int px= (int) (dp*density+0.5f);
        return px;
    }
    public static float px2dp(Context context,int px){
        float density = context.getResources().getDisplayMetrics().density;
        float dp= px/density;
        return dp;
    }
}
