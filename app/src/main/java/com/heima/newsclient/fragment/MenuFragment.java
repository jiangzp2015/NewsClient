package com.heima.newsclient.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heima.newsclient.R;

/**
 * @author SparkJzp
 * @date 2016/11/11
 * @describe 侧边菜单Fragment
 */

public class MenuFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu,null);
    }
}
