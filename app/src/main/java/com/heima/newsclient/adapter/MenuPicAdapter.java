package com.heima.newsclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.heima.newsclient.R;
import com.heima.newsclient.entity.NewsPicBean;

import java.util.List;

/**
 * @author SparkJzp
 * @date 2016/11/17
 * @describe TODO
 */
public class MenuPicAdapter extends BaseAdapter{
    private List<NewsPicBean.DataBean.NewsBean> mList;
    private Context mContext;

    public MenuPicAdapter(List<NewsPicBean.DataBean.NewsBean> news, Context context) {
        this.mList=news;
        mContext=context;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuPicAdapter.ViewHolder holder;
        if (convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_pic, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        NewsPicBean.DataBean.NewsBean newsBean = mList.get(position);
        holder.tvLabel.setText(newsBean.getTitle());
        Glide.with(mContext).load(newsBean.getListimage()).into(holder.ivIcon);
        return convertView;
    }

    static class ViewHolder{
        ImageView ivIcon;
        TextView tvLabel;

        public ViewHolder(View view) {
            ivIcon= (ImageView) view.findViewById(R.id.iv_icon);
            tvLabel=(TextView)view.findViewById(R.id.tv_label);

        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
