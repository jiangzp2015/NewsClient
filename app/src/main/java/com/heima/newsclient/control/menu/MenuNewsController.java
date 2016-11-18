package com.heima.newsclient.control.menu;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.heima.newsclient.R;
import com.heima.newsclient.activity.HomeActivity;
import com.heima.newsclient.adapter.MenuNewsAdapter;
import com.heima.newsclient.control.BaseController;
import com.heima.newsclient.entity.NewsCenterBean;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import static com.heima.newsclient.R.layout.controller_menu_news;

/**
 * @author SparkJzp
 * @date 2016/11/12
 * @describe 新闻
 */

public class MenuNewsController extends BaseController implements ViewPager.OnPageChangeListener, View.OnClickListener, SlidingMenu.OnOpenedListener, SlidingMenu.OnOpenListener, SlidingMenu.OnCloseListener, SlidingMenu.OnClosedListener {

    private static final String TAG = "MenuNewsController";
    private ViewPager mViewPager;
    private TabPageIndicator mIndicator;
    private ImageView mIvArr;
    private List<BaseController> mList;
    private List<NewsCenterBean.ChildBean> mChildBeanList;

    public MenuNewsController(Context context, List<NewsCenterBean.ChildBean> childBeanList) {
        super(context);
        mChildBeanList = childBeanList;
    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(mContext).inflate(controller_menu_news, null);

        mViewPager = (ViewPager) view.findViewById(R.id.viewpager_news);
        mIndicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        mIvArr = (ImageView) view.findViewById(R.id.iv_arr);

        mIvArr.setOnClickListener(this);

        HomeActivity activity = (HomeActivity) mContext;
        SlidingMenu slidingMenu = activity.getSlidingMenu();
        slidingMenu.setOnOpenedListener(this);
        slidingMenu.setOnOpenListener(this);
        slidingMenu.setOnCloseListener(this);
        slidingMenu.setOnClosedListener(this);

        return view;
    }

    @Override
    public void initData() {
        mList = new ArrayList<>();
        for (NewsCenterBean.ChildBean bean : mChildBeanList) {
            mList.add(new NewsListController(mContext, bean));
        }

        MenuNewsAdapter adapter = new MenuNewsAdapter(mList, mChildBeanList);
        mViewPager.setAdapter(adapter);
        //让指示器与viewpager绑定到一起。
        mIndicator.setViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {// 在第一个页签,允许侧边栏出现
            // 开启侧边栏
            setSlidingMenuEnable(true);
        } else {// 其他页签,禁用侧边栏, 保证viewpager可以正常向右滑动
            // 关闭侧边栏
            setSlidingMenuEnable(false);
        }
    }
    /**
     * 设置侧边栏可用不可用
     *
     * @param enable
     */
    private void setSlidingMenuEnable(boolean enable) {
        HomeActivity mainUI = (HomeActivity) mContext;
        SlidingMenu slidingMenu = mainUI.getSlidingMenu();

        if (enable) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            // 禁用掉侧边栏滑动效果
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //如果最外层是空闲状态 让他走，反之
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            NewsListController controller = (NewsListController) mList.get(mViewPager.getCurrentItem());
            controller.autoSwitch();
        }
    }

    @Override
    public void onClick(View v) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    @Override
    public void onOpened() {
        startChangPageTask();
    }

    @Override
    public void onOpen() {
        startChangPageTask();
    }

    @Override
    public void onClose() {
        startChangPageTask();
    }

    @Override
    public void onClosed() {
        startChangPageTask();
    }
    /**
     * 切换图片
     */
    private void startChangPageTask() {
        NewsListController controller = (NewsListController) mList.get(mViewPager.getCurrentItem());
        controller.autoSwitch();
    }
}
