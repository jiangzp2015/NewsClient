package com.heima.newsclient.global;

/**
 * @author SparkJzp
 * @date 2016/11/11
 * @describe 存储 常量 的接口
 */
public interface Constant {

    //服务器地址
    String URL_ROOT="http://192.168.29.98:8080/zhbj_v2";
    //新闻信息的地址
    String URL_NEWS=URL_ROOT+"/categories.json";
    //组图的地址
    String URL_PICS=URL_ROOT+"/photos/photos_1.json";

     //保持 是否完成指导页面
    String KEY_FINISH_GUIDE = "finish_guide";

    //标记请求
    String TAG_REQUEST_NEWS="newsCenter_get";
    //标记已读
    String KEY_NEWSCENT_GET="read_ids";
    //Web字体的设置
    String KEY_NEWS_TEXT_SIZE = "text_size";
}
