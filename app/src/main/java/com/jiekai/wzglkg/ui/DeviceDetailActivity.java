package com.jiekai.wzglkg.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jiekai.wzglkg.R;
import com.jiekai.wzglkg.adapter.DeviceDetailAdapter;
import com.jiekai.wzglkg.adapter.DeviceDetailAdapterEntity;
import com.jiekai.wzglkg.config.Config;
import com.jiekai.wzglkg.config.Constants;
import com.jiekai.wzglkg.config.SqlUrl;
import com.jiekai.wzglkg.entity.DeviceDetailEntity;
import com.jiekai.wzglkg.entity.DevicedocEntity;
import com.jiekai.wzglkg.test.NFCBaseActivity;
import com.jiekai.wzglkg.utils.PictureSelectUtils;
import com.jiekai.wzglkg.utils.StringUtils;
import com.jiekai.wzglkg.utils.TimeUtils;
import com.jiekai.wzglkg.utils.dbutils.DBManager;
import com.jiekai.wzglkg.utils.dbutils.DbCallBack;
import com.jiekai.wzglkg.utils.zxing.CaptureActivity;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by laowu on 2017/12/22.
 * 设备详细信息
 */

public class DeviceDetailActivity extends NFCBaseActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.read_card)
    TextView readCard;
    @BindView(R.id.sao_ma)
    TextView saoMa;
    @BindView(R.id.button_layout)
    LinearLayout buttonLayout;

    private DeviceDetailAdapter detailAdapter;
    private List<DeviceDetailAdapterEntity> dataList = new ArrayList<>();
    private AlertDialog alertDialog;

    private DeviceDetailEntity currentDevice;

    @Override
    public void initView() {
        setContentView(R.layout.activity_device_detail);
    }

    @Override
    public void initData() {
        title.setText(getResources().getString(R.string.device_detail));
        back.setOnClickListener(this);
        readCard.setOnClickListener(this);
        saoMa.setOnClickListener(this);

        alertDialog = new AlertDialog.Builder(mActivity)
                .setTitle("")
                .setMessage(getResources().getString(R.string.please_nfc))
                .create();
    }

    @Override
    public void initOperation() {
        if (detailAdapter == null) {
            detailAdapter = new DeviceDetailAdapter(mActivity, dataList);
            listview.setAdapter(detailAdapter);
            listview.setOnItemClickListener(this);
        }
    }

    @Override
    public void getNfcData(String nfcString) {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        nfcEnable = false;
        getDeviceById(nfcString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.read_card:
                nfcEnable = true;
                alertDialog.show();
                break;
            case R.id.sao_ma:
                startActivityForResult(new Intent(mActivity, CaptureActivity.class), Constants.SCAN);
                break;
        }
    }

    /**
     * 通过ID编码获取设备信息
     *
     * @param nfcString
     */
    private void getDeviceById(String nfcString) {
        if (StringUtils.isEmpty(nfcString)) {
            alert(getResources().getString(R.string.get_id_err));
            return;
        }
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GET_DEVICE_DETAIL)
                .params(new String[]{nfcString, nfcString, nfcString})
                .clazz(DeviceDetailEntity.class)
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
                            paresDeviceToShow((DeviceDetailEntity) result.get(0));
                            buttonLayout.setVisibility(View.GONE);
                            listview.setVisibility(View.VISIBLE);
                        } else {
                            alert(getResources().getString(R.string.no_data));
                        }
                    }
                });
    }

    /**
     * 把deviceEntity的数据转换成listView结构去显示
     *
     * @param deviceEntity
     */
    private void paresDeviceToShow(DeviceDetailEntity deviceEntity) {
        currentDevice = deviceEntity;
        dataList.clear();
        dataList.add(new DeviceDetailAdapterEntity("设备自编号", deviceEntity.getBH()));
//        private String MC;      //设备名称
        dataList.add(new DeviceDetailAdapterEntity("设备名称", deviceEntity.getMC()));
//        private String LB;      //设备类别
        dataList.add(new DeviceDetailAdapterEntity("设备类别", deviceEntity.getLeibie()));
//        private String XH;      //设备型号
        dataList.add(new DeviceDetailAdapterEntity("设备型号", deviceEntity.getXinghao()));
//        private String GG;      //设备规格
        dataList.add(new DeviceDetailAdapterEntity("设备规格", deviceEntity.getGuige()));
//        private String JLQDMC;      //物资记录清单名称
        dataList.add(new DeviceDetailAdapterEntity("物资记录清单名称", deviceEntity.getJLQDMC()));
//        private String LZJLBH;      //物资流转记录编号
        dataList.add(new DeviceDetailAdapterEntity("物资流转记录编号", deviceEntity.getLZJLBH()));
//        private String TMBH;      //条码编号
//        dataList.add(new DeviceDetailAdapterEntity("条码编号", deviceEntity.getTMBH()));
//        private String EWMBH;      //二维码编号
//        dataList.add(new DeviceDetailAdapterEntity("二维码编号", deviceEntity.getEWMBH()));
//        private String KWZT;      //物资库位状态
        dataList.add(new DeviceDetailAdapterEntity("物资库位状态", deviceEntity.getKWZT()));
//        private String CCBH;      //出厂编号
        dataList.add(new DeviceDetailAdapterEntity("出厂编号", deviceEntity.getCCBH()));
//        private String GYS;      //供应商
        dataList.add(new DeviceDetailAdapterEntity("供应商", deviceEntity.getGYS()));
//        private String GYSGB;      //供应商国别
        dataList.add(new DeviceDetailAdapterEntity("供应商国别", deviceEntity.getGYSGB()));
//        private String IDDZMBH1;      //ID电子码编号1
//        dataList.add(new DeviceDetailAdapterEntity("ID电子码编号1", deviceEntity.getIDDZMBH1()));
//        private String IDDZMBH2;      //ID电子码编号2
//        dataList.add(new DeviceDetailAdapterEntity("ID电子码编号2", deviceEntity.getIDDZMBH2()));
//        private String IDDZMBH3;      //ID电子码编号3
//        dataList.add(new DeviceDetailAdapterEntity("ID电子码编号3", deviceEntity.getIDDZMBH3()));
//        private String ZCLY;      //物资资产来源
        dataList.add(new DeviceDetailAdapterEntity("物资资产来源", deviceEntity.getZCLY()));
//        private String SYDW;      //物资所有单位
        dataList.add(new DeviceDetailAdapterEntity("物资所有单位", deviceEntity.getSYDW()));
//        private String SHIYDW;      //物资使用单位
        dataList.add(new DeviceDetailAdapterEntity("物资使用单位", deviceEntity.getSHIYDW()));
//        private String GYSDH;      //供应商电话
        dataList.add(new DeviceDetailAdapterEntity("供应商电话", deviceEntity.getGYSDH()));
//        private String GYSDZ;      //供应商地址
        dataList.add(new DeviceDetailAdapterEntity("供应商地址", deviceEntity.getGYSDZ()));
//        private String GYSJSFWDH;      //供应商技术服务电话
        dataList.add(new DeviceDetailAdapterEntity("供应商技术服务电话", deviceEntity.getGYSJSFWDH()));
//        private Date SCRQ;      //生产日期
        dataList.add(new DeviceDetailAdapterEntity("生产日期", TimeUtils.dateToStringYYYYmmdd(deviceEntity.getSCRQ())));
//        private Date DHRQ;      //到货日期
        dataList.add(new DeviceDetailAdapterEntity("到货日期", TimeUtils.dateToStringYYYYmmdd(deviceEntity.getDHRQ())));
//        private Date JHRQ;      //进货日期
        dataList.add(new DeviceDetailAdapterEntity("进货日期", TimeUtils.dateToStringYYYYmmdd(deviceEntity.getJHRQ())));
//        private Date TCRQ;      //投产日期
        dataList.add(new DeviceDetailAdapterEntity("投产日期", TimeUtils.dateToStringYYYYmmdd(deviceEntity.getTCRQ())));
//        private String JLDW;      //计量单位
        dataList.add(new DeviceDetailAdapterEntity("计量单位", deviceEntity.getJLDW()));
//        private String JBR;      //经办人
        dataList.add(new DeviceDetailAdapterEntity("经办人", deviceEntity.getJBR()));
//        private String YSJCBG;      //原始检测报告
        DeviceDetailAdapterEntity ysjcbg = new DeviceDetailAdapterEntity("原始检测报告", Constants.detail_fujian);
        ysjcbg.setImage(true);
        ysjcbg.setImageType(Config.doc_jcbg);
        dataList.add(ysjcbg);
//        private String AZJCXM;      //安装检查项目
        dataList.add(new DeviceDetailAdapterEntity("安装检查项目", deviceEntity.getAZJCXM()));
//        private String FFDJ;      //防腐等级
        dataList.add(new DeviceDetailAdapterEntity("防腐等级", deviceEntity.getFFDJ()));
//        private String CCHGZ;      //出厂合格证
        DeviceDetailAdapterEntity cchgz = new DeviceDetailAdapterEntity("出厂合格证", Constants.detail_fujian);
        cchgz.setImage(true);
        cchgz.setImageType(Config.doc_hgz);
        dataList.add(cchgz);
//        private String ZJFS;      //折旧方式
        dataList.add(new DeviceDetailAdapterEntity("折旧方式", deviceEntity.getZJFS()));
//        private String SYSM;      //物资使用寿命
        dataList.add(new DeviceDetailAdapterEntity("物资使用寿命", deviceEntity.getSYSM()));
//        private String WZMP;      //物资铭牌
        DeviceDetailAdapterEntity wzmp = new DeviceDetailAdapterEntity("物资铭牌", Constants.detail_fujian);
        wzmp.setImage(true);
        wzmp.setImageType(Config.doc_wzmp);
        dataList.add(wzmp);
//        private String WZYZ;      //物资原值
        dataList.add(new DeviceDetailAdapterEntity("物资原值", deviceEntity.getWZYZ()));
//        private String WZJZ;      //物资净值
        dataList.add(new DeviceDetailAdapterEntity("物资净值", deviceEntity.getWZJZ()));
//        private String WZCZ;      //物资残值
        dataList.add(new DeviceDetailAdapterEntity("物资残值", deviceEntity.getWZCZ()));
//        private String JGTZ;      //结构图纸
        DeviceDetailAdapterEntity jgtz = new DeviceDetailAdapterEntity("结构图纸", Constants.detail_fujian);
        jgtz.setImage(true);
        jgtz.setImageType(Config.doc_jgtz);
        dataList.add(jgtz);
//        private String SMS;      //使用维护说明书
        DeviceDetailAdapterEntity sms = new DeviceDetailAdapterEntity("使用维护说明书", Constants.detail_fujian);
        sms.setImage(true);
        sms.setImageType(Config.doc_sywhsms);
        dataList.add(sms);
//        private String YSPJ;      //易损配件
        DeviceDetailAdapterEntity yspj = new DeviceDetailAdapterEntity("易损配件", Constants.detail_fujian);
        yspj.setImage(true);
        yspj.setImageType(Config.doc_yspj);
        dataList.add(yspj);
//        private String QTFJ;      //其他附件
        DeviceDetailAdapterEntity qtfj = new DeviceDetailAdapterEntity("其他附件", Constants.detail_fujian);
        qtfj.setImage(true);
        qtfj.setImageType(Config.doc_qtfj);
        dataList.add(qtfj);
//        private String SFPJ;      //是否配件
        dataList.add(new DeviceDetailAdapterEntity("是否配件", deviceEntity.getSFPJ().equals("1") ? "是" : "否"));
//        private String SSSBBH;      //如果是配件，所属主设备编号
//        dataList.add(new DeviceDetailAdapterEntity("", deviceEntity.getSSSBBH()));
//        private String SBZT;      //设备状态
        String sbzt = deviceEntity.getSBZT();
        if ("0".equals(sbzt)) { //在库
            sbzt = "在库";
        } else if ("1".equals(sbzt)) { //出库
            sbzt = "出库";
        } else if ("2".equals(sbzt)) { //维修
            sbzt = "维修";
        } else if ("4".equals(sbzt)) { //报废
            sbzt = "报废";
        } else if ("5".equals(sbzt)) { //大修
            sbzt = "大修";
        } else if ("6".equals(sbzt)) { //返厂
            sbzt = "返厂";
        }
        dataList.add(new DeviceDetailAdapterEntity("设备状态", sbzt));
//        private Timestamp DJSJ;      //登记时间
        dataList.add(new DeviceDetailAdapterEntity("登记时间", TimeUtils.dateToStringYYYYmmdd(deviceEntity.getDJSJ())));
        if (detailAdapter != null) {
            detailAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SCAN && resultCode == RESULT_OK) {
            String code = data.getExtras().getString("result");
            getDeviceById(code);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DeviceDetailAdapterEntity deviceDetailAdapterEntity = (DeviceDetailAdapterEntity) parent.getItemAtPosition(position);
        if (deviceDetailAdapterEntity.isImage()) {
            getImageData(deviceDetailAdapterEntity.getImageType());
        }
    }

    private void getImageData(String dataLB) {
        final List<LocalMedia> localMedias = new ArrayList<>();
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.Get_Image_Path)
                .params(new String[]{currentDevice.getBH(), dataLB})
                .clazz(DevicedocEntity.class)
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(getResources().getString(R.string.loading_data));
                    }

                    @Override
                    public void onError(String err) {
                        alert(R.string.loading_data_failed);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        if (result != null && result.size() != 0) {
                            for (int i=0; i<result.size(); i++) {
                                LocalMedia localMedia = new LocalMedia();
                                localMedia.setPath(Config.WEB_HOLDE + ((DevicedocEntity) result.get(0)).getWJDZ());
                                localMedias.add(localMedia);
                            }
                            PictureSelectUtils.previewPicture(mActivity, localMedias);
                        } else {
                            alert(R.string.no_data);
                        }
                        dismissProgressDialog();
                    }
                });
    }
}
