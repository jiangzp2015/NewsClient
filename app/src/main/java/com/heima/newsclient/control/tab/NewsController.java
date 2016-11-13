package com.heima.newsclient.control.tab;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.heima.newsclient.BaseApplication;
import com.heima.newsclient.activity.HomeActivity;
import com.heima.newsclient.control.TabController;
import com.heima.newsclient.entity.NewsCenterBean;
import com.heima.newsclient.fragment.MenuFragment;
import com.heima.newsclient.utils.Constant;

import static com.android.volley.VolleyLog.TAG;

/**
 * @author SparkJzp
 * @date 2016/11/12
 * @describe 新闻页面的控制器
 */

public class NewsController extends TabController {
    private NewsCenterBean mNewsCenterBean;

    public NewsController(Context context) {
        super(context);
    }

    @Override
    public View initContentView() {
        TextView textView = new TextView(mContext);
        textView.setText("新闻中心");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20);
        return textView;
    }

    @Override
    public void initData() {
        mTvTitle.setText("新闻中心");
        Log.d(TAG, "onResponse:+++++++++ " + "从网络下载数据");
        //        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
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
        request.setTag(Constant.TAG_REQUEST_NEWS);
        BaseApplication.getHttpQueue().add(request);
    }

    /**
     * 设置左边菜单的数据
     */
    private void performSetMenuData() {
        HomeActivity activity = (HomeActivity) mContext;
        MenuFragment menuFragment = activity.getMenuFragment();
        menuFragment.setData(mNewsCenterBean.data);
    }
}
