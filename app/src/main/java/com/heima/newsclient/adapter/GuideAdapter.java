package com.heima.newsclient.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author SparkJzp
 * @date 2016/11/11
 * @describe  导航页的适配器
 */

public class GuideAdapter extends PagerAdapter {

    private List<View> mViewList;

    public GuideAdapter(List<View> viewList) {
        mViewList = viewList;
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = mViewList.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

       container.removeView((View) object);
    }
}
