package com.jiekai.wzgl.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiekai.wzgl.AppContext;
import com.jiekai.wzgl.R;
import com.jiekai.wzgl.adapter.PankuDataListAdapter;
import com.jiekai.wzgl.config.SqlUrl;
import com.jiekai.wzgl.entity.DeviceEntity;
import com.jiekai.wzgl.entity.PankuDataEntity;
import com.jiekai.wzgl.entity.PankuDataListEntity;
import com.jiekai.wzgl.entity.PankuDataNumEntity;
import com.jiekai.wzgl.test.NFCBaseActivity;
import com.jiekai.wzgl.utils.StringUtils;
import com.jiekai.wzgl.utils.dbutils.DBManager;
import com.jiekai.wzgl.utils.dbutils.DbCallBack;
import com.jiekai.wzgl.utils.localDbUtils.PanKuDataListColumn;
import com.jiekai.wzgl.utils.localDbUtils.PanKuDataNumColumn;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laowu on 2017/12/23.
 * 盘库
 */

public class PanKuActivity extends NFCBaseActivity implements View.OnClickListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.start)
    TextView start;
    @BindView(R.id.finish)
    TextView finish;
    @BindView(R.id.data_upload)
    TextView dataUpload;
    @BindView(R.id.data_detail)
    TextView dataDetail;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private PankuDataListAdapter pankuDataListAdapter;
    private List<PankuDataListEntity> pankuDataListDatas = new ArrayList<>();

    @Override
    public void initView() {
        setContentView(R.layout.activity_paku);
    }

    @Override
    public void initData() {
        title.setText(getResources().getString(R.string.pan_ku));

        back.setOnClickListener(this);
        start.setOnClickListener(this);
        finish.setOnClickListener(this);
        dataUpload.setOnClickListener(this);
        dataDetail.setOnClickListener(this);
    }

    @Override
    public void initOperation() {
        if (pankuDataListAdapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
            layoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(layoutManager);
            pankuDataListAdapter = new PankuDataListAdapter(mActivity, pankuDataListDatas);
            recyclerView.setAdapter(pankuDataListAdapter);
            View headerView = LayoutInflater.from(mActivity).inflate(R.layout.adapter_panu_data_list, recyclerView, false);
            pankuDataListAdapter.addHeaderView(headerView);
        }
    }

    @Override
    public void getNfcData(String nfcString) {
        getDeviceDataById(nfcString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.start:    //开始
                nfcEnable = true;
                alert(getResources().getString(R.string.please_nfc));
                break;
            case R.id.finish:   //结束
                nfcEnable = false;
                alert(getResources().getString(R.string.stop_pan_ku));
                clearLocalDB();
                break;
            case R.id.data_upload:  //数据上传

                break;
            case R.id.data_detail:  //数据详情（统计数据个数）
                startActivity(new Intent(mActivity, PanKuNumActivity.class));
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
                .sql(SqlUrl.GetPanKuDataByID)
                .params(new String[]{id})
                .clazz(PankuDataEntity.class)
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
                            addLocalSqlDB((PankuDataEntity) result.get(0));
                        } else {
                            alert(getResources().getString(R.string.no_data));
                        }
                    }
                });
    }

    /**
     * 插入到本地数据库中的盘库列表中
     * @param pankuDataEntity
     */
    private void addLocalSqlDB(PankuDataEntity pankuDataEntity) {
        if (pankuDataEntity == null || pankuDataEntity.getBH() == null) {
            return;
        }
        String sql = "SELECT * FROM " + PanKuDataListColumn.TABLE_NAME + " WHERE " + PanKuDataListColumn.BH + " = ?";
        List<PankuDataListEntity> result = AppContext.dbHelper.selectAll(sql, PankuDataListEntity.class, new String[]{pankuDataEntity.getBH()});
        if (result != null && result.size() != 0) { //已经盘库了
            alert(getResources().getString(R.string.device_has_panku));
        } else {    //没有盘库时，插入到盘库列表中
            ContentValues contentValues = new ContentValues();
            contentValues.put(PanKuDataListColumn.BH, pankuDataEntity.getBH());
            contentValues.put(PanKuDataListColumn.MC, pankuDataEntity.getMC());
            contentValues.put(PanKuDataListColumn.LB, pankuDataEntity.getLB());
            contentValues.put(PanKuDataListColumn.XH, pankuDataEntity.getXH());
            contentValues.put(PanKuDataListColumn.GG, pankuDataEntity.getGG());
            contentValues.put(PanKuDataListColumn.IDDZMBH1, pankuDataEntity.getIDDZMBH1());
            long insertResult = AppContext.dbHelper.insertSql(PanKuDataListColumn.TABLE_NAME, contentValues);
            if (insertResult != -1) {   //插入数据库成功
                boolean updataResult = updateLocalSqlDBNumber(pankuDataEntity);
                if (updataResult) {     //更新设备类型数量成功
                    PankuDataListEntity entity = new PankuDataListEntity();
                    entity.setMC(pankuDataEntity.getMC());
                    entity.setBH(pankuDataEntity.getBH());
                    entity.setLB(pankuDataEntity.getLB());
                    entity.setXH(pankuDataEntity.getXH());
                    entity.setGG(pankuDataEntity.getGG());
                    entity.setIDDZMBH1(pankuDataEntity.getIDDZMBH1());
                    pankuDataListAdapter.addItem(recyclerView, entity);
                } else {    //更新设备类型数量失败
                    String wheres = PanKuDataListColumn.BH + " = ?";
                    String deletBh = pankuDataEntity.getBH();
                    if (!StringUtils.isEmpty(deletBh)) {
                        AppContext.dbHelper.delete(PanKuDataListColumn.TABLE_NAME, wheres, new String[]{deletBh});
                    }
                    alert(getResources().getString(R.string.this_device_panku_failed));
                }
            } else {    //插入数据库失败
                alert(getResources().getString(R.string.this_device_panku_failed));
            }
        }
    }

    /**
     * 更新本地数据中的盘库数量
     * @param pankuDataEntity
     */
    private boolean updateLocalSqlDBNumber(PankuDataEntity pankuDataEntity) {
        if (pankuDataEntity == null) {
            return false;
        }
        String sql = "SELECT * FROM " + PanKuDataNumColumn.TABLE_NAME + " WHERE " +
                PanKuDataNumColumn.LB + " = ? AND " +
                PanKuDataNumColumn.XH + " = ? AND " +
                PanKuDataNumColumn.GG + " = ?";
        List<PankuDataNumEntity> result = AppContext.dbHelper.selectAll(sql, PankuDataNumEntity.class,
                new String[]{pankuDataEntity.getLB(), pankuDataEntity.getXH(), pankuDataEntity.getGG()});
        if (result != null && result.size() != 0) { //设备中含有条数据，加一就好了
            ContentValues contentValues = new ContentValues();
            int num = Integer.valueOf(result.get(0).getNUM()).intValue();
            contentValues.put(PanKuDataNumColumn.NUM, String.valueOf(num+1));
            String where = PanKuDataNumColumn.LB + " = ? AND " +
                    PanKuDataNumColumn.XH + " = ? AND " +
                    PanKuDataNumColumn.GG + " = ?";
            int updateResult = AppContext.dbHelper.update(PanKuDataNumColumn.TABLE_NAME, contentValues, where,
                    new String[]{pankuDataEntity.getLB(), pankuDataEntity.getXH(), pankuDataEntity.getGG()});
            if (updateResult != 0) {
                return true;
            } else {
                return false;
            }
        } else {    //设备中没有这条数据，添加这条数据
            ContentValues contentValues = new ContentValues();
            contentValues.put(PanKuDataNumColumn.LB, pankuDataEntity.getLB());
            contentValues.put(PanKuDataNumColumn.XH, pankuDataEntity.getXH());
            contentValues.put(PanKuDataNumColumn.GG, pankuDataEntity.getGG());
            contentValues.put(PanKuDataNumColumn.NUM, "1");
            long insertResult = AppContext.dbHelper.insertSql(PanKuDataNumColumn.TABLE_NAME, contentValues);
            if (insertResult == -1) {
                return false;
            } else {
                return true;
            }
        }
    }

    private void clearLocalDB() {
        String deletPankuList = "DELETE FROM " + PanKuDataListColumn.TABLE_NAME;
        AppContext.dbHelper.execSQL(deletPankuList);
        String deletpankuNum = "DELETE FROM " + PanKuDataNumColumn.TABLE_NAME;
        AppContext.dbHelper.execSQL(deletpankuNum);
        pankuDataListAdapter.clearData();
    }
}
