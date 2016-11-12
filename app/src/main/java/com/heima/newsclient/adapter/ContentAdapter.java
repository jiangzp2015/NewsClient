package com.heima.newsclient.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.heima.newsclient.control.TabController;

import java.util.List;

/**
 * @author SparkJzp
 * @date 2016/11/12
 * @describe
 */

public class ContentAdapter extends PagerAdapter {
    List<TabController> mList;
    public ContentAdapter(List<TabController> list) {
        mList=list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //获取基本控制
        TabController controller=mList.get(position);
        //得到子类各自初始的视图
        View rootView = controller.getRootView();
        //添加到ViewPager中
        container.addView(rootView);
        //初始化 控制器的数据
        controller.initData();

        return rootView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
