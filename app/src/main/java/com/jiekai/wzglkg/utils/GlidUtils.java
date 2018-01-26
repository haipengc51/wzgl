package com.jiekai.wzglkg.utils;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jiekai.wzglkg.R;

/**
 * Created by laowu on 2017/12/4.
 * glide加载图片工具类
 */

public class GlidUtils {

    public static void displayImage(Activity activity, String url, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.ic_image_err);
        requestOptions.placeholder(R.drawable.ic_image_err);
        Glide.with(activity).setDefaultRequestOptions(requestOptions).load(url).into(imageView);
    }
}
