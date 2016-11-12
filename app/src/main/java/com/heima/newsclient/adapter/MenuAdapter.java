package com.heima.newsclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.heima.newsclient.entity.NewsCenterBean;

import java.util.List;

/**
 * @author SparkJzp
 * @date 2016/11/12
 * @describe TODO
 */

public class MenuAdapter extends BaseAdapter {

    private Context mContext;
    private List<NewsCenterBean.DataBean> mDataBeanList;

    public MenuAdapter(Context context, List<NewsCenterBean.DataBean> dataBeanList) {
        mContext = context;
        mDataBeanList = dataBeanList;
    }

    @Override
    public int getCount() {
        return mDataBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
