package com.heima.newsclient.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.heima.newsclient.R;
import com.heima.newsclient.activity.HomeActivity;
import com.heima.newsclient.adapter.ContentAdapter;
import com.heima.newsclient.control.BaseController;
import com.heima.newsclient.control.TabController;
import com.heima.newsclient.control.tab.GovController;
import com.heima.newsclient.control.tab.HomeController;
import com.heima.newsclient.control.tab.NewsController;
import com.heima.newsclient.control.tab.SettingController;
import com.heima.newsclient.control.tab.SmartServiceController;
import com.heima.newsclient.view.LazyViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SparkJzp
 * @date 2016/11/11
 * @describe 内容Fragment
 */

public class ContentFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private View mView;
    private LazyViewPager mViewPagerContent;
    private RadioGroup mRgSelect;
    private List<TabController> mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_content, null);
        initView();
        initData();
        initListener();
        return mView;
    }

    public void initView() {
        mViewPagerContent = (LazyViewPager) mView.findViewById(R.id.vp_content);
        mRgSelect = (RadioGroup) mView.findViewById(R.id.rg_tab_bottom);

    }

    public void initData() {
        //定义集合，里面存储控制器
        mList = new ArrayList<>();
        mList.add(new HomeController(getActivity()));
        mList.add(new NewsController(getActivity()));
        mList.add(new SmartServiceController(getActivity()));
        mList.add(new GovController(getActivity()));
        mList.add(new SettingController(getActivity()));

        ContentAdapter adapter = new ContentAdapter(mList);
        mViewPagerContent.setAdapter(adapter);
        //用于控制viewPager 保留几个界面，相邻 ，默认是1.
        mViewPagerContent.setOffscreenPageLimit(0);
        setSlidingMenuEnable(false);
    }

    public void initListener() {
        mRgSelect.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_content:
                mViewPagerContent.setCurrentItem(0, false);// 禁用页面切换的动画效果
                setSlidingMenuEnable(false);
                break;
            case R.id.rb_news:
                mViewPagerContent.setCurrentItem(1,false);
                setSlidingMenuEnable(true);
                break;
            case R.id.rb_smart:
                mViewPagerContent.setCurrentItem(2,false);
                setSlidingMenuEnable(true);
                break;
            case R.id.rb_gov:
                mViewPagerContent.setCurrentItem(3,false);
                setSlidingMenuEnable(true);
                break;
            case R.id.rb_setting:
                mViewPagerContent.setCurrentItem(4,false);
                setSlidingMenuEnable(false);
                break;

        }
    }
    public void switchMenu(int position){
        BaseController controller=mList.get(mViewPagerContent.getCurrentItem());
        controller.setSwitchMenu(position);
    }
    /**
     * 设置侧边栏可用不可用
     *
     * @param enable
     */
    private void setSlidingMenuEnable(boolean enable) {
        HomeActivity mainUI = (HomeActivity) getActivity();
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();
        if (enable) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            // 禁用掉侧边栏滑动效果
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

}
