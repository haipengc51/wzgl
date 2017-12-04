package com.jiekai.wzgl.utils;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by laowu on 2017/12/4.
 * glide加载图片工具类
 */

public class GlidUtils {

    public static void displayImage(Activity activity, String url, ImageView imageView) {
        Glide.with(activity).load(url).into(imageView);
    }
}
