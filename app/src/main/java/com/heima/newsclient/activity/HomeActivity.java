package com.heima.newsclient.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.heima.newsclient.R;
import com.heima.newsclient.fragment.ContentFragment;
import com.heima.newsclient.fragment.MenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

public class HomeActivity extends SlidingActivity {
    private static String TAG = "HomeActivity";
    private static final String TAG_MENU = "menu";
    private static final String TAG_CONTENT = "content";
    private FragmentManager mFragmentManager;


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
        mFragmentManager = getFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.fl_content, new ContentFragment(), TAG_CONTENT);
        transaction.replace(R.id.fl_menu, new MenuFragment(), TAG_MENU);
        transaction.commit();
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024;
        Log.d(TAG, "maxMemory: "+maxMemory);
    }

    /**
     * 初始化SlideMenu
     */
    private void initMenu() {
        setBehindContentView(R.layout.view_menu);
        SlidingMenu slide = getSlidingMenu();
        slide.setBehindWidth(240);
        slide.setMode(SlidingMenu.LEFT);
        slide.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slide.setBehindScrollScale(0.35f);
        slide.setShadowWidthRes(R.dimen.shadow_width);
        slide.setShadowDrawable(R.drawable.shadow);
        slide.setFadeDegree(0.35f);
    }

    public MenuFragment getMenuFragment() {
        return (MenuFragment) mFragmentManager.findFragmentByTag(TAG_MENU);
    }

    public ContentFragment getContentFragment() {
        return (ContentFragment) mFragmentManager.findFragmentByTag(TAG_CONTENT);
    }

}
