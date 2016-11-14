package com.heima.newsclient.control.menu;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.heima.newsclient.control.BaseController;
import com.heima.newsclient.entity.NewsCenterBean;

/**
 * @author SparkJzp
 * @date 2016/11/14
 * @describe TODO
 */

public class NewsListController extends BaseController {
    NewsCenterBean.ChildBean mBean;
    public NewsListController(Context context , NewsCenterBean.ChildBean bean) {
        super(context);
        this.mBean = bean;
    }

    @Override
    public View initView() {
        TextView textView=new TextView(mContext);
        textView.setText("新闻------实现");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20);
        return textView;
    }

    @Override
    public void initData() {

    }
}
