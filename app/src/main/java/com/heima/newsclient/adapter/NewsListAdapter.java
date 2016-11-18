package com.heima.newsclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.heima.newsclient.R;
import com.heima.newsclient.entity.NewsListBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author SparkJzp
 * @date 2016/11/15
 * @describe TODO
 */
public class NewsListAdapter extends BaseAdapter{
    private Context mContext;
    private List<NewsListBean.DataBean.NewsBean> mList;

    public NewsListAdapter(Context context, List<NewsListBean.DataBean.NewsBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public NewsListBean.DataBean.NewsBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_news_list,null);
            holder=new ViewHolder();
            holder.tvTitle= (TextView) convertView.findViewById(R.id.tv_title);
            holder.tvDate= (TextView) convertView.findViewById(R.id.tv_time);
            holder.ivLabel= (ImageView) convertView.findViewById(R.id.iv_icon);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        NewsListBean.DataBean.NewsBean newsBean = mList.get(position);
        holder.tvTitle.setText(newsBean.getTitle());
        holder.tvDate.setText(newsBean.getPubdate());
        Picasso.with(mContext).load(newsBean.getListimage()).into(holder.ivLabel);

        return convertView;
    }

    public void setData(List<NewsListBean.DataBean.NewsBean> list) {
        mList = list;
    }

    /**
     * 累加处理数据
     * @param list
     */
    public void addData(List<NewsListBean.DataBean.NewsBean> list) {
        mList.addAll(list);
    }

    static class ViewHolder{
        TextView tvTitle,tvDate;
        ImageView ivLabel;
    }
}
