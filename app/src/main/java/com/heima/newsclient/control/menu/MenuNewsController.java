package com.heima.newsclient.control.menu;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.heima.newsclient.R;
import com.heima.newsclient.activity.HomeActivity;
import com.heima.newsclient.adapter.MenuNewsAdapter;
import com.heima.newsclient.control.BaseController;
import com.heima.newsclient.entity.NewsCenterBean;
import com.heima.newsclient.utils.UIUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SparkJzp
 * @date 2016/11/12
 * @describe 新闻
 */

public class MenuNewsController extends BaseController implements ViewPager.OnPageChangeListener, View.OnClickListener {


    private static final String TAG="MenuNewsController";
    private ViewPager mViewPager;
    private TabPageIndicator mIndicator;
    private ImageView mIvArr;
    private List<BaseController> mList;
    private List<NewsCenterBean.ChildBean> mChildBeanList;
    public MenuNewsController(Context context,List<NewsCenterBean.ChildBean> childBeanList) {
        super(context);
        mChildBeanList=childBeanList;
    }

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.controller_menu_news);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager_news);
        mIndicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        mIvArr = (ImageView) view.findViewById(R.id.iv_arr);
        mIvArr.setOnClickListener(this);
        return view;
    }

    @Override
    public void initData() {
        mList = new ArrayList<>();
        for (NewsCenterBean.ChildBean bean:
        mChildBeanList) {
            mList.add(new NewsListController(mContext,bean));
        }
        MenuNewsAdapter adapter=new MenuNewsAdapter(mList,mChildBeanList);
        mViewPager.setAdapter(adapter);
        mIndicator.setViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        HomeActivity homeActivity= (HomeActivity) mContext;
        SlidingMenu slidingMenu=homeActivity.getSlidingMenu();
        slidingMenu.setTouchModeAbove(position==0?SlidingMenu.TOUCHMODE_FULLSCREEN:SlidingMenu.TOUCHMODE_NONE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1 );
    }
}
