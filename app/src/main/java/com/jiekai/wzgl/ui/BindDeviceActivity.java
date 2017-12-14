package com.jiekai.wzgl.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.config.SqlUrl;
import com.jiekai.wzgl.entity.DeviceBHEntity;
import com.jiekai.wzgl.entity.DeviceEntity;
import com.jiekai.wzgl.entity.DeviceMCEntity;
import com.jiekai.wzgl.test.NFCBaseActivity;
import com.jiekai.wzgl.ui.popup.DeviceCodePopup;
import com.jiekai.wzgl.ui.popup.DeviceNamePopup;
import com.jiekai.wzgl.ui.popup.DeviceTypePopup;
import com.jiekai.wzgl.utils.GlidUtils;
import com.jiekai.wzgl.utils.PictureSelectUtils;
import com.jiekai.wzgl.utils.StringUtils;
import com.jiekai.wzgl.utils.dbutils.DBManager;
import com.jiekai.wzgl.utils.dbutils.DbCallBack;
import com.jiekai.wzgl.utils.dbutils.DbDeal;
import com.jiekai.wzgl.utils.treeutils.Node;
import com.jiekai.wzgl.utils.treeutils.TreeListViewAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laowu on 2017/12/7.
 * 设备绑定界面
 */

public class BindDeviceActivity extends NFCBaseActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, TreeListViewAdapter.OnTreeNodeClickListener,
        DeviceNamePopup.OnDeviceNameClick, DeviceCodePopup.OnDeviceCodeClick {
    private static final int READ_DEVICE_NFC = 0;
    private static final int READ_PART_NFC = 1;
    private static final int CHOOSE_PICHTURE = 0;

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.device_type)
    TextView deviceType;
    @BindView(R.id.device_name)
    TextView deviceName;
    @BindView(R.id.device_id)
    TextView deviceId;
    @BindView(R.id.device_card)
    TextView deviceCard;
    @BindView(R.id.read_device_nfc)
    TextView readDeviceNfc;
    @BindView(R.id.part_card)
    TextView partCard;
    @BindView(R.id.read_part_nfc)
    TextView readPartNfc;
    @BindView(R.id.parent)
    LinearLayout parent;
    @BindView(R.id.part_name)
    TextView partName;
    @BindView(R.id.part_id)
    TextView partId;
    @BindView(R.id.choose_picture)
    TextView choosePicture;
    @BindView(R.id.device_picture)
    ImageView devicePicture;

    private int readNfcType;
    private String currentDeviceCode = null;    //选中设备的自编号
    private List<LocalMedia> choosePictures = null; //选中设备图片的地址

    private DeviceTypePopup deviceTypePopup;
    private DeviceNamePopup deviceNamePopup;
    private List<String> deviceNameList = new ArrayList<>();
    private DeviceCodePopup deviceCodePopup;
    private List<String> deviceCodeList = new ArrayList<>();
    private AlertDialog alertDialog;

    @Override
    public void initView() {
        setContentView(R.layout.activity_bind_device);
    }

    @Override
    public void initData() {
        title.setText(getResources().getString(R.string.device_bind));
    }

    @Override
    public void initOperation() {
        back.setOnClickListener(this);
        deviceType.setOnClickListener(this);
        deviceName.setOnClickListener(this);
        deviceId.setOnClickListener(this);
        readDeviceNfc.setOnClickListener(this);
        readPartNfc.setOnClickListener(this);
        choosePicture.setOnClickListener(this);
        devicePicture.setOnClickListener(this);

        initPopupWindow();
    }

    private void initPopupWindow() {
        deviceTypePopup = new DeviceTypePopup(this, deviceType, this);
        deviceNamePopup = new DeviceNamePopup(this, deviceName, this);
        deviceCodePopup = new DeviceCodePopup(this, deviceId, this);
        alertDialog = new AlertDialog.Builder(this)
                .setTitle("")
                .setMessage(getResources().getString(R.string.please_nfc))
                .create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.device_name:
                deviceNamePopup.setPopTitle(getResources().getString(R.string.device_name));
                deviceNamePopup.setPopListData(deviceNameList);
                deviceNamePopup.showCenter(v);
                break;
            case R.id.device_id:
                deviceCodePopup.setPopTitle(getResources().getString(R.string.device_id));
                deviceCodePopup.setPopListData(deviceCodeList);
                deviceCodePopup.showCenter(v);
                break;
            case R.id.read_device_nfc:
                //TODO 检查一下NFC是否启动
                readNfcType = READ_DEVICE_NFC;
                nfcEnable = true;
                alertDialog.show();
                break;
            case R.id.read_part_nfc:
                nfcEnable = true;
                readNfcType = READ_PART_NFC;
                alertDialog.show();
                break;
            case R.id.choose_picture:
                PictureSelectUtils.choosePicture(BindDeviceActivity.this, CHOOSE_PICHTURE);
                break;
            case R.id.device_picture:
                if (choosePictures != null && choosePictures.size() != 0) {
                    PictureSelectUtils.previewPicture(BindDeviceActivity.this, choosePictures);
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void getNfcData(String nfcString) {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        if (readNfcType == READ_DEVICE_NFC) {
            deviceCard.setText(nfcString);
            nfcEnable = false;
            //TODO 如果上面的类型什么的没有选择，直接刷卡，此卡已经绑定设备的话，需要查询设备信息显示的上面
            if (currentDeviceCode == null) {

            }
        } else if (readNfcType == READ_PART_NFC) {
            partCard.setText(nfcString);
            nfcEnable = false;
            findDeviceByID(nfcString);
        }
    }

    /**
     * 树的点击事件
     *
     * @param view
     * @param node
     * @param position
     */
    @Override
    public void onClick(View view, Node node, int position) {
        if (StringUtils.isEmpty(node.getTEXT())) {
            return;
        }
        deviceType.setText(node.getTEXT());
        deviceTypePopup.dismiss();
        DbDeal dbDeal = DBManager.dbDeal(DBManager.SELECT)
                .clazz(DeviceMCEntity.class)
                .params(new String[]{node.getId()});
        switch (node.getLevel()) {
            case 0:
                dbDeal.sql(SqlUrl.GetDeviceMCByLB);
                break;
            case 1:
                dbDeal.sql(SqlUrl.GetDeviceMCByXh);
                break;
            case 2:
                dbDeal.sql(SqlUrl.GetDeviceMCByGG);
                break;
        }
        dbDeal.execut(new DbCallBack() {
            @Override
            public void onDbStart() {
                showProgressDialog(getResources().getString(R.string.loding_device_mc));
            }

            @Override
            public void onError(String err) {
                alert(err);
                dismissProgressDialog();
            }

            @Override
            public void onResponse(List result) {
                deviceNameList.clear();
                for (int i = 0; i < result.size(); i++) {
                    deviceNameList.add(((DeviceMCEntity) result.get(i)).getMC());
                }
                dismissProgressDialog();
            }
        });
    }

    /**
     * 设备名称点击回调
     *
     * @param deviceName
     */
    @Override
    public void OnDeviceNameClick(String deviceName) {
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GetDeviceBHByMC)
                .params(new String[]{deviceName})
                .clazz(DeviceBHEntity.class)
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(getResources().getString(R.string.loding_device_bh));
                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        deviceCodeList.clear();
                        for (int i = 0; i < result.size(); i++) {
                            deviceCodeList.add(((DeviceBHEntity) result.get(i)).getBH());
                        }
                        dismissProgressDialog();
                    }
                });
    }

    /**
     * 设备自编码的点击回调
     *
     * @param deviceCode
     */
    @Override
    public void OnDeviceCodeClick(String deviceCode) {
        currentDeviceCode = deviceCode;
    }

    /**
     * 根据id卡号，发现配件的名称和自编号
     *
     * @param idCard
     */
    private void findDeviceByID(String idCard) {
        if (StringUtils.isEmpty(idCard)) {
            return;
        }
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GetDeviceByID)
                .params(new String[]{idCard})
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
                        DeviceEntity item = (DeviceEntity) result.get(0);
                        partName.setText(item.getMC());
                        partId.setText(item.getBH());
                        dismissProgressDialog();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_PICHTURE && resultCode == RESULT_OK) {
            choosePictures = PictureSelector.obtainMultipleResult(data);
            if (choosePictures != null && choosePictures.size() != 0) {
                String currentDevicePicturePath = choosePictures.get(0).getCompressPath();
                GlidUtils.displayImage(BindDeviceActivity.this, currentDevicePicturePath, devicePicture);
            }
        }
    }
}
