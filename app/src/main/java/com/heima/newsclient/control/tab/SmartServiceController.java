package com.heima.newsclient.control.tab;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.heima.newsclient.control.TabController;

/**
 * @author SparkJzp
 * @date 2016/11/12
 * @describe   只能服务页面的控制器
 */

public class SmartServiceController extends TabController {
    public SmartServiceController(Context context) {
        super(context);
    }

    @Override
    public View initContentView() {
        TextView textView=new TextView(mContext);
        textView.setText("智慧服务");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20);
        return textView;
    }

    @Override
    public void initData() {
        mTvTitle.setText("智慧服务");
    }
}
