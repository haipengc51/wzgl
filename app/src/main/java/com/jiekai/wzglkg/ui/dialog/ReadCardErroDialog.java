package com.jiekai.wzglkg.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import static android.content.DialogInterface.BUTTON_NEGATIVE;

/**
 * Created by LaoWu on 2018/3/26.
 * 读卡失败弹出卡号的界面
 */

public class ReadCardErroDialog {
    private Context context;
    private AlertDialog alertDialog;

    public ReadCardErroDialog(Context context) {
        this.context = context;
    }

    /**
     * @param id    要显示的编号
     * @param isNfc     true为NFC读卡，false为摄像头扫码
     */
    @SuppressLint("NewApi")
    public void errShowIdDialog(String id, boolean isNfc) {
        if (((Activity)context).isDestroyed() || ((Activity) context).isFinishing()) {
            return;
        }
        alertDialog = new AlertDialog.Builder(context).setTitle("提示").create();
        StringBuilder msg = new StringBuilder();
        msg.append("您");
        if (isNfc) {
            msg.append("读卡");
        } else {
            msg.append("扫码");
        }
        msg.append("的编号为：");
        msg.append(id);
        alertDialog.setMessage(msg.toString());
        alertDialog.setButton(BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.show();
    }
}
