package com.jiekai.wzglkg.ui.popup;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.jiekai.wzglkg.R;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by LaoWu on 2017/12/16.
 * 弹出生成的二维码
 */

public class CodePopup {
    public Context context;
    private ImageView codeImage;
    public PopupWindow popupWindow;

    private ProgressDialog progressDialog;

    public CodePopup(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context)
                .inflate(R.layout.popup_create_code, null);
        codeImage = (ImageView) view.findViewById(R.id.code_image);

        popupWindow = new PopupWindow(view);
        popupWindow.setWidth(WRAP_CONTENT);
        popupWindow.setHeight(WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.fade_in_popup);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public void showCenter(View parentView) {
        if (popupWindow != null && !popupWindow.isShowing()) {
            popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);//, Gravity.BOTTOM, 0, parentView.getHeight());
        }
    }

    public void dismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    public void setCodeMap(Bitmap bitmap) {
        codeImage.setImageBitmap(bitmap);
    }
}
