package com.jiekai.wzglkg.ui.update;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by LaoWu on 2018/3/13.
 * 弹出窗口的基类
 */

public abstract class BaseDialogFragment extends DialogFragment {
    protected boolean mIsOutCanback = true;       //点击弹出窗口外面可以消失
    protected boolean mIsKeyCanback = true;       //点击back键可以消失

    protected abstract View bindView(LayoutInflater inflater, ViewGroup container);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        getDialog().setCanceledOnTouchOutside(mIsOutCanback);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return !mIsKeyCanback;
                } else {
                    return false;
                }
            }
        });
        View view = bindView(inflater, container);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //可以初始化界面的宽高
        WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes(layoutParams);
    }

    public void hideDialog() {
        try {
            Dialog dialog = getDialog();
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
