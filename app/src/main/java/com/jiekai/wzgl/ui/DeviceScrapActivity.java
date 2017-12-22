package com.jiekai.wzgl.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.config.Config;
import com.jiekai.wzgl.config.Constants;
import com.jiekai.wzgl.config.SqlUrl;
import com.jiekai.wzgl.entity.DeviceEntity;
import com.jiekai.wzgl.test.NFCBaseActivity;
import com.jiekai.wzgl.utils.GlidUtils;
import com.jiekai.wzgl.utils.PictureSelectUtils;
import com.jiekai.wzgl.utils.StringUtils;
import com.jiekai.wzgl.utils.dbutils.DBManager;
import com.jiekai.wzgl.utils.dbutils.DbCallBack;
import com.jiekai.wzgl.utils.ftputils.FtpCallBack;
import com.jiekai.wzgl.utils.ftputils.FtpManager;
import com.jiekai.wzgl.utils.zxing.CaptureActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laowu on 2017/12/22.
 * 设备报废
 */

public class DeviceScrapActivity extends NFCBaseActivity implements View.OnClickListener{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.device_name)
    TextView deviceName;
    @BindView(R.id.device_xinghao)
    TextView deviceXinghao;
    @BindView(R.id.read_card)
    TextView readCard;
    @BindView(R.id.device_id)
    TextView deviceId;
    @BindView(R.id.sao_ma)
    TextView saoMa;
    @BindView(R.id.image_show)
    ImageView imageShow;
    @BindView(R.id.choose_picture)
    TextView choosePicture;
    @BindView(R.id.enter)
    TextView enter;
    @BindView(R.id.cancle)
    TextView cancle;

    private List<LocalMedia> choosePictures = new ArrayList<>();
    private AlertDialog alertDialog;
    private DeviceEntity currentDevice;

    @Override
    public void initView() {
        setContentView(R.layout.activity_device_scrap);
    }

    @Override
    public void initData() {
        title.setText(getResources().getString(R.string.device_scrap));

        back.setOnClickListener(this);
        readCard.setOnClickListener(this);
        saoMa.setOnClickListener(this);
        choosePicture.setOnClickListener(this);
        imageShow.setOnClickListener(this);
        enter.setOnClickListener(this);
        cancle.setOnClickListener(this);
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
            case R.id.cancle:
                finish();
                break;
            case R.id.read_card:    //读卡
                nfcEnable = true;
                alertDialog.show();
                break;
            case R.id.sao_ma:       //扫码
                startActivityForResult(new Intent(mActivity, CaptureActivity.class), Constants.SCAN);
                break;
            case R.id.image_show:   //显示图片
                if (choosePictures != null && choosePictures.size() != 0) {
                    PictureSelectUtils.previewPicture(mActivity, choosePictures);
                }
                break;
            case R.id.choose_picture:   //选择图片
                PictureSelectUtils.choosePicture(mActivity, Constants.REQUEST_PICTURE);
                break;
            case R.id.enter:    //确定
                deviceScrap();
                break;
        }
    }

    /**
     * 通过ID卡号获取设备信息
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
                            currentDevice = (DeviceEntity) result.get(0);
                            deviceName.setText(currentDevice.getMC());
                            deviceXinghao.setText(currentDevice.getXH());
                            deviceId.setText(currentDevice.getBH());
                        } else {
                            alert(getResources().getString(R.string.no_data));
                        }
                    }
                });
    }

    private void deviceScrap() {
        uploadImage();
    }

    private void uploadImage() {
        if (currentDevice == null) {
            alert(getResources().getString(R.string.choose_scrap_device));
            return;
        }
        if (choosePictures == null || choosePictures.size() == 0) {
            alert(getResources().getString(R.string.please_choose_image));
            return;
        }
        final String localPath = choosePictures.get(0).getCompressPath();
        final String fileType = localPath.substring(localPath.lastIndexOf("."));
        final String romoteName = userData.getUSERID() + currentDevice.getBH().toString() + System.currentTimeMillis();
        FtpManager.getInstance().uploadFile(localPath,
                Config.BINDIMAGE_PATH, romoteName + fileType, new FtpCallBack() {
                    @Override
                    public void ftpStart() {
                        showProgressDialog(getResources().getString(R.string.uploading_image));
                    }

                    @Override
                    public void ftpSuccess(String remotePath) {
                        dismissProgressDialog();
//                        saveImagePathToRemoteDB(romoteName,
//                                FileSizeUtils.getAutoFileOrFilesSize(localPath),
//                                Config.BINDIMAGE_PATH + romoteName + fileType,
//                                fileType);
                    }

                    @Override
                    public void ftpFaild(String error) {
                        alert(error);
                        dismissProgressDialog();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_PICTURE && resultCode == RESULT_OK) {  //选择图片回调
            choosePictures = PictureSelector.obtainMultipleResult(data);
            if (choosePictures != null && choosePictures.size() != 0) {
                String currentDevicePicturePath = choosePictures.get(0).getCompressPath();
                GlidUtils.displayImage(mActivity, currentDevicePicturePath, imageShow);
            }
        } else if (requestCode == Constants.SCAN && resultCode == RESULT_OK) {  //扫码回到
            String code = data.getExtras().getString("result");
            getDeviceDataById(code);
        }
    }
}
