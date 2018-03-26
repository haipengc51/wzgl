package com.jiekai.wzglkg.test;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jiekai.wzglkg.ui.base.MyBaseActivity;
import com.jiekai.wzglkg.ui.dialog.ReadCardErroDialog;
import com.jiekai.wzglkg.utils.nfcutils.NfcUtils;

/**
 * Created by laowu on 2017/12/1.
 */

public abstract class NFCBaseActivity extends MyBaseActivity {
    public boolean nfcEnable = false;

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;

    protected ReadCardErroDialog readCardErroDialog;

    /**
     * 获取到nfc卡的信息
     * @param nfcString
     */
    public abstract void getNfcData(String nfcString);
    //TODO 启动模式一应设置成singleTop，否则每次都走onCreate()


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readCardErroDialog = new ReadCardErroDialog(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (nfcEnable) {
            getNfcData(NfcUtils.getNFCNum(intent));
        }
    }
}
