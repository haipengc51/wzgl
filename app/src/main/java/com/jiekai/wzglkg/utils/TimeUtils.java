package com.jiekai.wzglkg.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by laowu on 2017/12/22.
 * 时间转换工具类
 */

public class TimeUtils {
    /**
     * data转换成String
     * @param data
     * @param formatType
     * @return
     */
    public static String dateToString(Date data, String formatType) {
        if (data == null) {
            return null;
        }
        return new SimpleDateFormat(formatType).format(data);
    }

    /**
     * data转换成String
     * @param data
     * @return
     */
    public static String dateToStringYYYYmmdd(Date data) {
        if (data == null) {
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(data);
    }

    /**
     * data转换成String
     * @param data
     * @return
     */
    public static String dateToStringYYYYmmddHHMMSS(Date data) {
        if (data == null) {
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(data);
    }
}
