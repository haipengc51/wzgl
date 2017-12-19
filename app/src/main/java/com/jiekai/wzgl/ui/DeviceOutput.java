package com.jiekai.wzgl.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.config.Constants;
import com.jiekai.wzgl.config.IntentFlag;
import com.jiekai.wzgl.config.SqlUrl;
import com.jiekai.wzgl.entity.DeviceEntity;
import com.jiekai.wzgl.test.NFCBaseActivity;
import com.jiekai.wzgl.utils.GlidUtils;
import com.jiekai.wzgl.utils.PictureSelectUtils;
import com.jiekai.wzgl.utils.StringUtils;
import com.jiekai.wzgl.utils.dbutils.DBManager;
import com.jiekai.wzgl.utils.dbutils.DbCallBack;
import com.jiekai.wzgl.utils.zxing.CaptureActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LaoWu on 2017/12/16.
 * 设备出库
 */

public class DeviceOutput extends NFCBaseActivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.out_image)
    ImageView outImage;
    @BindView(R.id.choose_picture)
    TextView choosePicture;
    @BindView(R.id.wzmc_cache)
    TextView wzmcCache;
    @BindView(R.id.wzmc)
    TextView wzmc;
    @BindView(R.id.sbzbm_cache)
    TextView sbzbmCache;
    @BindView(R.id.sbzbm)
    TextView sbzbm;
    @BindView(R.id.syjh_cache)
    TextView syjhCache;
    @BindView(R.id.syjh)
    TextView syjh;
    @BindView(R.id.read_card)
    TextView readCard;
    @BindView(R.id.recognize)
    TextView recognize;
    @BindView(R.id.enter)
    TextView enter;
    @BindView(R.id.cancle)
    TextView cancle;

    private List<LocalMedia> choosePictures = new ArrayList<>();
    private AlertDialog alertDialog;
    private String outMc;
    private String outXh;

    private DeviceEntity deviceEntity;

    public static void startForResult(Activity activity, int ResultCode, String outMC,
                                      String outXH) {
        Intent intent = new Intent();
        intent.setClass(activity, DeviceOutput.class);
        intent.putExtra(IntentFlag.MC, outMC);
        intent.putExtra(IntentFlag.XH, outXH);
        activity.startActivityForResult(intent, ResultCode);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_device_output);
    }

    @Override
    public void initData() {
        title.setText(getResources().getString(R.string.device_output));

        outMc = getIntent().getStringExtra(IntentFlag.MC);
        outXh = getIntent().getStringExtra(IntentFlag.XH);

        back.setOnClickListener(this);
        choosePicture.setOnClickListener(this);
        outImage.setOnClickListener(this);
        readCard.setOnClickListener(this);
        recognize.setOnClickListener(this);
        enter.setOnClickListener(this);
    }

    @Override
    public void initOperation() {
        alertDialog = new AlertDialog.Builder(mActivity)
                .setTitle("")
                .setMessage(getResources().getString(R.string.please_nfc))
                .create();
    }

    @Override
    public void getNfcData(String nfcString) {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        nfcEnable = false;
        //读取设备自编码和设备名称使用井号
        getDeviceDataById(nfcString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.choose_picture:
                PictureSelectUtils.choosePicture(mActivity, Constants.REQUEST_PICTURE);
                break;
            case R.id.out_image:
                if (choosePictures != null && choosePictures.size() != 0)
                    PictureSelectUtils.previewPicture(mActivity, choosePictures);
                break;
            case R.id.read_card:        //读卡
                nfcEnable = true;
                alertDialog.show();
                break;
            case R.id.recognize:    //扫码
                startActivityForResult(new Intent(mActivity, CaptureActivity.class), Constants.SCAN);
                break;
            case R.id.enter:
                deviceOut();
                break;
        }
    }

    /**
     * 通过扫描到的id号获取设备名称，自编码，使用井号
     *
     * @param id
     */
    private void getDeviceDataById(String id) {
        if (StringUtils.isEmpty(id)) {
            return;
        }
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GetDeviceByID)
                .params(new String[]{id})
                .clazz(DeviceEntity.class)
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(getResources().getString(R.string.loading_device));
                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        dismissProgressDialog();
                        if (result != null && result.size() != 0) {
                            deviceEntity = (DeviceEntity) result.get(0);
                            wzmc.setText(deviceEntity.getMC());
                            sbzbm.setText(deviceEntity.getBH());
                            checkDevice();
                        } else {
                            alert(getResources().getString(R.string.no_data));
                        }
                    }
                });
    }

    private boolean checkDevice() {
        if (outMc != null && outMc.equals(deviceEntity.getMC()) &&
                outXh != null && outXh.equals(deviceEntity.getXH())) {
            return true;
        } else {
            alert(getResources().getString(R.string.out_device_erro));
            return false;
        }
    }

    /**
     * 执行设备出库的操作
     * @return
     */
    private void deviceOut() {
//        DBManager.dbDeal(DBManager.UPDATA)
//                .sql(SqlUrl.OUT_DEVICE)
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_PICTURE && resultCode == RESULT_OK) {
            choosePictures = PictureSelector.obtainMultipleResult(data);
            if (choosePictures != null && choosePictures.size() != 0) {
                String currentDevicePicturePath = choosePictures.get(0).getCompressPath();
                GlidUtils.displayImage(mActivity, currentDevicePicturePath, outImage);
            }
        } else if (requestCode == Constants.SCAN && resultCode == RESULT_OK) {
            String code = data.getExtras().getString("result");
            getDeviceDataById(code);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PictureSelectUtils.clearPictureSelectorCache(DeviceOutput.this);
    }
}
