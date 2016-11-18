package com.heima.newsclient.control.tab;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.heima.newsclient.R;
import com.heima.newsclient.activity.HomeActivity;
import com.heima.newsclient.control.BaseController;
import com.heima.newsclient.control.TabController;
import com.heima.newsclient.control.menu.MenuInteractController;
import com.heima.newsclient.control.menu.MenuNewsController;
import com.heima.newsclient.control.menu.MenuPicController;
import com.heima.newsclient.control.menu.MenuSubjectController;
import com.heima.newsclient.entity.NewsCenterBean;
import com.heima.newsclient.fragment.MenuFragment;
import com.heima.newsclient.global.Constant;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

/**
 * @author SparkJzp
 * @date 2016/11/12
 * @describe 这是新闻中心的控制器， 有关首页的逻辑代码，都在这里写
 *             该类必须对外提供 首页要显示的视图 ， 以及操作数据的方法
 */

public class NewsController extends TabController implements View.OnClickListener {
    private NewsCenterBean mNewsCenterBean;
    private FrameLayout mContainer;
    private List<BaseController> mMenuControllerList;

    public NewsController(Context context) {
        super(context);
    }

    @Override
    public View initContentView() {
        mContainer = new FrameLayout(mContext);
        return mContainer;
    }

    @Override
    public void initData() {
        mTvTitle.setText("新闻中心");
        mIvLeft.setOnClickListener(this);
        mIvRight.setOnClickListener(this);
        Log.d(TAG, "onResponse:+++++++++ " + "从网络下载数据");
        //        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest request = new StringRequest(Request.Method.GET,
                Constant.URL_NEWS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String stringJson = new String(response.getBytes("ISO-8859-1"), "utf-8");
                    Gson gson = new Gson();
                    Log.d(TAG, "onResponse:+++++++++ " + stringJson);
                    mNewsCenterBean = gson.fromJson(stringJson, NewsCenterBean.class);
                    performSetMenuData();
                    prepareMenuControllers();
                    setSwitchMenu(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "加载失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }
    /**
     * 设置左边菜单的数据
     */
    private void performSetMenuData() {
        HomeActivity activity = (HomeActivity) mContext;
        MenuFragment menuFragment = activity.getMenuFragment();
        menuFragment.setData(mNewsCenterBean.data);
        //接口回调
//        menuFragment.setOnClickItemListener(this);
    }

    /**
     *  对外暴露该方法， 用于接收点击的条目下标，以便放置对应的控制器到容器里面去
     * @param position
     */
    public void setSwitchMenu(int position) {
        mContainer.removeAllViews();
        BaseController controller=mMenuControllerList.get(position);
        if (controller instanceof MenuPicController){
            mTvTitle.setText("组图");
            mIvLeft.setVisibility(View.VISIBLE);
            mIvRight.setVisibility(View.VISIBLE);
        }else if (controller instanceof MenuNewsController){
            mTvTitle.setText("新闻");
            mIvLeft.setVisibility(View.VISIBLE);
            mIvRight.setVisibility(View.GONE);
        }else if (controller instanceof MenuSubjectController){
            mTvTitle.setText("专题");
            mIvLeft.setVisibility(View.GONE);
            mIvRight.setVisibility(View.GONE);
        }else if (controller instanceof MenuInteractController){
            mTvTitle.setText("互动");
            mIvLeft.setVisibility(View.GONE);
            mIvRight.setVisibility(View.GONE);
        }
        View rootView = controller.getRootView();
        mContainer.addView(rootView);
        controller.initData();
    }
    /**
     * 先用集合存储4个菜单的控制器
     */
    public void prepareMenuControllers() {
        mMenuControllerList = new ArrayList<>();
        for (NewsCenterBean.DataBean bean:mNewsCenterBean.data) {
 /*           type | 描述
            1    | 新闻菜单
            2	   | 组图菜单
            3	   | 互动菜单
            10   | 专题菜单
            .....| .....菜单*/
            switch (bean.type){
                case 1:
                    mMenuControllerList.add(new MenuNewsController(mContext,bean.children));
                    break;
                case 2:
                    mMenuControllerList.add(new MenuPicController(mContext));
                    break;
                case 3:
                    mMenuControllerList.add(new MenuInteractController(mContext));
                    break;
                case 10:
                    mMenuControllerList.add(new MenuSubjectController(mContext));
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                HomeActivity activity= (HomeActivity) mContext;
                SlidingMenu slidingMenu = activity.getSlidingMenu();
                slidingMenu.toggle();
                break;
            case R.id.iv_right: //点击右边

                MenuPicController controller = (MenuPicController) mMenuControllerList.get(2);

                if(controller.isListShow()){
                    //如果现在显示的是listView
                    controller.showGird();
                    mIvRight.setImageResource(R.drawable.icon_pic_list_type);
                }else{

                    //如果现在显示的是listview
                    controller.showList();
                    mIvRight.setImageResource(R.drawable.icon_pic_grid_type);
                }

                break;
        }
    }

}
