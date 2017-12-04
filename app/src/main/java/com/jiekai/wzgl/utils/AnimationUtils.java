package com.jiekai.wzgl.utils;

import android.app.Activity;

import com.jiekai.wzgl.R;

/**
 * Created by laowu on 2017/12/3.
 */

public class AnimationUtils {

    /**
     * 左边进入，左边退出
     * @param activity
     */
    public static void setAnimationOfLeft(Activity activity) {
        activity.overridePendingTransition(R.anim.animation_left_in, R.anim.animation_left_out);
    }

    /**
     * 右边进入，右边退出
     * @param activity
     */
    public static void setAnimationOfRight(Activity activity) {
        activity.overridePendingTransition(R.anim.animation_right_in, R.anim.animation_right_out);
    }

    /**
     * 上边进入 上面退出
     * @param activity
     */
    public static void setAnimationOfTop(Activity activity) {
        activity.overridePendingTransition(R.anim.animation_top_in, R.anim.animation_top_out);
    }

    /**
     * 下面进入 下面退出
     * @param activity
     */
    public static void setAnimationOfBottom(Activity activity) {
        activity.overridePendingTransition(R.anim.animation_bottom_in, R.anim.animation_bottom_out);
    }

    /**
     * 缩放进入 缩放退出
     * @param activity
     */
    public static void setAnimationOfScale(Activity activity) {
        activity.overridePendingTransition(R.anim.animation_scale_in, R.anim.animation_scale_out);
    }

    /**
     * 淡入 淡出
     * @param activity
     */
    public static void setAnimationOfFade(Activity activity) {
        activity.overridePendingTransition(R.anim.animation_fade_in, R.anim.animation_fade_out);
    }
}
