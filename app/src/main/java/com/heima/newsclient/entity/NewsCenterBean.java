package com.heima.newsclient.entity;

import java.util.List;

/**
 * @author SparkJzp
 * @date 2016/11/12
 * @describe TODO
 */

public class NewsCenterBean {
    public List<DataBean> data;
    public List<Integer> extend;
    public int retcode;

    public class DataBean {
        public List<ChildBean> children;
        public int id;
        public String title;
        public int type;
        public String url;
        public String url1;
        public String dayurl;
        public String excurl;
        public String weekurl;

        public class ChildBean{
            public int id;
            public String title;
            public int type;
            public String url;
        }
    }

}
