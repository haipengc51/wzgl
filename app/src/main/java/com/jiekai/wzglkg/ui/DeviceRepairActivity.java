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
import com.jiekai.wzglkg.entity.LastInsertIdEntity;
import com.jiekai.wzglkg.test.NFCBaseActivity;
import com.jiekai.wzglkg.ui.popup.DeviceCodePopup;
import com.jiekai.wzglkg.utils.CommonUtils;
import com.jiekai.wzglkg.utils.FileSizeUtils;
import com.jiekai.wzglkg.utils.GlidUtils;
import com.jiekai.wzglkg.utils.PictureSelectUtils;
import com.jiekai.wzglkg.utils.StringUtils;
import com.jiekai.wzglkg.utils.dbutils.DBManager;
import com.jiekai.wzglkg.utils.dbutils.DbCallBack;
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
 * Created by laowu on 2017/12/23.
 * 设备维修界面
 */

public class DeviceRepairActivity extends NFCBaseActivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.repair_image)
    ImageView repairImage;
    @BindView(R.id.choose_picture)
    TextView choosePicture;
    @BindView(R.id.repair_type)
    TextView repairType;
    @BindView(R.id.device_name)
    TextView deviceName;
    @BindView(R.id.read_card)
    TextView readCard;
    @BindView(R.id.device_id)
    TextView deviceId;
    @BindView(R.id.sao_ma)
    TextView saoMa;
    @BindView(R.id.enter)
    TextView enter;
    @BindView(R.id.cancle)
    TextView cancle;
    @BindView(R.id.beizhu)
    EditText beizhu;

    private List<LocalMedia> choosePictures = new ArrayList<>();
    private AlertDialog alertDialog;
    private DeviceEntity currentDevice;
    private String currentRepairType = null;

    private DeviceCodePopup repairTypePop;
    private List<String> repairTypeData = new ArrayList<>();

    private String imagePath;       //图片的远程地址 /View/AppImage/out/123.jpg
    private String imageType;       //图片的类型     .jpg
    private String romoteImageName;     //图片远程服务器的名称 123.jpg
    private String localPath;   //图片本地的地址

    @Override
    public void initView() {
        setContentView(R.layout.activity_device_repair);
    }

    @Override
    public void initData() {
        title.setText(getResources().getString(R.string.device_repair));

        back.setOnClickListener(this);
        repairImage.setOnClickListener(this);
        choosePicture.setOnClickListener(this);
        repairType.setOnClickListener(this);
        readCard.setOnClickListener(this);
        saoMa.setOnClickListener(this);
        enter.setOnClickListener(this);
        cancle.setOnClickListener(this);
    }

    @Override
    public void initOperation() {
        alertDialog = new AlertDialog.Builder(mActivity)
                .setTitle("")
                .setMessage(getResources().getString(R.string.please_nfc))
                .create();
        repairTypeData.clear();
        repairTypeData.add("维修");
        repairTypeData.add("大修");
        repairTypeData.add("返厂");
        repairTypePop = new DeviceCodePopup(mActivity, repairType, onRepairTypeClick);
        repairTypePop.setPopListData(repairTypeData);
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
            case R.id.repair_image:
                if (choosePictures != null && choosePictures.size() != 0) {
                    PictureSelectUtils.previewPicture(mActivity, choosePictures);
                }
                break;
            case R.id.choose_picture:
                PictureSelectUtils.choosePicture(mActivity, Constants.REQUEST_PICTURE);
                break;
            case R.id.repair_type:
                repairTypePop.showCenter(v);
                break;
            case R.id.read_card:
                nfcEnable = true;
                alertDialog.show();
                break;
            case R.id.sao_ma:
                startActivityForResult(new Intent(mActivity, CaptureActivity.class), Constants.SCAN);
                break;
            case R.id.enter:
                deviceRepair();
                break;
        }
    }

    private DeviceCodePopup.OnDeviceCodeClick onRepairTypeClick = new DeviceCodePopup.OnDeviceCodeClick() {
        @Override
        public void OnDeviceCodeClick(String deviceCode) {
            currentRepairType = deviceCode;
        }
    };

    /**
     * 通过ID卡号获取设备信息
     *
     * @param id
     */
    private void getDeviceDataById(String id) {
        if (StringUtils.isEmpty(id)) {
            return;
        }
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GetDeviceByID)
                .params(new String[]{id, id, id})
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
                            deviceId.setText(currentDevice.getBH());
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
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GetDeviceBySAOMA)
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
                            deviceId.setText(currentDevice.getBH());
                        } else {
                            alert(getResources().getString(R.string.no_data));
                        }
                    }
                });
    }


    private void deviceRepair() {
        if (currentDevice == null) {
            alert(getResources().getString(R.string.choose_repair_device));
            return;
        }
        if (choosePictures == null || choosePictures.size() == 0) {
            alert(R.string.please_choose_image);
            return;
        }
        if (StringUtils.isEmpty(currentRepairType)) {
            alert(R.string.choose_repair_type);
            return;
        }
        uploadImage();
    }

    private void uploadImage() {
        localPath = choosePictures.get(0).getCompressPath();
        imageType = localPath.substring(localPath.lastIndexOf("."));
        romoteImageName = userData.getUSERID() + currentDevice.getBH().toString() + System.currentTimeMillis() + imageType;
        FtpManager.getInstance().uploadFile(localPath,
                Config.REPAIR_PATH, romoteImageName, new FtpCallBack() {
                    @Override
                    public void ftpStart() {
                        showProgressDialog(getResources().getString(R.string.uploading_image));
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
        String path = Config.REPAIR_PATH + romoteImageName;
        if (StringUtils.isEmpty(path)) {
            return;
        }
        FtpManager.getInstance().deletFile(path, new FtpCallBack() {
            @Override
            public void ftpStart() {

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
        DBManager.dbDeal(DBManager.START_EVENT)
                .execut(new DbCallBack() {
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
                        insertRepairDevice();
                    }
                });
    }

    /**
     * 插入维修的数据库
     */
    private void insertRepairDevice() {
        String lb;
        switch (currentRepairType) {
            case "维修":
                lb = "3";
                break;
            case "大修":
                lb = "4";
                break;
            case "返厂":
                lb = "5";
                break;
            default:
                lb = "3";
                break;
        }
        DBManager.dbDeal(DBManager.EVENT_INSERT)
                .sql(SqlUrl.REPAIR_DEVICE)
                .params(new Object[]{currentDevice.getBH(), new Date(new java.util.Date().getTime()),
                        userData.getUSERID(), lb, CommonUtils.getDataIfNull(beizhu.getText().toString())})
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {

                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        dismissProgressDialog();
                        deletImage();
                        rollback();
                    }

                    @Override
                    public void onResponse(List result) {
                        getInsertId();
                    }
                });
    }

    private void getInsertId() {
        DBManager.dbDeal(DBManager.EVENT_SELECT)
                .sql(SqlUrl.SELECT_INSERT_ID)
                .clazz(LastInsertIdEntity.class)
                .execut(new DbCallBack() {
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
                        if (result != null && result.size() != 0) {
                            insertImagePath(String.valueOf(((LastInsertIdEntity) result.get(0)).getLast_insert_id()));
                        } else {
                            alert(R.string.insert_erro);
                            dismissProgressDialog();
                            rollback();
                            deletImage();
                        }
                    }
                });
    }

    /**
     * 图片路径插入到数据库中
     * SBBH就是上次插入的id
     */
    private void insertImagePath(String SBBH) {
        String fileSize = FileSizeUtils.getAutoFileOrFilesSize(localPath);
        if (StringUtils.isEmpty(fileSize)) {
            rollback();
            deletImage();
            return;
        }
        String type;
        switch (currentRepairType) {
            case "维修":
                type = Config.doc_sbwx;
                break;
            case "大修":
                type = Config.doc_sbdx;
                break;
            case "返厂":
                type = Config.doc_sbfc;
                break;
            default:
                type = Config.doc_sbwx;
                break;
        }
        DBManager.dbDeal(DBManager.EVENT_INSERT)
                .sql(SqlUrl.INSERT_IAMGE)
                .params(new String[]{SBBH, romoteImageName, fileSize, imagePath, imageType, type})
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {

                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
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
        String lb;
        switch (currentRepairType) {
            case "维修":
                lb = "2";
                break;
            case "大修":
                lb = "5";
                break;
            case "返厂":
                lb = "6";
                break;
            default:
                lb = "2";
                break;
        }
        DBManager.dbDeal(DBManager.EVENT_UPDATA)
                .sql(SqlUrl.CHANGE_DEVICE_STATE)
                .params(new String[]{lb, currentDevice.getBH()})
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {

                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
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
        DBManager.dbDeal(DBManager.ROLLBACK)
                .execut(new DbCallBack() {
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
                        alert(R.string.device_repair_faild);
                        dismissProgressDialog();
                    }
                });
    }

    private void commit() {
        DBManager.dbDeal(DBManager.COMMIT)
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {

                    }

                    @Override
                    public void onError(String err) {
                        alert(R.string.device_repair_faild);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        alert(R.string.device_repair_success);
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
                GlidUtils.displayImage(mActivity, currentDevicePicturePath, repairImage);
            }
        } else if (requestCode == Constants.SCAN && resultCode == RESULT_OK) {  //扫码回到
            String code = data.getExtras().getString("result");
            getDeviceDataBySAOMA(code);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PictureSelectUtils.clearPictureSelectorCache(mActivity);
    }
}
