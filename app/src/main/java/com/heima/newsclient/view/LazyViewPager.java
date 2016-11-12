package com.heima.newsclient.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author SparkJzp
 * @date 2016/11/12
 * @describe 继承ViewPager 重写onTouchEvent方法 不做滑动处理
 */

public class LazyViewPager extends NoScrollViewPager {
    public LazyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
