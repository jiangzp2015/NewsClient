package com.heima.newsclient.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.heima.newsclient.R;
import com.heima.newsclient.adapter.MenuAdapter;
import com.heima.newsclient.entity.NewsCenterBean;

import java.util.List;

/**
 * @author SparkJzp
 * @date 2016/11/11
 * @describe 侧边菜单Fragment
 */

public class MenuFragment extends Fragment {

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

    }

    public void setData(List<NewsCenterBean.DataBean> list){
        mAdapter = new MenuAdapter(getActivity(),list);
        mLvMenu.setAdapter(mAdapter);

    }
}
