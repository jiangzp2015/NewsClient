package com.heima.newsclient.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.heima.newsclient.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author SparkJzp
 * @date 2016/11/16
 * @describe 自定义ListView  可下拉刷新
 */

public class PullToRefreshListView extends ListView implements AbsListView.OnScrollListener, android.widget.AdapterView.OnItemClickListener {
    private static final String TAG = "PullToRefreshListView";
    private ImageView mIvArr;
    private ProgressBar mProgressBar;
    private TextView mTvTime;
    private TextView mTvLabel;
    private View mHeaderView;
    private int mHeaderHeight;
    private static final int STATE_PULL_TO_REFRESH = 1;// 下拉刷新
    private static final int STATE_RELEASE_TO_REFRESH = 2;// 松开刷新
    private static final int STATE_REFRESHING = 3;// 正在刷新
    // 当前下拉刷新的状态
    private int mCurrentState = STATE_PULL_TO_REFRESH;// 默认是下拉刷新

    private boolean isLoadingMore;// 标记是否正在加载更多

    private RotateAnimation mUpAnimation;
    private RotateAnimation mDownAnimation;
    private SimpleDateFormat mDateFormat;
    private int mStartY;
    private View mFooterView;
    private int mFooterHeight;

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initHeaderView();
        initFooterView();
    }

    /**
     * 初始化布局
     */
    private void init() {
        mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.list_refresh_header, null);
        mFooterView = View.inflate(getContext(), R.layout.list_refresh_footer, null);
        mIvArr = (ImageView) mHeaderView.findViewById(R.id.iv_arr);
        mProgressBar = (ProgressBar) mHeaderView.findViewById(R.id.progressbar);
        mTvTime = (TextView) mHeaderView.findViewById(R.id.tv_time);
        mTvLabel = (TextView) mHeaderView.findViewById(R.id.tv_label);
    }

    /**
     * 初始化底部View
     */
    private void initFooterView() {

        this.addFooterView(mFooterView);
        //相对布局使用这行代码 会报空指针
        mFooterView.measure(0, 0);
        mFooterHeight = mFooterView.getMeasuredHeight();
        Log.d(TAG, "initFooterView: " + mFooterHeight);
        //隐藏脚布局
        setFooterPadding(-mFooterHeight);
        //设置滑动监听
        this.setOnScrollListener(this);
    }

    private void setFooterPadding(int padding) {
        mFooterView.setPadding(0, padding, 0, 0);
    }


    /**
     * 初始化头部View
     */
    private void initHeaderView() {
        // 添加头布局
        this.addHeaderView(mHeaderView);
        //  (1, 获取头布局高度, 2.设置负paddingTop,布局就会往上走)
        // int height = mHeaderView.getHeight();//此处无法获取高度,因为布局还没有绘制完成
        // 绘制之前就要获取布局高度
        //   手动测量布局
        mHeaderView.measure(0, 0);
        // 隐藏头布局
        mHeaderHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0, -mHeaderHeight, 0, 0);
        initAnim();
        setCurrentTime();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mStartY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mStartY != -1) {
                    mStartY = (int) ev.getY();
                }
                if (mCurrentState == STATE_REFRESHING) {
                    break;
                }
                int dY = (int) (ev.getY() - mStartY);
                if (dY > 0 && getFirstVisiblePosition() == 0) {
                    // 计算当前的paddingtop值
                    int paddingTop = dY - mHeaderHeight;
                    if (paddingTop >= 0 && mCurrentState != STATE_RELEASE_TO_REFRESH) {
                        mCurrentState = STATE_RELEASE_TO_REFRESH;
                        //刷新
                        refreshState();
                    } else if (paddingTop < 0 && mCurrentState != STATE_PULL_TO_REFRESH) {
                        mCurrentState = STATE_PULL_TO_REFRESH;
                        refreshState();
                    }
                    //重新设置头部padding
                    mHeaderView.setPadding(0, paddingTop, 0, 0);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                //把坐标归零
                mStartY = -1;
                if (mCurrentState == STATE_RELEASE_TO_REFRESH) {
                    // 如果当前是松开刷新, 就要切换为正在刷新
                    mCurrentState = STATE_REFRESHING;
                    // 显示头布局
                    mHeaderView.setPadding(0, 0, 0, 0);
                    refreshState();
                    // 下拉刷新回调
                    if (mListener != null) {
                        mListener.onRefresh();
                    }
                } else if (mCurrentState == STATE_PULL_TO_REFRESH) {
                    mHeaderView.setPadding(0, -mHeaderHeight, 0, 0);
                }
                break;
            default:
                break;

        }
        return super.onTouchEvent(ev);
    }

    /**
     * 根据当前状态刷新界面
     */
    private void refreshState() {
        switch (mCurrentState) {
            case STATE_PULL_TO_REFRESH:
                mTvLabel.setText("下拉刷新");
                mIvArr.setVisibility(VISIBLE);
                mIvArr.startAnimation(mDownAnimation);
                mProgressBar.setVisibility(INVISIBLE);
                break;
            case STATE_REFRESHING:
                mTvLabel.setText("正在刷新");
                mProgressBar.setVisibility(VISIBLE);
                mIvArr.clearAnimation();
                mIvArr.setVisibility(INVISIBLE);
                break;
            case STATE_RELEASE_TO_REFRESH:
                mTvLabel.setText("释放刷新");
                mIvArr.setAnimation(mUpAnimation);
                mIvArr.setVisibility(VISIBLE);
                mProgressBar.setVisibility(INVISIBLE);
                break;

        }
    }

    /**
     * 设置上次更新时间
     */
    private void setCurrentTime() {
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = mDateFormat.format(new Date());
        mTvTime.setText(time);
    }

    /**
     * 初始化箭头动画
     */
    private void initAnim() {
        mUpAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mUpAnimation.setDuration(500);
        mUpAnimation.setFillAfter(true);

        mDownAnimation = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mDownAnimation.setDuration(500);
        mDownAnimation.setFillAfter(true);
    }


    //定义接口 对刷新的回调
    private OnRefreshListener mListener;

    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }

    public interface OnRefreshListener {
        //下拉刷新
         void onRefresh();

        //加载更多
         void onLoadMore();
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        Log.d(TAG, "getLastVisiblePosition" + getLastVisiblePosition());
        int lastVisiblePosition = getLastVisiblePosition();
        Log.d(TAG, "lastVisiblePosition" + lastVisiblePosition);
        if (scrollState == SCROLL_STATE_IDLE && lastVisiblePosition == getAdapter().getCount() - 1) {
            isLoadingMore = true;
            //展现脚布局
            setFooterPadding(0);
            //listview设置当前要展示的item的位置
            setSelection(getCount() - 1);
            if (mListener != null) {
                mListener.onLoadMore();
            }

        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    // 刷新完成
    public void onRefreshComplete(boolean success) {
        if (!isLoadingMore) {
            mHeaderView.setPadding(0, -mHeaderHeight, 0, 0);
            mCurrentState = STATE_PULL_TO_REFRESH;
            mProgressBar.setVisibility(View.INVISIBLE);
            mIvArr.setVisibility(View.VISIBLE);
            mTvLabel.setText("下拉刷新");
            if (success) {
                setCurrentTime();
            }
        } else {
            //隐藏脚布局
            setFooterPadding(-mFooterHeight);
            isLoadingMore = false;
        }
    }

    private OnItemClickListener mItemClickListener;

    // 重写item点击方法
    @Override
    public void setOnItemClickListener(
            android.widget.AdapterView.OnItemClickListener listener) {
        mItemClickListener = listener;
        super.setOnItemClickListener(this);// 将点击事件设置给当前的RefreshListView
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(parent, view, position
                    - getHeaderViewsCount(), id);
        }
    }


}
