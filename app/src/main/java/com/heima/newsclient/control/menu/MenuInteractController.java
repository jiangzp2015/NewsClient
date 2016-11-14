package com.heima.newsclient.control.menu;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.heima.newsclient.control.BaseController;

/**
 * @author SparkJzp
 * @date 2016/11/12
 * @describe 互动
 */

public class MenuInteractController extends BaseController {
    public MenuInteractController(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        TextView tv = new TextView(mContext);
        tv.setText("菜单---互动");
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(18);
        tv.setTextColor(Color.RED);
        return tv;
    }

    @Override
    public void initData() {
    }
}
