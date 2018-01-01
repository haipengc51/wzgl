package com.jiekai.wzgl.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.adapter.PartListAdapter;
import com.jiekai.wzgl.config.Config;
import com.jiekai.wzgl.config.SqlUrl;
import com.jiekai.wzgl.entity.DeviceBHEntity;
import com.jiekai.wzgl.entity.DeviceEntity;
import com.jiekai.wzgl.entity.DeviceMCEntity;
import com.jiekai.wzgl.entity.DevicesortEntity;
import com.jiekai.wzgl.entity.PartListEntity;
import com.jiekai.wzgl.test.NFCBaseActivity;
import com.jiekai.wzgl.ui.popup.CodePopup;
import com.jiekai.wzgl.ui.popup.DeviceCodePopup;
import com.jiekai.wzgl.ui.popup.DeviceNamePopup;
import com.jiekai.wzgl.ui.popup.DeviceTypePopup;
import com.jiekai.wzgl.utils.CommonUtils;
import com.jiekai.wzgl.utils.FileSizeUtils;
import com.jiekai.wzgl.utils.GlidUtils;
import com.jiekai.wzgl.utils.PictureSelectUtils;
import com.jiekai.wzgl.utils.StringUtils;
import com.jiekai.wzgl.utils.dbutils.DBManager;
import com.jiekai.wzgl.utils.dbutils.DbCallBack;
import com.jiekai.wzgl.utils.dbutils.DbDeal;
import com.jiekai.wzgl.utils.ftputils.FtpCallBack;
import com.jiekai.wzgl.utils.ftputils.FtpManager;
import com.jiekai.wzgl.utils.treeutils.Node;
import com.jiekai.wzgl.utils.treeutils.TreeListViewAdapter;
import com.jiekai.wzgl.utils.zxing.encoding.EncodingUtils;
import com.jiekai.wzgl.weight.MyListView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laowu on 2017/12/7.
 * 设备绑定界面
 * 不要树结构，要一级一级的结构
 */

public class BindDeviceActivity_new extends NFCBaseActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, DeviceCodePopup.OnDeviceCodeClick {
    private static final int READ_DEVICE_NFC = 0;
    private static final int READ_PART_NFC = 1;
    private static final int CHOOSE_PICHTURE = 0;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.device_leibie)
    TextView deviceLeibie;
    @BindView(R.id.device_xinghao)
    TextView deviceXinghao;
    @BindView(R.id.device_guige)
    TextView deviceGuige;
    @BindView(R.id.device_id)
    TextView deviceId;
    @BindView(R.id.device_card)
    TextView deviceCard;
    @BindView(R.id.read_device_nfc)
    TextView readDeviceNfc;
    @BindView(R.id.create_code)
    TextView createCode;
    @BindView(R.id.device_picture)
    ImageView devicePicture;
    @BindView(R.id.choose_picture)
    TextView choosePicture;
    @BindView(R.id.part_name)
    TextView partName;
    @BindView(R.id.part_id)
    TextView partId;
    @BindView(R.id.part_card)
    TextView partCard;
    @BindView(R.id.read_part_nfc)
    TextView readPartNfc;
    @BindView(R.id.add_part_button)
    TextView addPartButton;
    @BindView(R.id.part_list)
    MyListView partList;
    @BindView(R.id.cancle)
    TextView cancle;
    @BindView(R.id.bind_button)
    TextView bindButton;
    @BindView(R.id.parent)
    LinearLayout parent;


    private int readNfcType;
    private String currentDeviceCode = null;    //选中设备的自编号
    private List<LocalMedia> choosePictures = null;     //选中设备图片的地址

    private DeviceNamePopup deviceLeibiePopup;
    private DeviceNamePopup deviceXinghaoPopup;
    private DeviceNamePopup deviceGuigePopup;
    private DeviceCodePopup deviceCodePopup;
    private List<String> deviceCodeList = new ArrayList<>();
    private AlertDialog alertDialog;

    private List<PartListEntity> partListDatas = new ArrayList<>();
    private PartListAdapter partListAdapter;
    private View partListHeaderView;

    private Bitmap logo;
    private Bitmap code;
    private DevicesortEntity currentLeibie;
    private DevicesortEntity currentXinghao;
    private DevicesortEntity currentGuige;

    @Override
    public void initView() {
        setContentView(R.layout.activity_bind_device_new);
    }

    @Override
    public void initData() {
        title.setText(getResources().getString(R.string.device_bind));
    }

    @Override
    public void initOperation() {
        back.setOnClickListener(this);
        deviceLeibie.setOnClickListener(this);
        deviceXinghao.setOnClickListener(this);
        deviceGuige.setOnClickListener(this);
        deviceId.setOnClickListener(this);
        readDeviceNfc.setOnClickListener(this);
        readPartNfc.setOnClickListener(this);
        choosePicture.setOnClickListener(this);
        devicePicture.setOnClickListener(this);
        addPartButton.setOnClickListener(this);
        cancle.setOnClickListener(this);
        bindButton.setOnClickListener(this);
        createCode.setOnClickListener(this);

        init();
    }

    private void init() {
        deviceLeibiePopup = new DeviceNamePopup(this, deviceLeibie, leibieClick);
        deviceXinghaoPopup = new DeviceNamePopup(this, deviceXinghao, xinghaoClick);
        deviceGuigePopup = new DeviceNamePopup(this, deviceGuige, guigeClick);
        deviceCodePopup = new DeviceCodePopup(this, deviceId, this);
        alertDialog = new AlertDialog.Builder(this)
                .setTitle("")
                .setMessage(getResources().getString(R.string.please_nfc))
                .create();
        if (partListAdapter == null) {
            partListAdapter = new PartListAdapter(BindDeviceActivity_new.this, partListDatas);
            partListHeaderView = LayoutInflater.from(this).inflate(R.layout.header_depart_list, null);
            partListHeaderView.setVisibility(View.GONE);
            partList.addHeaderView(partListHeaderView);
            partList.setAdapter(partListAdapter);
        }
    }

    private void clearView() {
        deviceLeibie.setText("");
        deviceXinghao.setText("");
        deviceGuige.setText("");
        deviceId.setText("");
        partListDatas.clear();
        partListAdapter.setDataList(partListDatas);
        partListHeaderView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.device_leibie:
                getLeiBie();
                break;
            case R.id.device_xinghao:
                getXingHaoByLeibie();
                break;
            case R.id.device_guige:
                getGuiGeByXingHao();
                break;
            case R.id.device_id:
                getDeviceBHByGuiGe();
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
                PictureSelectUtils.choosePicture(BindDeviceActivity_new.this, CHOOSE_PICHTURE);
                break;
            case R.id.device_picture:
                if (choosePictures != null && choosePictures.size() != 0) {
                    PictureSelectUtils.previewPicture(BindDeviceActivity_new.this, choosePictures);
                }
                break;
            case R.id.add_part_button:
                String partBh = partId.getText().toString();
                if (StringUtils.isEmpty(currentDeviceCode)) {
                    alert(getResources().getString(R.string.please_first_get_device));
                }
                if (StringUtils.isEmpty(partBh)) {
                    alert(getResources().getString(R.string.please_first_get_part_bh));
                    return;
                }
                addDepart("1", currentDeviceCode, partBh);
                break;
            case R.id.cancle:
                finish();
                break;
            case R.id.bind_button:
                bindDevice();
                break;
            case R.id.create_code:
                createCode(deviceCard.getText().toString());
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

    private DeviceNamePopup.OnDeviceNameClick leibieClick = new DeviceNamePopup.OnDeviceNameClick() {

        @Override
        public void OnDeviceNameClick(DevicesortEntity devicesortEntity) {
            currentLeibie = devicesortEntity;
            deviceXinghao.setText("");
            deviceGuige.setText("");
            deviceId.setText("");
        }
    };

    private DeviceNamePopup.OnDeviceNameClick xinghaoClick = new DeviceNamePopup.OnDeviceNameClick() {

        @Override
        public void OnDeviceNameClick(DevicesortEntity devicesortEntity) {
            currentXinghao = devicesortEntity;
            deviceGuige.setText("");
            deviceId.setText("");
        }
    };

    private DeviceNamePopup.OnDeviceNameClick guigeClick = new DeviceNamePopup.OnDeviceNameClick() {

        @Override
        public void OnDeviceNameClick(DevicesortEntity devicesortEntity) {
            currentGuige = devicesortEntity;
            deviceId.setText("");
//            getDeviceBHByGuiGe(currentLeibie.getCOOD(), currentXinghao.getCOOD(), currentGuige.getCOOD());
        }
    };

    private void getLeiBie() {
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GetAllLeiBie)
                .clazz(DevicesortEntity.class)
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(getResources().getString(R.string.loading_leixing));
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
                            deviceLeibiePopup.setPopListData(result);
                            deviceLeibiePopup.showCenter(deviceLeibie);
                        } else {
                            alert(getResources().getString(R.string.no_data));
                        }
                    }
                });
    }

    private void getXingHaoByLeibie() {
        if (currentLeibie == null) {
            alert("请先选择类别");
            return;
        }
        String leibie = currentLeibie.getCOOD();
        if (StringUtils.isEmpty(leibie)) {
            alert(getResources().getString(R.string.params_empty));
            return;
        }
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GetXingHaoByLeiBie)
                .params(new String[]{leibie})
                .clazz(DevicesortEntity.class)
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(getResources().getString(R.string.loading_leixing));
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
                            deviceXinghaoPopup.setPopListData(result);
                            deviceXinghaoPopup.showCenter(deviceXinghao);
                        } else {
                            alert(getResources().getString(R.string.no_data));
                        }
                    }
                });
    }

    private void getGuiGeByXingHao() {
        if (currentLeibie == null) {
            alert("请先输入类别");
            return;
        }
        if (currentXinghao == null) {
            alert("请先选择型号");
            return;
        }
        String xinghao = currentXinghao.getCOOD();
        if (StringUtils.isEmpty(xinghao)) {
            alert(getResources().getString(R.string.params_empty));
            return;
        }
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GetGuiGeByXingHao)
                .params(new String[]{xinghao})
                .clazz(DevicesortEntity.class)
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(getResources().getString(R.string.loading_leixing));
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
                            deviceGuigePopup.setPopListData(result);
                            deviceGuigePopup.showCenter(deviceGuige);
                        } else {
                            alert(getResources().getString(R.string.no_data));
                        }
                    }
                });
    }


    /**
     * 通过设备规格加载设备自编号
     **/
    public void getDeviceBHByGuiGe() {
        if (currentLeibie == null) {
            alert("请先选择类别");
            return;
        }
        if (currentXinghao == null) {
            alert("请先选择型号");
            return;
        }
        if (currentGuige == null) {
            alert("请先选择规格");
            return;
        }
        String leibie = currentLeibie.getCOOD();
        String xinghao = currentXinghao.getCOOD();
        String guige = currentGuige.getCOOD();
        deviceId.setText("");
        partListDatas.clear();
        partListAdapter.setDataList(partListDatas);
        partListHeaderView.setVisibility(View.GONE);
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GetBHByLeiBieXinghaoGuige)
                .params(new String[]{leibie, xinghao, guige})
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
                        dismissProgressDialog();
                        if (result == null || result.size() == 0){
                            alert(getResources().getString(R.string.no_data));
                            return;
                        }
                        deviceCodeList.clear();
                        for (int i = 0; i < result.size(); i++) {
                            deviceCodeList.add(((DeviceBHEntity) result.get(i)).getBH());
                            deviceCodePopup.setPopTitle(getResources().getString(R.string.device_id));
                            deviceCodePopup.setPopListData(deviceCodeList);
                            deviceCodePopup.showCenter(deviceId);
                        }
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
        if (!StringUtils.isEmpty(currentDeviceCode)) {
            findPartsByDeviceID(currentDeviceCode);
        }
    }

    /**
     * 根据配件的id卡号，发现配件的名称和自编号
     *
     * @param idCard
     */
    private void findDeviceByID(String idCard) {
        if (StringUtils.isEmpty(idCard)) {
            return;
        }
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GetDeviceByID)
                .params(new String[]{idCard, idCard, idCard})
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
                        if (result != null && result.size() != 0) {
                            DeviceEntity item = (DeviceEntity) result.get(0);
                            partName.setText(item.getMC());
                            partId.setText(item.getBH());
                        }  else {
                            alert(getResources().getString(R.string.no_data));
                        }
                        dismissProgressDialog();
                    }
                });
    }

    /**
     * 把配件添加到设备上
     *
     * @param sspj   是否添加配件（1是配件，0不是配件）
     * @param sssbbh 所属设备编号，如果是删除设配件的话，需要传空
     * @param partID 配件的自编号
     */
    private void addDepart(String sspj, String sssbbh, String partID) {
        DBManager.dbDeal(DBManager.UPDATA)
                .sql(SqlUrl.AddDepart)
                .params(new String[]{sspj, sssbbh, partID})
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(getResources().getString(R.string.adding_depart));
                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        dismissProgressDialog();
                        alert(getResources().getString(R.string.add_depart_success));
                        findPartsByDeviceID(currentDeviceCode);
                    }
                });
    }

    /**
     * 根据设备的id获取配件列表
     *
     * @param deviceId
     */
    private void findPartsByDeviceID(String deviceId) {
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GetPartListByDeviceId)
                .params(new String[]{deviceId})
                .clazz(PartListEntity.class)
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {

                    }

                    @Override
                    public void onError(String err) {

                    }

                    @Override
                    public void onResponse(List result) {
                        partListAdapter.setDataList(result);
                        if (result.size() != 0) {
                            partListHeaderView.setVisibility(View.VISIBLE);
                        } else {
                            partListHeaderView.setVisibility(View.GONE);
                        }
                    }
                });
    }

    /**
     * 提交绑定设备
     */
    private void bindDevice() {
        String deviceCardID = deviceCard.getText().toString();
        String deviceBH = deviceId.getText().toString();
        if (StringUtils.isEmpty(deviceBH)) {
            alert(getResources().getString(R.string.please_first_get_device));
            return;
        }
        if (StringUtils.isEmpty(deviceCardID)) {
            alert(getResources().getString(R.string.please_first_get_device_card));
            return;
        }
        if (choosePictures == null || choosePictures.size() == 0) {
            alert(getResources().getString(R.string.please_choose_image));
            return;
        }
        String fileType = null;
        try {
            fileType = choosePictures.get(0).getCompressPath().substring(choosePictures.get(0).getCompressPath().lastIndexOf("."));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(fileType)) {
            alert(getResources().getString(R.string.please_choose_true_iamge));
            return;
        }
        DBManager.dbDeal(DBManager.UPDATA)
                .sql(SqlUrl.BIND_DEVICE)
                .params(new String[]{deviceCardID, deviceBH})
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(getResources().getString(R.string.bindding_device));
                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        dismissProgressDialog();
                        upLoadImage();
                    }
                });
    }

    /**
     * 上传图片
     */
    private void upLoadImage() {
        final String localPath = choosePictures.get(0).getCompressPath();
        final String romoteName = userData.getUSERID() + deviceId.getText().toString() + System.currentTimeMillis();
        final String fileType = localPath.substring(localPath.lastIndexOf("."));
        FtpManager.getInstance().uploadFile(localPath,
                Config.BINDIMAGE_PATH, romoteName + fileType, new FtpCallBack() {
                    @Override
                    public void ftpStart() {
                        showProgressDialog(getResources().getString(R.string.uploading_image));
                    }

                    @Override
                    public void ftpSuccess(String remotePath) {
                        saveImagePathToRemoteDB(romoteName,
                                FileSizeUtils.getAutoFileOrFilesSize(localPath),
                                Config.BINDIMAGE_PATH + romoteName + fileType,
                                fileType);
                    }

                    @Override
                    public void ftpFaild(String error) {
                        alert(error);
                        dismissProgressDialog();
                    }
                });
    }

    /**
     * 把上传的图片地址存到自己的服务器上面
     */
    private void saveImagePathToRemoteDB(String wjmc, String wjdx, String wjdz, String wjlx) {
        DBManager.dbDeal(DBManager.INSERT)
                .sql(SqlUrl.SaveDoc)
                .params(new String[]{deviceId.getText().toString(), wjmc, wjdx, wjdz, wjlx, Config.SBBD})
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
                        dismissProgressDialog();
                        alert(getResources().getString(R.string.device_bind_success));
                        finish();
                    }
                });
    }

    /**
     * 生成二维码界面
     */
    private void createCode(String code) {
//        if (StringUtils.isEmpty(code)) {
//            alert("没有发现要生成二维码的ID号");
//            return;
//        }
        int bitmapWidth = CommonUtils.dip2Px(BindDeviceActivity_new.this, 150);
        logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        this.code = EncodingUtils.createQRCode("00000000001", bitmapWidth, bitmapWidth, logo);
        CodePopup codePopup = new CodePopup(BindDeviceActivity_new.this);
        codePopup.showCenter(createCode);
        codePopup.setCodeMap(this.code);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PictureSelectUtils.clearPictureSelectorCache(BindDeviceActivity_new.this);
        if (this.code != null) {
            this.code.recycle();
            this.code = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_PICHTURE && resultCode == RESULT_OK) {
            choosePictures = PictureSelector.obtainMultipleResult(data);
            if (choosePictures != null && choosePictures.size() != 0) {
                String currentDevicePicturePath = choosePictures.get(0).getCompressPath();
                GlidUtils.displayImage(BindDeviceActivity_new.this, currentDevicePicturePath, devicePicture);
            }
        }
    }
}
