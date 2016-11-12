package com.heima.newsclient.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.heima.newsclient.R;
import com.heima.newsclient.fragment.ContentFragment;
import com.heima.newsclient.fragment.MenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

public class HomeActivity extends SlidingActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initMenu();
        initFragment();

    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_content,new ContentFragment());
        transaction.replace(R.id.fl_menu,new MenuFragment());
        transaction.commit();
    }

    /**
     * 初始化SlideMenu
     */
    private void initMenu() {
        setBehindContentView(R.layout.view_menu);
        SlidingMenu slide=getSlidingMenu();
        slide.setBehindWidth(240);
        slide.setMode(SlidingMenu.LEFT);
        slide.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slide.setBehindScrollScale(0.35f);
        slide.setShadowWidthRes(R.dimen.shadow_width);
        slide.setShadowDrawable(R.drawable.shadow);
        slide.setFadeDegree(0.35f);
    }


}
