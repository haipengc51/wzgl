package com.jiekai.wzglkg.utils.nfcutils;

import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by LaoWu on 2017/12/5.
 * 读取nfc记录的工具类
 */

public class NfcUtils {

    /**
     * 手机是否支持nfc
     * @param context
     * @return
     */
    public boolean isNfc(Context context) {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(context);
        return nfcAdapter != null;
    }

    /**
     * 手机是否打开了nfc功能
     * @param context
     * @return
     */
    public boolean nfcEnable(Context context) {
        if (NfcAdapter.getDefaultAdapter(context) != null && NfcAdapter.getDefaultAdapter(context).isEnabled()) {
            return true;
        }
        return false;
    }

    /**
     * 读取nfc的信息
     * @param intent
     */
    public static String readNfc(Intent intent) {
        Tag detecedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (detecedTag == null) {
            return null;
        }
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()) ||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction()) ||
                NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage msgs[] = null;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i=0; i<rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            if (msgs != null) {
                NdefRecord record = msgs[0].getRecords()[0];
                return parseTextRecord(record);
            }
        }
        return null;
    }

    private static String parseTextRecord(NdefRecord ndefRecord) {
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

    public static String getNFCNum(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] dataId = tag.getId();
        return bytesToHexString(dataId);// 字符序列转换为16进制字符串
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();

        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.toUpperCase(Character.forDigit(
                    (src[i] >>> 4) & 0x0F, 16));
            buffer[1] = Character.toUpperCase(Character.forDigit(src[i] & 0x0F,
                    16));
            System.out.println(buffer);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }
}
