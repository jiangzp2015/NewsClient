package com.heima.newsclient.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.heima.newsclient.R;
import com.heima.newsclient.activity.HomeActivity;
import com.heima.newsclient.adapter.MenuAdapter;
import com.heima.newsclient.entity.NewsCenterBean;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.List;

/**
 * @author SparkJzp
 * @date 2016/11/11
 * @describe 侧边菜单Fragment
 */

public class MenuFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View mView;
    private ListView mLvMenu;
    private MenuAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_menu, null);
        initView();
        initData();
        initListener();
        return mView;
    }
    public void initView(){
        mLvMenu = (ListView) mView.findViewById(R.id.lv_menu);
    }
    public void initData(){

    }
    public void initListener(){
        mLvMenu.setOnItemClickListener(this);
    }

    public void setData(List<NewsCenterBean.DataBean> list){
        mAdapter = new MenuAdapter(getActivity(),list);
        mLvMenu.setAdapter(mAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HomeActivity homeActivity=(HomeActivity) getActivity();
        SlidingMenu slidingMenu = homeActivity.getSlidingMenu();
        slidingMenu.toggle();
        mAdapter.setSelect(position);
        mAdapter.notifyDataSetChanged();

/*        ContentFragment contentFragment=homeActivity.getContentFragment();
        contentFragment.switchMenu(position);
        */
        if (mListener!=null){
            mListener.onClickItem(position);
        }
    }

    public interface onClickItemListener{
        void onClickItem(int position);
    }
    onClickItemListener mListener;
    public void setOnClickItemListener(onClickItemListener listener){
        mListener=listener;
    }
}
