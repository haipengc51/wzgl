package com.jiekai.wzgl.ui;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.utils.CommonUtils;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by LaoWu on 2017/12/8.
 * popu windons 工具类
 */

public class PopupWindowUtils {
    private PopupWindow popupWindow;

    public PopupWindowUtils(Context context, View view) {
        popupWindow = new PopupWindow(view);
        popupWindow.setWidth(WRAP_CONTENT);
        popupWindow.setHeight(CommonUtils.getScreentHeight(context) - CommonUtils.getStatusBarHeight(context)
            - CommonUtils.dip2Px(context, 200));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.fade_in_popup);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        popupWindow.setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public void showCenter(View parentView) {
        if (popupWindow != null && !popupWindow.isShowing()) {
            popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
        }
    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }
}
