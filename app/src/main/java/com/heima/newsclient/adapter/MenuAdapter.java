package com.heima.newsclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.heima.newsclient.R;
import com.heima.newsclient.entity.NewsCenterBean;
import com.heima.newsclient.utils.UIUtils;

import java.util.List;

/**
 * @author SparkJzp
 * @date 2016/11/12
 * @describe TODO
 */

public class MenuAdapter extends BaseAdapter {

    private Context mContext;
    private List<NewsCenterBean.DataBean> mDataBeanList;
    private int mSelectPosition;
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
        if (convertView==null){
//            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_menu,null);
            convertView= UIUtils.inflate(R.layout.item_menu);
        }
        NewsCenterBean.DataBean dataBean = mDataBeanList.get(position);
        TextView tv=(TextView)convertView;
        tv.setText(dataBean.title);
        tv.setEnabled(position==mSelectPosition? true:false);
        return convertView;
    }
    public void setSelect(int selectId){
        mSelectPosition=selectId;
    }
}
