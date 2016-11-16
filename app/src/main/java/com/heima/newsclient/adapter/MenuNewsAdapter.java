package com.heima.newsclient.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.heima.newsclient.control.BaseController;
import com.heima.newsclient.entity.NewsCenterBean;

import java.util.List;

/**
 * @author SparkJzp
 * @date 2016/11/14
 * @describe TODO
 */
public class MenuNewsAdapter extends PagerAdapter {

    private List<BaseController> mList;
    private List<NewsCenterBean.ChildBean> mList1;

    public MenuNewsAdapter(List<BaseController> list,List<NewsCenterBean.ChildBean> titles) {
        this.mList = list;
        this.mList1 =titles;
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
        BaseController controller = mList.get(position);
        View rootView = controller.getRootView();
        container.addView(rootView);
        controller.initData();
        return rootView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mList1.get(position).title;
    }
}
