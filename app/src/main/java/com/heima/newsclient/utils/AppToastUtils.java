package com.heima.newsclient.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.heima.newsclient.R;

/**
 * @author SparkJzp
 * @date 2016/11/16
 * @describe TODO
 */

public class AppToastUtils {
    public static Builder builder(Context context) {
        return new Builder(context);
    }

    public static class Builder {
        private Context mContext;
        private String mContent = "";
        private int mDuration = Toast.LENGTH_SHORT;
        private boolean mHideIcon = false;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder content(int resId) {
            return content(mContext.getResources().getString(resId));
        }

        public Builder content(String content) {
            mContent = content;
            return this;
        }

        public Builder duration(int duration) {
            mDuration = duration;
            return this;
        }

        public Builder hideIcon(boolean hideIcon) {
            mHideIcon = hideIcon;
            return this;
        }
        public Toast build() {
            View layout = View.inflate(mContext, R.layout.view_app_toast,null);
            TextView content = (TextView) layout.findViewById(R.id.textview_content);
            content.setText(mContent);
            if(mHideIcon) {
                ImageView icon = (ImageView)layout.findViewById(R.id.imageview_icon);
                icon.setVisibility(View.GONE);
            }
            Toast toast = new Toast(mContext);
            toast.setDuration(mDuration);
            toast.setView(layout);

            return toast;
        }
    }
}
