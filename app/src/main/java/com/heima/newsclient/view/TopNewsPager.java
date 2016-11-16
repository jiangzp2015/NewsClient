package com.heima.newsclient.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * @author SparkJzp
 * @date 2016/11/15
 * @describe 自定义TopViewPage
 */

public class TopNewsPager extends ViewPager {

    private static final String TAG = "TopNewsPager";
    private float mMoveX;
    private float mDownX;

    public TopNewsPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent: ++++++"+" dispatchTouchEvent");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                Log.d(TAG, "ACTION_DOWN: ++++++"+" ACTION_DOWN");
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                mMoveX = ev.getX();
                Log.d(TAG, "ACTION_HOVER_MOVE: ++++++"+" ACTION_HOVER_MOVE");
                if (getCurrentItem() == 0 && mMoveX > mDownX) {
                    // true ： 孩子不想让父亲拦截事件  -->
                    // true :事件到此，孩子可以得到事件 ，false : 父亲会拦截事件，父亲自己处理。
                    Log.d(TAG, "dispatchTouchEvent: ++++++"+" 父亲会拦截");
                    requestDisallowInterceptTouchEvent(false);
                    
                } else if (getCurrentItem() == getAdapter().getCount() - 1
                        && mDownX > mMoveX) {
                    Log.d(TAG, "dispatchTouchEvent: ++++++"+" 父亲会拦截");
                    requestDisallowInterceptTouchEvent(false);
                } else {
                    Log.d(TAG, "dispatchTouchEvent: ++++++"+" 子类自己会处理");
                    requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
