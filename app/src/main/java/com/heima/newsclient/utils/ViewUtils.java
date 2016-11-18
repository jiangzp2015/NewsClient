package com.heima.newsclient.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author SparkJzp
 * @date 2016/11/16
 * @describe 关于View的工具类
 */

public class ViewUtils {
    @SuppressWarnings("unchecked")
    public static <T> T findViewById(Activity activity, int viewId) {
        return (T) activity.findViewById(viewId);
    }

    @SuppressWarnings("unchecked")
    public static <T> T findViewById(View viewParent, int viewId) {
        return (T) viewParent.findViewById(viewId);
    }


}
