package com.jiekai.wzgl.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.adapter.DeviceDetailAdapter;
import com.jiekai.wzgl.adapter.DeviceDetailAdapterEntity;
import com.jiekai.wzgl.config.Constants;
import com.jiekai.wzgl.config.SqlUrl;
import com.jiekai.wzgl.entity.DeviceEntity;
import com.jiekai.wzgl.test.NFCBaseActivity;
import com.jiekai.wzgl.utils.StringUtils;
import com.jiekai.wzgl.utils.TimeUtils;
import com.jiekai.wzgl.utils.dbutils.DBManager;
import com.jiekai.wzgl.utils.dbutils.DbCallBack;
import com.jiekai.wzgl.utils.zxing.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laowu on 2017/12/22.
 * 设备详细信息
 */

public class DeviceDetailActivity extends NFCBaseActivity implements View.OnClickListener {
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
    RelativeLayout buttonLayout;

    private DeviceDetailAdapter detailAdapter;
    private List<DeviceDetailAdapterEntity> dataList = new ArrayList<>();
    private AlertDialog alertDialog;

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
                .sql(SqlUrl.GetDeviceByID)
                .params(new String[]{nfcString})
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
                            paresDeviceToShow((DeviceEntity) result.get(0));
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
    private void paresDeviceToShow(DeviceEntity deviceEntity) {
        dataList.clear();
        dataList.add(new DeviceDetailAdapterEntity("设备自编号", deviceEntity.getBH()));
//        private String MC;      //设备名称
        dataList.add(new DeviceDetailAdapterEntity("设备名称", deviceEntity.getMC()));
//        private String LB;      //设备类别
        dataList.add(new DeviceDetailAdapterEntity("设备类别", deviceEntity.getLB()));
//        private String XH;      //设备型号
        dataList.add(new DeviceDetailAdapterEntity("设备型号", deviceEntity.getXH()));
//        private String GG;      //设备规格
        dataList.add(new DeviceDetailAdapterEntity("设备规格", deviceEntity.getGG()));
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
        dataList.add(new DeviceDetailAdapterEntity("原始检测报告", deviceEntity.getYSJCBG()));
//        private String AZJCXM;      //安装检查项目
        dataList.add(new DeviceDetailAdapterEntity("安装检查项目", deviceEntity.getAZJCXM()));
//        private String FFDJ;      //防腐等级
        dataList.add(new DeviceDetailAdapterEntity("防腐等级", deviceEntity.getFFDJ()));
//        private String CCHGZ;      //出厂合格证
        dataList.add(new DeviceDetailAdapterEntity("出厂合格证", deviceEntity.getCCHGZ()));
//        private String ZJFS;      //折旧方式
        dataList.add(new DeviceDetailAdapterEntity("折旧方式", deviceEntity.getZJFS()));
//        private String SYSM;      //物资使用寿命
        dataList.add(new DeviceDetailAdapterEntity("物资使用寿命", deviceEntity.getSYSM()));
//        private String WZMP;      //物资铭牌
        dataList.add(new DeviceDetailAdapterEntity("物资铭牌", deviceEntity.getWZMP()));
//        private String WZYZ;      //物资原值
        dataList.add(new DeviceDetailAdapterEntity("物资原值", deviceEntity.getWZYZ()));
//        private String WZJZ;      //物资净值
        dataList.add(new DeviceDetailAdapterEntity("物资净值", deviceEntity.getWZJZ()));
//        private String WZCZ;      //物资残值
        dataList.add(new DeviceDetailAdapterEntity("物资残值", deviceEntity.getWZCZ()));
//        private String JGTZ;      //结构图纸
        dataList.add(new DeviceDetailAdapterEntity("结构图纸", deviceEntity.getJGTZ()));
//        private String SMS;      //使用维护说明书
        dataList.add(new DeviceDetailAdapterEntity("使用维护说明书", deviceEntity.getSMS()));
//        private String YSPJ;      //易损配件
        dataList.add(new DeviceDetailAdapterEntity("易损配件", deviceEntity.getYSPJ()));
//        private String QTFJ;      //其他附件
        dataList.add(new DeviceDetailAdapterEntity("其他附件", deviceEntity.getQTFJ()));
//        private String SFPJ;      //是否配件
        dataList.add(new DeviceDetailAdapterEntity("是否配件", deviceEntity.getSFPJ().equals("0") ? "是" : "否"));
//        private String SSSBBH;      //如果是配件，所属主设备编号
        dataList.add(new DeviceDetailAdapterEntity("", deviceEntity.getSSSBBH()));
//        private String SBZT;      //设备状态
        dataList.add(new DeviceDetailAdapterEntity("设备状态", deviceEntity.getSBZT()));
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
}
