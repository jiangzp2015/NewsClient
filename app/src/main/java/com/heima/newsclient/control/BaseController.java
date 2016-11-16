package com.heima.newsclient.control;

import android.content.Context;
import android.view.View;

/**
 * @author SparkJzp
 * @date 2016/11/12
 * @describe 控制器的基类，抽取子类共有，放在这里面定义。
 */

public abstract class BaseController {
    protected Context mContext;
    private View mView;
    public BaseController(Context context) {
        mContext = context;
        mView=initView();
    }
    //具体视图子类自己去构建
    public abstract View initView();
    //具体数据子类自己填充
    public abstract void initData();
    //暴露 一个得到View的方法
    public View getRootView(){
        return mView;
    }

    /**
     * 那个子类需要自己调用
     * @param position
     */
    public void setSwitchMenu(int position) {

    }
}
