package com.jiekai.wzgl.utils;

/**
 * Created by LaoWu on 2017/12/6.
 * 字符串工具类
 */

public class StringUtils {
    /**
     * 判断字符串是否为空
     * @param msg
     * @return
     */
    public static boolean isEmpty(String msg) {
        if (msg == null || msg.length() == 0) {
            return true;
        }
        return false;
    }
}
