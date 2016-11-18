package com.heima.newsclient.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.heima.newsclient.R;
import com.heima.newsclient.global.Constant;
import com.heima.newsclient.utils.PrefUtils;
import com.heima.newsclient.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author SparkJzp
 * @date 2016/11/17
 * @describe TODO
 */
public class NewsDetailActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.iv_text_size)
    ImageView mIvTextSize;
    private int mChooseWhich;
    private static final String URL = "http://www.jianshu.com/";
    private WebSettings mWebSettings;
    private int mSelectItem = 2;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        initData();
        initListener();
    }

    /**
     * 事件处理
     */
    private void initListener() {
        mIvBack.setOnClickListener(this);
        mIvTextSize.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //testUrl
        mUrl = "http://www.jianshu.com/";
//        String url = getIntent().getStringExtra("url");
        mWebView.loadUrl(mUrl);

        //初始化Web
        initWebView();
    }

    /**
     * 初始化Web设置
     */
    private void initWebView() {
        mWebSettings = mWebView.getSettings();
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebClient());
        mChooseWhich = PrefUtils.getInt(Constant.KEY_NEWS_TEXT_SIZE, mSelectItem, this);
        performUpdateTextSize();
    }
    class MyWebClient extends WebViewClient{
        //网页的内部链接点击之后会执行该方法
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.contains("detu.com")){
                view.loadUrl(URL);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_text_size:
                //显示
                showTextSizeDialog();
                break;
        }
    }

    /**
     * 设置字体大小的diaog
     */
    private void showTextSizeDialog() {
        AlertDialog.Builder buider=new AlertDialog.Builder(this);
        buider.setTitle("设置字体的大小");
        String[] items = new String[] { "超大号字体", "大号字体", "正常字体", "小号字体",
                "超小号字体" };
        buider.setSingleChoiceItems(items, mSelectItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mChooseWhich=which;
                PrefUtils.putInt(Constant.KEY_NEWS_TEXT_SIZE,which, UIUtils.getContext());
            }
        });
        buider.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                performUpdateTextSize();

            }
        });
        buider.setNegativeButton("取消",null);
        buider.show();
    }

    /**
     * 更新字体
     */
    private void performUpdateTextSize() {
        switch (mChooseWhich) {
            case 0:
                mWebSettings.setTextSize(WebSettings.TextSize.LARGEST);
                break;
            case 1:
                mWebSettings.setTextSize(WebSettings.TextSize.LARGER);
                break;
            case 2:
                mWebSettings.setTextSize(WebSettings.TextSize.NORMAL);
                break;
            case 3:
                mWebSettings.setTextSize(WebSettings.TextSize.SMALLER);
                break;
            case 4:
                mWebSettings.setTextSize(WebSettings.TextSize.SMALLEST);
                break;

            default:
                break;
        }

        mSelectItem = mChooseWhich;
        }


}
