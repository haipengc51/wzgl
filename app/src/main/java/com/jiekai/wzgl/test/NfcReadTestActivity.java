package com.jiekai.wzgl.test;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.jiekai.wzgl.R;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by laowu on 2017/12/1.
 */

public class NfcReadTestActivity extends NFCBaseActivity {
    private TextView nfcTextView;
    private String nfcString ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_nfc);
        nfcTextView = (TextView) findViewById(R.id.nfc_read);
        readNfcTag(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        readNfcTag(intent);
    }

    private void readNfcTag(Intent intent) {
        Tag detecedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (detecedTag == null) {
            return;
        }
        Ndef ndef = Ndef.get(detecedTag);
        nfcString = ndef.getType() + "\n" + "max size:" + ndef.getMaxSize() + "byte\n";

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()) ||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction()) ||
                NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage msgs[] = null;
            int contentSize = 0;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i=0; i<rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                    contentSize += msgs[i].toByteArray().length;
                }
            }
            if (msgs != null) {
                NdefRecord record = msgs[0].getRecords()[0];
                String textRecord = parseTextRecord(record);
                nfcString += textRecord + "\n\ntext\n" + contentSize + "byte\n";
            }
        }

        nfcTextView.setText(nfcString);
    }

    public static String parseTextRecord(NdefRecord ndefRecord) {
        try {
            if (ndefRecord.getTnf() != NdefRecord.TNF_WELL_KNOWN) {
                return null;
            }
            if (!Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                return null;
            }
            byte[] payload = ndefRecord.getPayload();
            String textEncoding = ((payload[0] & 0x80) == 0) ? "UTF-8" : "UTF-16";
            int languageCodeLength = payload[0] & 0x3f;
            String textRecord = new String(payload, languageCodeLength + 1,
                    payload.length - languageCodeLength - 1, textEncoding);
            return textRecord;
        } catch (Exception e) {
            throw  new IllegalArgumentException();
        }
    }
}
