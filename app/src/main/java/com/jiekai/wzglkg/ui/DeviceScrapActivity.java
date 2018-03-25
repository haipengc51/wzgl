package com.jiekai.wzglkg.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiekai.wzglkg.R;
import com.jiekai.wzglkg.config.Config;
import com.jiekai.wzglkg.config.Constants;
import com.jiekai.wzglkg.config.SqlUrl;
import com.jiekai.wzglkg.entity.DeviceEntity;
import com.jiekai.wzglkg.entity.DevicescrapEntity;
import com.jiekai.wzglkg.test.NFCBaseActivity;
import com.jiekai.wzglkg.utils.CommonUtils;
import com.jiekai.wzglkg.utils.FileSizeUtils;
import com.jiekai.wzglkg.utils.GlidUtils;
import com.jiekai.wzglkg.utils.PictureSelectUtils;
import com.jiekai.wzglkg.utils.StringUtils;
import com.jiekai.wzglkg.utils.dbutils.DBManager;
import com.jiekai.wzglkg.utils.dbutils.DbCallBack;
import com.jiekai.wzglkg.utils.dbutils.DbDeal;
import com.jiekai.wzglkg.utils.ftputils.FtpCallBack;
import com.jiekai.wzglkg.utils.ftputils.FtpManager;
import com.jiekai.wzglkg.utils.zxing.CaptureActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by laowu on 2017/12/22.
 * 设备报废
 */

public class DeviceScrapActivity extends NFCBaseActivity implements View.OnClickListener {

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
    @BindView(R.id.beizhu)
    EditText beizhu;

    private List<LocalMedia> choosePictures = new ArrayList<>();
    private AlertDialog alertDialog;
    private DeviceEntity currentDevice;

    private String imagePath;       //图片的远程地址 /out/123.jpg
    private String imageType;       //图片的类型     .jpg
    private String romoteImageName;     //图片远程服务器的名称 123.jpg
    private String localPath;   //图片本地的地址

    private boolean isScrap = false;

    private DbDeal dbDeal = null;
    private DbDeal eventDbDeal = null;

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
    public void progressDialogCancleLisen() {
        if (dbDeal != null) {
            dbDeal.cancleDbDeal();
            dismissProgressDialog();
        }
        if (eventDbDeal != null) {
            eventDbDeal.cancleDbDeal();
            dismissProgressDialog();
        }
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
                PictureSelectUtils.choosePicture(PictureSelector.create(this), Constants.REQUEST_PICTURE);
                break;
            case R.id.enter:    //确定
                deviceScrap();
                break;
        }
    }

    /**
     * 通过ID卡号获取设备信息
     *
     * @param id
     */
    private void getDeviceDataById(String id) {
        if (StringUtils.isEmpty(id)) {
            return;
        }
        dbDeal = DBManager.dbDeal(DBManager.SELECT);
                dbDeal.sql(SqlUrl.GetDeviceByID)
                .params(new String[]{id, id, id})
                .clazz(DeviceEntity.class)
                .execut(mContext, new DbCallBack() {
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
                            checkDevice();
                        } else {
                            alert(getResources().getString(R.string.no_data));
                        }
                    }
                });
    }

    /**
     * 通过二维码获取设备信息
     *
     * @param id
     */
    private void getDeviceDataBySAOMA(String id) {
        if (StringUtils.isEmpty(id)) {
            return;
        }
        dbDeal = DBManager.dbDeal(DBManager.SELECT);
                dbDeal.sql(SqlUrl.GetDeviceBySAOMA)
                .params(new String[]{id})
                .clazz(DeviceEntity.class)
                .execut(mContext, new DbCallBack() {
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
                            checkDevice();
                        } else {
                            alert(getResources().getString(R.string.no_data));
                        }
                    }
                });
    }

    /**
     * 查找该设备是否报废
     */
    private void checkDevice() {
        dbDeal = DBManager.dbDeal(DBManager.SELECT);
                dbDeal.sql(SqlUrl.GET_SCRAP_DEVICE)
                .params(new String[]{currentDevice.getBH()})
                .clazz(DevicescrapEntity.class)
                .execut(mContext, new DbCallBack() {
                    @Override
                    public void onDbStart() {

                    }

                    @Override
                    public void onError(String err) {

                    }

                    @Override
                    public void onResponse(List result) {
                        if (result != null && result.size() != 0) {
                            alert(R.string.device_already_scrap);
                            isScrap = true;
                        } else {
                            isScrap = false;
                        }
                    }
                });
    }

    private void deviceScrap() {
        if (isScrap) {
            alert(R.string.device_already_scrap);
            return;
        }
        if (currentDevice == null) {
            alert(getResources().getString(R.string.choose_scrap_device));
            return;
        }
        if (choosePictures == null || choosePictures.size() == 0) {
            alert(getResources().getString(R.string.please_choose_image));
            return;
        }
        uploadImage();
    }

    private void uploadImage() {
        localPath = choosePictures.get(0).getCompressPath();
        imageType = localPath.substring(localPath.lastIndexOf("."));
        romoteImageName = userData.getUSERID() + currentDevice.getBH().toString() + System.currentTimeMillis() + imageType;
        FtpManager.getInstance().uploadFile(localPath,
                Config.SCRAP_PATH, romoteImageName, new FtpCallBack() {
                    @Override
                    public void ftpStart() {
                        showProgressDialog(getResources().getString(R.string.uploading_image));
                    }

                    @Override
                    public void ftpProgress(long allSize, long currentSize, int process) {

                    }

                    @Override
                    public void ftpSuccess(String remotePath) {
                        dismissProgressDialog();
                        imagePath = Config.FTP_PATH_HANDLER + remotePath;
                        startEvent();
                    }

                    @Override
                    public void ftpFaild(String error) {
                        alert(error);
                        dismissProgressDialog();
                    }
                });
    }

    private void deletImage() {
        String path = Config.SCRAP_PATH + romoteImageName;
        if (StringUtils.isEmpty(path)) {
            return;
        }
        FtpManager.getInstance().deletFile(path, new FtpCallBack() {
            @Override
            public void ftpStart() {

            }

            @Override
            public void ftpProgress(long allSize, long currentSize, int process) {

            }

            @Override
            public void ftpSuccess(String remotePath) {

            }

            @Override
            public void ftpFaild(String error) {

            }
        });
    }

    /**
     * 开启数据库事务
     */
    private void startEvent() {
        eventDbDeal = DBManager.dbDeal(DBManager.START_EVENT);
                eventDbDeal.execut(mContext, new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(getResources().getString(R.string.uploading_db));
                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        deletImage();
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        insertScrap();
                    }
                });
    }

    private void insertScrap() {
        if (eventDbDeal == null) {
            dismissProgressDialog();
            deletImage();
            return;
        }
        eventDbDeal.reset(DBManager.EVENT_INSERT)
                .sql(SqlUrl.ADD_DEVICE_SCRAP)
                .params(new Object[]{currentDevice.getBH(),
                        new Date(new java.util.Date().getTime()), userData.getUSERID(),
                        CommonUtils.getDataIfNull(beizhu.getText().toString())})
                .execut(mContext, new DbCallBack() {
                    @Override
                    public void onDbStart() {
                    }

                    @Override
                    public void onError(String err) {
                        dismissProgressDialog();
                        alert(err);
                        deletImage();
                    }

                    @Override
                    public void onResponse(List result) {
                        insertImagePath();
                    }
                });
    }

    /**
     * 图片路径插入到数据库中
     * SBBH就是上次插入的id
     */
    private void insertImagePath() {
        String fileSize = FileSizeUtils.getAutoFileOrFilesSize(localPath);
        if (StringUtils.isEmpty(fileSize)) {
            rollback();
            deletImage();
            dismissProgressDialog();
            return;
        }
        if (eventDbDeal == null) {
            dismissProgressDialog();
            rollback();
            deletImage();
            return;
        }
        eventDbDeal.reset(DBManager.EVENT_INSERT)
                .sql(SqlUrl.INSERT_IAMGE)
                .params(new String[]{currentDevice.getBH(), romoteImageName, fileSize, imagePath, imageType, Config.doc_sbbf})
                .execut(mContext, new DbCallBack() {
                    @Override
                    public void onDbStart() {

                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        dismissProgressDialog();
                        rollback();
                        deletImage();
                    }

                    @Override
                    public void onResponse(List result) {
                        changeDeviceState();
                    }
                });
    }

    private void changeDeviceState() {
        if (eventDbDeal == null) {
            dismissProgressDialog();
            rollback();
            deletImage();
            return;
        }
        eventDbDeal.reset(DBManager.EVENT_UPDATA)
                .sql(SqlUrl.CHANGE_DEVICE_STATE)
                .params(new String[]{"4", currentDevice.getBH()})
                .execut(mContext, new DbCallBack() {
                    @Override
                    public void onDbStart() {

                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        dismissProgressDialog();
                        rollback();
                        deletImage();
                    }

                    @Override
                    public void onResponse(List result) {
                        commit();
                    }
                });
    }

    private void rollback() {
        if (eventDbDeal == null) {
            dismissProgressDialog();
            return;
        }
        eventDbDeal.reset(DBManager.ROLLBACK)
                .execut(mContext, new DbCallBack() {
                    @Override
                    public void onDbStart() {

                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        dismissProgressDialog();
                        alert(R.string.device_scrap_faild);
                    }
                });
    }

    private void commit() {
        if (eventDbDeal == null) {
            dismissProgressDialog();
            return;
        }
        eventDbDeal.reset(DBManager.COMMIT)
                .execut(mContext, new DbCallBack() {
                    @Override
                    public void onDbStart() {

                    }

                    @Override
                    public void onError(String err) {
                        alert(R.string.device_scrap_faild);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        alert(R.string.device_scrap_success);
                        dismissProgressDialog();
                        finish();
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
            getDeviceDataBySAOMA(code);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PictureSelectUtils.clearPictureSelectorCache(this);
    }
}
