package com.heima.newsclient.control.menu;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.heima.newsclient.R;
import com.heima.newsclient.activity.HomeActivity;
import com.heima.newsclient.adapter.NewsListAdapter;
import com.heima.newsclient.adapter.TopNewAdapter;
import com.heima.newsclient.control.BaseController;
import com.heima.newsclient.entity.NewsCenterBean;
import com.heima.newsclient.entity.NewsListBean;
import com.heima.newsclient.global.Constant;
import com.heima.newsclient.view.PullToRefreshListView;
import com.heima.newsclient.view.TopNewsPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @author SparkJzp
 * @ate 2016/11/14
 * @describe 通用新闻列表控制器， 中国、北京 、国际... 使用的都是这个控制器
 */

public class NewsListController extends BaseController implements ViewPager.OnPageChangeListener, View.OnTouchListener, PullToRefreshListView.OnRefreshListener {
    private static final String TAG = "NewsListController";
    private TopNewsPager mViewPager;

    NewsCenterBean.ChildBean mBean;
    private NewsListBean mNewsListBean;
    private LinearLayout mLlPoint;
    private TextView mTvLabel;

    private View mCurrentView;
    private AutoSwitchImage mAutoTask;
    private PullToRefreshListView mLvInfo;
    private View mHeadView;
    private NewsListAdapter mAdapter;
    private Handler mHandler = new Handler();
    private String mUrl;
    private PtrFrameLayout mPtrlayout;

    public NewsListController(Context context, NewsCenterBean.ChildBean bean) {
        super(context);
        this.mBean = bean;
    }

    @Override
    public View initView() {
        //导入的是本身这个控制器的布局
        View view = View.inflate(mContext, R.layout.controller_news_list, null);
        mLvInfo = (PullToRefreshListView) view.findViewById(R.id.lv_info);
        //导入顶部的viewpager
        mHeadView = View.inflate(mContext, R.layout.view_top_news_pager, null);
        mViewPager = (TopNewsPager) mHeadView.findViewById(R.id.viewpager_top);
        mLlPoint = (LinearLayout) mHeadView.findViewById(R.id.ll_point);
        mTvLabel = (TextView) mHeadView.findViewById(R.id.tv_label);
        //添加头部view
        mLvInfo.addHeaderView(mHeadView);

        mViewPager.addOnPageChangeListener(this);
        mViewPager.setOnTouchListener(this);
        mLvInfo.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void initData() {
        //初次上来马上就去设置适配器，但是这个集合是空的。
        List<NewsListBean.DataBean.NewsBean> list = new ArrayList<>();
        mAdapter = new NewsListAdapter(mContext, list);
        mLvInfo.setAdapter(mAdapter);

        getDataFromServer();
    }

    //请求数据
    private void getDataFromServer() {
        mUrl = Constant.URL_ROOT + mBean.url;
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest request = new StringRequest(Request.Method.GET, mUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    response = new String(response.getBytes("ISO-8859-1"), "utf-8");
                    Log.d(TAG, "onResponse: 请求成功" + response);
                    Gson gson = new Gson();
                    mNewsListBean = gson.fromJson(response, NewsListBean.class);
                    //设置数据
                    performTopNewsData();
                    //动态添加小圆点
                    performAddPoint();
                    //开始自动切换图片
                    autoSwitch();
                    //设置适配器
                    setAdapter();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //通知ListView 刷新完成了
                            mLvInfo.onRefreshComplete(true);
                        }
                    }, 1200);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: 请求失败" + error.getMessage());
                mLvInfo.onRefreshComplete(false);
            }
        });
        queue.add(request);

    }

    /**
     * 加载更多数据
     */
    protected void getMoreDataFromServer() {

        Toast.makeText(mContext, "加载更多数据", Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置适配器
     * 初次进来，请求数据会执行一次
     * 以后每下拉刷新一次， 也会执行一次。
     */
    private void setAdapter() {
        List<NewsListBean.DataBean.NewsBean> list = mNewsListBean.getData().getNews();
        mAdapter.setData(list);
        mAdapter.notifyDataSetChanged();

    }

    /**
     * 动态添加小圆点
     */
    private void performAddPoint() {
        mLlPoint.removeAllViews();
        int size = mContext.getResources().getDimensionPixelSize(R.dimen.point_top_size);
        for (int i = 0; i < mNewsListBean.getData().getTopnews().size(); i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(R.drawable.point_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            if (i != 0) {
                params.leftMargin = size;
            } else {
                imageView.setSelected(true);
                mCurrentView = imageView;
            }
            mLlPoint.addView(imageView, params);
        }
    }

    /**
     *该方法适用于设置顶部的图片数据
     */
    private void performTopNewsData() {

        List<ImageView> list = new ArrayList<>();
        List<NewsListBean.DataBean.TopnewsBean> topNews = mNewsListBean.getData().getTopnews();
        for (int i = 0; i < topNews.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            list.add(imageView);
        }
        TopNewAdapter adapter = new TopNewAdapter(list, topNews, mContext);
        mViewPager.setAdapter(adapter);
        mTvLabel.setText(topNews.get(0).getTitle());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentView.setSelected(false);
        View view = mLlPoint.getChildAt(position);
        view.setSelected(true);
        mCurrentView = view;
        NewsListBean.DataBean dataBean = mNewsListBean.getData();
        mTvLabel.setText(dataBean.getTopnews().get(position).getTitle());
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


    }

    /**
     * 自动切换图片
     */
    public void autoSwitch() {
        if (mAutoTask == null) {
            mAutoTask = new AutoSwitchImage();
        }
        mAutoTask.start();
    }

    @Override
    public void onRefresh() {
        // 从网络加载数据
        getDataFromServer();
    }

    @Override
    public void onLoadMore() {
        // 加载更多数据
        if (mUrl != null) {
            getMoreDataFromServer();
        } else {
            mLvInfo.onRefreshComplete(true);
            Toast.makeText(mContext, "没有更多数据了", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    class AutoSwitchImage extends Handler implements Runnable {

        @Override
        public void run() {
            int currentItem = mViewPager.getCurrentItem();
            if (currentItem == mViewPager.getAdapter().getCount() - 1) {
                mViewPager.setCurrentItem(0);
            } else {
                mViewPager.setCurrentItem(currentItem + 1);
            }
            postDelayed(this, 1500);
        }

        public void stop() {
            removeCallbacks(this);
        }

        public void start() {
            removeCallbacks(this);
            postDelayed(this, 1500);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mAutoTask.stop();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                mAutoTask.start();
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "onTouch: ACTION_CANCEL++");
                break;
        }
        return false;
    }


}
