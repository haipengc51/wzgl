package com.jiekai.wzglkg.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.jiekai.wzglkg.R;

/**
 * Created by laowu on 2017/12/18.
 */

public class ReadNfcCard {
    private AlertDialog alertDialog;
    private boolean nfcEnable = false;

    public ReadNfcCard(Context context, TextView enterText) {
        alertDialog = new AlertDialog.Builder(context)
                .setTitle("")
                .setMessage(context.getResources().getString(R.string.please_nfc))
                .create();
        enterText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
