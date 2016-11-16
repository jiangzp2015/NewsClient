package com.heima.newsclient.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.heima.newsclient.entity.NewsListBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author SparkJzp
 * @date 2016/11/15
 * @describe TODO
 */
public class TopNewAdapter extends PagerAdapter {

    List<ImageView> mList;
    List<NewsListBean.DataBean.TopnewsBean> mTopnewsBeanList;
    Context mContext;

    public TopNewAdapter(List<ImageView> list, List<NewsListBean.DataBean.TopnewsBean> topnewsBeanList, Context context) {
        mList = list;
        mTopnewsBeanList = topnewsBeanList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=mList.get(position);
        String urlImage = mTopnewsBeanList.get(position).getTopimage();
        Picasso.with(mContext).load(urlImage).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
