package com.heima.newsclient.utils;

import java.text.DecimalFormat;

/**
 * @author SparkJzp
 * @date 2016/11/16
 * @describe 文件大小格式工具
 */

public class FileSizeFormatUtil {
    public static long KB = 1024;
    public static long MB = KB*KB;
    public static long GB = MB*KB;

    public static String formatFileSize(long fileSize) {
        DecimalFormat df1 = new DecimalFormat("####.#");
        DecimalFormat df2 = new DecimalFormat("####.##");
        String formatString = "";
        if(fileSize < 100) {
            formatString = fileSize + "B";
        } else if(fileSize < 10 * KB) {
            formatString = df2.format(1.0f * fileSize / KB) + "KB";
        } else if(fileSize < 100 * KB) {
            formatString = df1.format(1.0f * fileSize / KB) + "KB";
        } else if(fileSize < 10 * MB) {
            formatString = df2.format(1.0f * fileSize / MB) + "MB";
        } else if(fileSize < 100 * MB) {
            formatString = df1.format(1.0f * fileSize / MB) + "MB";
        } else if(fileSize < 10 * GB) {
            formatString = df2.format(1.0f * fileSize / GB) + "GB";
        } else if(fileSize < 100 * GB) {
            formatString = df1.format(1.0f * fileSize / GB) + "GB";
        }

        return formatString;
    }
}
