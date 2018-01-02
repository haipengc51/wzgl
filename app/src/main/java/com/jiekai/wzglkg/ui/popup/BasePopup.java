package com.jiekai.wzglkg.ui.popup;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jiekai.wzglkg.R;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by laowu on 2017/12/12.
 */

public class BasePopup {
    public Context context;
    private TextView popTitle;
    public ListView popList;
    public PopupWindow popupWindow;

    private ProgressDialog progressDialog;

    public BasePopup(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context)
                .inflate(R.layout.spinner_base_popup_window, null);
        popTitle = (TextView) view.findViewById(R.id.pop_title);
        popList = (ListView) view.findViewById(R.id.pop_list);

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
            popupWindow.showAsDropDown(parentView);//, Gravity.BOTTOM, 0, parentView.getHeight());
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

    public void setPopTitle(String title) {
        if (popTitle != null && title != null) {
            popTitle.setText(title);
        }
    }

    public void showProgressDialog(String msg) {
        dismissProgressDialog();
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle(context.getResources().getString(R.string.please_wait));
        }
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void alert(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void alert(int strId) {
        Toast.makeText(context, strId, Toast.LENGTH_SHORT).show();
    }
}
