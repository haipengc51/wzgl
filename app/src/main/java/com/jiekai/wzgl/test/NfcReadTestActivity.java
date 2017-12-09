package com.jiekai.wzgl.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.utils.GlidUtils;
import com.jiekai.wzgl.utils.ftputils.FTPUtils;
import com.jiekai.wzgl.utils.ftputils.FtpCallBack;
import com.jiekai.wzgl.utils.ftputils.FtpManager;
import com.jiekai.wzgl.utils.nfcutils.NfcUtils;
import com.jiekai.wzgl.utils.zxing.CaptureActivity;
import com.jiekai.wzgl.utils.zxing.encoding.EncodingUtils;

/**
 * Created by laowu on 2017/12/1.
 */

public class NfcReadTestActivity extends NFCBaseActivity implements View.OnClickListener {
    private final static int SCAN = 0;

    private TextView nfcTextView;
    private Button upImage;
    private ImageView image1;
    private ImageView image2;
    private Button scan;
    private Button createCode;
    private String nfcString ;

    private FTPUtils ftpUtils = null;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initOperation() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_nfc);
        nfcTextView = (TextView) findViewById(R.id.nfc_read);
        upImage = (Button) findViewById(R.id.up_image);

        image1 = (ImageView) findViewById(R.id.imag1);
        image2 = (ImageView) findViewById(R.id.image2);
        scan = (Button) findViewById(R.id.scan);
        createCode = (Button) findViewById(R.id.create_code);
        GlidUtils.displayImage(this, "http://114.115.171.225/View/AppImage/test.jpg", image1);

        upImage.setOnClickListener(this);
        scan.setOnClickListener(this);
        createCode.setOnClickListener(this);
        NfcUtils.readNfc(getIntent());
    }

    @Override
    public void getNfcData(String nfcString) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        NfcUtils.readNfc(getIntent());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.up_image:
                upImage();
                break;
            case R.id.scan:
                startActivityForResult(new Intent(NfcReadTestActivity.this, CaptureActivity.class), SCAN);
                break;
            case R.id.create_code:
                Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                //        Bitmap qr = EncodingUtils.createQRCode(phone, CommonUtil.dip2px(ContactsAddFriendActivity.this, 200), CommonUtil.dip2px(ContactsAddFriendActivity.this, 200), logo);
                Bitmap qr = EncodingUtils.createQRCode("liuhaipeng", 250, 250, logo);
                image2.setImageBitmap(qr);
                break;
        }
    }

    private void upImage() {
        FtpManager.getInstance().uploadFile(Environment.getExternalStorageDirectory() + "/123.jpg",
                "/test/", "test.jpg", new FtpCallBack() {
                    @Override
                    public void ftpSuccess(String remotePath) {
                        Toast.makeText(NfcReadTestActivity.this, remotePath, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void ftpFaild(String error) {
                        Toast.makeText(NfcReadTestActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN && resultCode == RESULT_OK) {
            String code = data.getExtras().getString("result");
            nfcTextView.setText(code);
        }
    }
}
