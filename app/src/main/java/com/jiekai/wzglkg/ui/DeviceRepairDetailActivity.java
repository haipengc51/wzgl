package com.jiekai.wzglkg.ui;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiekai.wzglkg.R;
import com.jiekai.wzglkg.config.Config;
import com.jiekai.wzglkg.config.Constants;
import com.jiekai.wzglkg.config.IntentFlag;
import com.jiekai.wzglkg.config.SqlUrl;
import com.jiekai.wzglkg.entity.DevicedocEntity;
import com.jiekai.wzglkg.entity.DevicestoreEntity;
import com.jiekai.wzglkg.ui.base.MyBaseActivity;
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
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by LaoWu on 2018/1/5.
 * 未什么通过的申请的详情页
 */

public class DeviceRepairDetailActivity extends MyBaseActivity implements View.OnClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.record_type)
    TextView recordType;
    @BindView(R.id.device_id)
    TextView deviceId;
    @BindView(R.id.read_card)
    TextView readCard;
    @BindView(R.id.record_image)
    ImageView recordImage;
    @BindView(R.id.choose_picture)
    TextView choosePicture;
    @BindView(R.id.jinghao)
    EditText jinghao;
    @BindView(R.id.lydw)
    EditText lydw;
    @BindView(R.id.beizhu)
    EditText beizhu;
    @BindView(R.id.check_remark)
    TextView checkRemark;
    @BindView(R.id.recommit)
    TextView recommit;

    private DevicestoreEntity currentDatas;

    private List<LocalMedia> choosePictures = new ArrayList<>();
    private String imagePath;       //图片的远程地址 /out/123.jpg
    private String imageType;       //图片的类型     .jpg
    private String romoteImageName;     //图片远程服务器的名称 123.jpg
    private String localPath;   //图片本地的地址
    private boolean isChooseImage = false;  //是否重新上传了图片

    private DbDeal dbDeal = null;
    private DbDeal eventDbDeal = null;

    @Override
    public void initView() {
        setContentView(R.layout.activity_in_history_detail);
    }

    @Override
    public void initData() {
        title.setText(getResources().getString(R.string.record_failed_detail));
        back.setOnClickListener(this);
        choosePicture.setOnClickListener(this);
        recordImage.setOnClickListener(this);
        recommit.setOnClickListener(this);

        currentDatas = (DevicestoreEntity) getIntent().getSerializableExtra(IntentFlag.DATA);
    }

    @Override
    public void initOperation() {
        if (currentDatas != null) {
            if (Config.LB_WEIXIU.equals(currentDatas.getLB())) {
                recordType.setText(getResources().getString(R.string.device_weixiu));
            } else if (Config.LB_DAXIU.equals(currentDatas.getLB())) {
                recordType.setText(getResources().getString(R.string.device_daxiu));
            } else if (Config.LB_FANCHANG.equals(currentDatas.getLB())) {
                recordType.setText(getResources().getString(R.string.device_fanchang));
            }
            deviceId.setText(CommonUtils.getDataIfNull(currentDatas.getSBBH()));
//            jinghao.setText(CommonUtils.getDataIfNull(currentDatas.getJH()));
//            lydw.setText(CommonUtils.getDataIfNull(currentDatas.getLYDW()));
            beizhu.setText(CommonUtils.getDataIfNull(currentDatas.getBZ()));
            checkRemark.setText(CommonUtils.getDataIfNull(currentDatas.getSHBZ()));
            showCommitImage(currentDatas.getID());
        } else {
            alert(R.string.get_bh_faild);
            finish();
        }
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.choose_picture:
                PictureSelectUtils.choosePicture(PictureSelector.create(this), Constants.REQUEST_PICTURE);
                break;
            case R.id.record_image:
                if (choosePictures != null && choosePictures.size() != 0)
                    PictureSelectUtils.previewPicture(mActivity, choosePictures);
                break;
            case R.id.recommit:
                upload();
                break;
        }
    }

    /**
     * 开始提交数据
     */
    private void upload() {
        if (choosePictures != null && choosePictures.size() == 0) {
            alert(R.string.please_choose_image);
            return;
        }
        if (StringUtils.isEmpty(currentDatas.getSBBH())) {
            alert(R.string.get_bh_faild);
            return;
        }
//        if (StringUtils.isEmpty(jinghao.getText().toString())) {
//            alert(R.string.please_input_jinghao);
//            return;
//        }
//        if (StringUtils.isEmpty(lydw.getText().toString())) {
//            alert(R.string.input_lingyongdanwei);
//            return;
//        }
        if (isChooseImage) {
            updataImage();
        } else {
            startEvent();
        }
    }

    private void updataImage() {
        localPath = choosePictures.get(0).getCompressPath();
        imageType = localPath.substring(localPath.lastIndexOf("."));
        romoteImageName = userData.getUSERID() + currentDatas.getSBBH() + System.currentTimeMillis() + imageType;
        FtpManager.getInstance().uploadFile(localPath,
                Config.REPAIR_PATH, romoteImageName, new FtpCallBack() {
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
        if (!isChooseImage) {
            return;
        }
        String path = Config.REPAIR_PATH + romoteImageName;
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
                        insertRecord();
                    }
                });
    }

    /**
     * 插入记录的数据库
     */
    private void insertRecord() {
        if (eventDbDeal == null) {
            dismissProgressDialog();
            deletImage();
            rollback();
            return;
        }
        eventDbDeal.reset(DBManager.EVENT_UPDATA);
        eventDbDeal.sql(SqlUrl.UPDATE_DEVICE_STOR);
        if (Config.LB_WEIXIU.equals(currentDatas.getLB())) {
            eventDbDeal.params(new Object[]{new Date(new java.util.Date().getTime()), userData.getUSERID(), Config.LB_WEIXIU,
                    "", CommonUtils.getDataIfNull(beizhu.getText().toString()),
                    "", currentDatas.getID()});
        } else if (Config.LB_DAXIU.equals(currentDatas.getLB())) {
            eventDbDeal.params(new Object[]{new Date(new java.util.Date().getTime()), userData.getUSERID(), Config.LB_DAXIU,
                    "", CommonUtils.getDataIfNull(beizhu.getText().toString()),
                    "", currentDatas.getID()});
        } else if (Config.LB_FANCHANG.equals(currentDatas.getLB())) {
            eventDbDeal.params(new Object[]{new Date(new java.util.Date().getTime()), userData.getUSERID(), Config.LB_FANCHANG,
                    "", CommonUtils.getDataIfNull(beizhu.getText().toString()),
                    "", currentDatas.getID()});
        }

        eventDbDeal.execut(mContext, new DbCallBack() {
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
                        if (isChooseImage) {
                            insertImagePath(String.valueOf(currentDatas.getID()));
                        } else {
                            commit();
                        }
                    }
                });
    }

    /**
     * 图片路径插入到数据库中
     * SBBH就是上次插入的id
     */
    private void insertImagePath(String SBBH) {
        if (!isChooseImage) {
            return;
        }
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
        eventDbDeal.reset(DBManager.EVENT_UPDATA);
        eventDbDeal.sql(SqlUrl.UPDATE_IMAGE);
        if (Config.LB_WEIXIU.equals(currentDatas.getLB())) {
            eventDbDeal.params(new String[]{romoteImageName, fileSize, imagePath, imageType, SBBH, Config.doc_sbwx});
        } else if (Config.LB_DAXIU.equals(currentDatas.getLB())) {
            eventDbDeal.params(new String[]{romoteImageName, fileSize, imagePath, imageType, SBBH, Config.doc_sbdx});
        } else if (Config.LB_FANCHANG.equals(currentDatas.getLB())) {
            eventDbDeal.params(new String[]{romoteImageName, fileSize, imagePath, imageType, SBBH, Config.doc_sbfc});
        }
        eventDbDeal.execut(mContext, new DbCallBack() {
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
                    }

                    @Override
                    public void onResponse(List result) {
                        alert(R.string.commit_repair_faild);
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
                        alert(R.string.commit_repair_faild);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        alert(R.string.commit_repair_success);
                        dismissProgressDialog();
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }

    private void showCommitImage(int id) {
        if (id == -1) {
            alert(R.string.get_image_fail);
            return;
        }
        dbDeal = DBManager.dbDeal(DBManager.SELECT);
        dbDeal.sql(SqlUrl.Get_Image_Path);
        if (Config.LB_WEIXIU.equals(currentDatas.getLB())) {
            dbDeal.params(new Object[]{id, Config.doc_sbwx});
        } else if (Config.LB_DAXIU.equals(currentDatas.getLB())) {
            dbDeal.params(new Object[]{id, Config.doc_sbdx});
        } else if (Config.LB_FANCHANG.equals(currentDatas.getLB())) {
            dbDeal.params(new Object[]{id, Config.doc_sbfc});
        }
        dbDeal.clazz(DevicedocEntity.class)
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
                            DevicedocEntity entity = (DevicedocEntity) result.get(0);
                            LocalMedia localMedia = new LocalMedia();
                            localMedia.setPath(Config.WEB_HOLDE + entity.getWJDZ());
                            choosePictures.clear();
                            choosePictures.add(localMedia);
                            GlidUtils.displayImage(mActivity, Config.WEB_HOLDE + entity.getWJDZ(), recordImage);
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_PICTURE && resultCode == RESULT_OK) {
            choosePictures.clear();
            choosePictures = PictureSelector.obtainMultipleResult(data);
            if (choosePictures != null && choosePictures.size() != 0) {
                isChooseImage = true;
                String currentDevicePicturePath = choosePictures.get(0).getCompressPath();
                GlidUtils.displayImage(mActivity, currentDevicePicturePath, recordImage);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PictureSelectUtils.clearPictureSelectorCache(mActivity);
    }
}
