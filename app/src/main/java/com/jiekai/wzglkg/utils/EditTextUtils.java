package com.jiekai.wzglkg.utils;

import android.app.Activity;
import android.text.InputType;
import android.view.WindowManager;
import android.widget.EditText;

import java.lang.reflect.Method;

/**
 * Created by LaoWu on 2018/2/27.
 * editText控件的方法类
 */

public class EditTextUtils {
    /**
     * 点击EditText获取到焦点，设置软键盘是否也同步弹出
     * @param isShowOnClick
     */
    public static void setEditSoftKeywordShow(Activity mActivity, EditText editText, boolean isShowOnClick) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            if (isShowOnClick) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
            } else {
                editText.setInputType(InputType.TYPE_NULL);
            }
        } else {
            mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setSoftInputShownOnFocus;
                setSoftInputShownOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setSoftInputShownOnFocus.setAccessible(isShowOnClick);
                setSoftInputShownOnFocus.invoke(editText, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
