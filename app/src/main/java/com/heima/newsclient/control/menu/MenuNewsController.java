package com.heima.newsclient.control.menu;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.heima.newsclient.control.BaseController;

/**
 * @author SparkJzp
 * @date 2016/11/12
 * @describe 政务页面控制器
 */

public class MenuNewsController extends BaseController {

    public MenuNewsController(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        TextView textView= new TextView(mContext);
        textView.setText("菜单---新闻");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20);
        return textView;
    }

    @Override
    public void initData() {

    }
}
