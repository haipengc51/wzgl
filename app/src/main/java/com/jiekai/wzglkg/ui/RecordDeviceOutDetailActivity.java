package com.jiekai.wzglkg.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiekai.wzglkg.R;
import com.jiekai.wzglkg.config.Config;
import com.jiekai.wzglkg.config.IntentFlag;
import com.jiekai.wzglkg.config.SqlUrl;
import com.jiekai.wzglkg.entity.DevicedocEntity;
import com.jiekai.wzglkg.entity.DevicestoreEntity;
import com.jiekai.wzglkg.entity.UserNameEntity;
import com.jiekai.wzglkg.ui.base.MyBaseActivity;
import com.jiekai.wzglkg.utils.CommonUtils;
import com.jiekai.wzglkg.utils.GlidUtils;
import com.jiekai.wzglkg.utils.PictureSelectUtils;
import com.jiekai.wzglkg.utils.StringUtils;
import com.jiekai.wzglkg.utils.TimeUtils;
import com.jiekai.wzglkg.utils.dbutils.DBManager;
import com.jiekai.wzglkg.utils.dbutils.DbCallBack;
import com.jiekai.wzglkg.utils.dbutils.DbDeal;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LaoWu on 2018/1/15.
 */

public class RecordDeviceOutDetailActivity extends MyBaseActivity implements View.OnClickListener {
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
    @BindView(R.id.operator_people)
    TextView operatorPeople;
    @BindView(R.id.operator_time)
    TextView operatorTime;
    @BindView(R.id.jinghao)
    TextView jinghao;
    @BindView(R.id.lydw)
    TextView lydw;
    @BindView(R.id.check_people)
    TextView checkPeople;
    @BindView(R.id.check_time)
    TextView checkTime;
    @BindView(R.id.check_result)
    TextView checkResult;
    @BindView(R.id.check_remark)
    TextView checkRemark;
    @BindView(R.id.operator_remark)
    TextView operatorRemark;
    @BindView(R.id.lyr)
    TextView lyr;
    @BindView(R.id.lyrdh)
    TextView lyrdh;

    private DevicestoreEntity currentData;
    private List<LocalMedia> choosePictures = new ArrayList<>();

    private DbDeal imageDbDeal = null;
    private DbDeal nameDbDeal = null;

    @Override
    public void initView() {
        setContentView(R.layout.activity_record_device_out_detail);
    }

    @Override
    public void initData() {
        back.setVisibility(View.VISIBLE);
        title.setText(getResources().getString(R.string.record_out));

        back.setOnClickListener(this);
        recordImage.setOnClickListener(this);

        currentData = (DevicestoreEntity) getIntent().getSerializableExtra(IntentFlag.DATA);
    }

    @Override
    public void initOperation() {
        if (currentData != null) {
            deviceId.setText(CommonUtils.getDataIfNull(currentData.getSBBH()));
            operatorPeople.setText(CommonUtils.getDataIfNull(currentData.getCzrname()));
            operatorTime.setText(TimeUtils.dateToStringYYYYmmdd(currentData.getCZSJ()));
            jinghao.setText(CommonUtils.getDataIfNull(currentData.getJH()));
            lydw.setText(CommonUtils.getDataIfNull(currentData.getLYDW()));
            lyr.setText(CommonUtils.getDataIfNull(currentData.getLYR()));
            lyrdh.setText(CommonUtils.getDataIfNull(currentData.getLYRDH()));
            operatorRemark.setText(CommonUtils.getDataIfNull(currentData.getBZ()));
            if (currentData.getSHSJ() != null) {
                checkTime.setText(TimeUtils.dateToStringYYYYmmddHHMMSS(currentData.getSHSJ()));
            }
            checkRemark.setText(CommonUtils.getDataIfNull(currentData.getSHBZ()));
            if ("1".equals(currentData.getSHYJ())) {
                checkResult.setText("通过");
            } else if ("0".equals(currentData.getSHYJ())) {
                checkResult.setText("未通过");
            } else {
                checkResult.setText("待审核");
            }

            showCommitImage(currentData.getID());
            getSHRName();
        } else {
            alert(R.string.no_data);
            finish();
        }
    }

    @Override
    public void progressDialogCancleLisen() {
        if (imageDbDeal != null) {
            imageDbDeal.cancleDbDeal();
            dismissProgressDialog();
        }
        if (nameDbDeal != null) {
            nameDbDeal.cancleDbDeal();
            dismissProgressDialog();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.record_image:
                if (choosePictures != null && choosePictures.size() != 0) {
                    PictureSelectUtils.previewPicture(mActivity, choosePictures);
                }
                break;
        }
    }

    private void showCommitImage(int id) {
        if (id == -1) {
            alert(R.string.get_image_fail);
            return;
        }
        imageDbDeal = DBManager.dbDeal(DBManager.SELECT);
                imageDbDeal.sql(SqlUrl.Get_Image_Path)
                .params(new Object[]{id, Config.doc_sbck})
                .clazz(DevicedocEntity.class)
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

    private void getSHRName() {
        if (StringUtils.isEmpty(currentData.getSHR())) {
            return;
        }
        nameDbDeal = DBManager.dbDeal(DBManager.SELECT);
                nameDbDeal.sql(SqlUrl.GET_NAME_BY_ID)
                .params(new String[]{currentData.getSHR()})
                .clazz(UserNameEntity.class)
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
                            checkPeople.setText(((UserNameEntity) result.get(0)).getName());
                        }
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PictureSelectUtils.clearPictureSelectorCache(mActivity);
    }
}
