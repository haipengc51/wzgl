package com.jiekai.wzgl.test;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;

import com.jiekai.wzgl.ui.base.MyBaseActivity;

/**
 * Created by laowu on 2017/12/1.
 */

public class NFCBaseActivity extends MyBaseActivity {
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;

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
}
