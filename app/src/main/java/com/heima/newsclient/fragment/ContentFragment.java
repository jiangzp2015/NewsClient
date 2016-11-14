package com.heima.newsclient.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.heima.newsclient.R;
import com.heima.newsclient.adapter.ContentAdapter;
import com.heima.newsclient.control.BaseController;
import com.heima.newsclient.control.TabController;
import com.heima.newsclient.control.tab.GovController;
import com.heima.newsclient.control.tab.HomeController;
import com.heima.newsclient.control.tab.NewsController;
import com.heima.newsclient.control.tab.SettingController;
import com.heima.newsclient.control.tab.SmartServiceController;
import com.heima.newsclient.utils.UIUtils;
import com.heima.newsclient.view.LazyViewPager;

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
        mView= UIUtils.inflate(R.layout.fragment_content);
//        mView = inflater.inflate(R.layout.fragment_content, null);
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
        mList = new ArrayList<>();
        mList.add(new HomeController(getActivity()));
        mList.add(new NewsController(getActivity()));
        mList.add(new SmartServiceController(getActivity()));
        mList.add(new GovController(getActivity()));
        mList.add(new SettingController(getActivity()));

        ContentAdapter adapter = new ContentAdapter(mList);
        mViewPagerContent.setAdapter(adapter);

        mViewPagerContent.setOffscreenPageLimit(0);
    }

    public void initListener() {
        mRgSelect.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_content:
                mViewPagerContent.setCurrentItem(0);
                break;
            case R.id.rb_news:
                mViewPagerContent.setCurrentItem(1);
                break;
            case R.id.rb_smart:
                mViewPagerContent.setCurrentItem(2);
                break;
            case R.id.rb_gov:
                mViewPagerContent.setCurrentItem(3);
                break;
            case R.id.rb_setting:
                mViewPagerContent.setCurrentItem(4);
                break;

        }
    }
    public void switchMenu(int position){
        BaseController controller=mList.get(mViewPagerContent.getCurrentItem());
        controller.setSwitchMenu(position);
    }
}
