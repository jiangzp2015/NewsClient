package com.heima.newsclient.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.heima.newsclient.R;

/**
 * @author SparkJzp
 * @date 2016/11/12
 * @describe 带标题的控制器
 */

public abstract class TabController extends BaseController {

    protected TextView mTvTitle;

    public TabController(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.controller_tab, null);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        FrameLayout flContainer= (FrameLayout) view.findViewById(R.id.fl_container);
        flContainer.addView(initContentView());
        return view;
    }
    public abstract View initContentView();
}
