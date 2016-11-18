package com.heima.newsclient.control.menu;

import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.heima.newsclient.R;
import com.heima.newsclient.adapter.MenuPicAdapter;
import com.heima.newsclient.control.BaseController;
import com.heima.newsclient.entity.NewsPicBean;
import com.heima.newsclient.global.Constant;

import java.io.UnsupportedEncodingException;

/**
 * @author SparkJzp
 * @date 2016/11/12
 * @describe  组图
 */

public class MenuPicController extends BaseController {

    private ListView mListView;
    private NewsPicBean mNewsPicBean;
    private GridView mGridView;

    public MenuPicController(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        //填入listView
        View view = View.inflate(mContext, R.layout.controller_pic, null);
        mListView = (ListView) view.findViewById(R.id.lv_list);
        mGridView = (GridView) view.findViewById(R.id.gv_list);
        return view;

    }

    @Override
    public void initData() {
        //适配器
        performDataFromNetwork();

    }

    /**
     * 从服务端请求数据
     */
    private void performDataFromNetwork() {
        String url= Constant.URL_PICS;
        final RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest request=new StringRequest(Request.Method.GET
                , url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(mContext, "请求数据成功", Toast.LENGTH_SHORT).show();
                try {
                    response=new String(response.getBytes("ISO-8859-1"),"UTF-8");
                    Gson gson=new Gson();
                    mNewsPicBean = gson.fromJson(response, NewsPicBean.class);

                    setAdapter();

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "网络数据获取失败", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

    /**
     * 设置适配器
     */

    private void setAdapter() {
        MenuPicAdapter adapter=new MenuPicAdapter(mNewsPicBean.getData().getNews(),mContext);
        mListView.setAdapter(adapter);
        mGridView.setAdapter(adapter);
    }
    /**
     * 显示gridView
     */
    public void showGird(){

        mListView.setVisibility(View.GONE);
        mGridView.setVisibility(View.VISIBLE);
    }

    public void showList(){
        mListView.setVisibility(View.VISIBLE);
        mGridView.setVisibility(View.GONE);
    }

    /**
     * 判断该控件是否显示
     * @return
     */
    public boolean isListShow(){
        return mListView.isShown();
    }
}
