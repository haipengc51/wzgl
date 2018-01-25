package com.jiekai.wzglkg.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by laowu on 2018/1/19.
 * 权限管理工具类
 */

public class PermissionUtils {
    /**
     * 判断权限是否开启
     * @param context
     * @param permission
     * @return 开启返回true 拒绝返回false
     */
    public static boolean checkPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Activity activity, int requstCode, String... permission) {
        ActivityCompat.requestPermissions(activity, permission, requstCode);
    }
}
