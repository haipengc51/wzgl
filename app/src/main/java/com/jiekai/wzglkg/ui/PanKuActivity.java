package com.jiekai.wzglkg.ui;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiekai.wzglkg.AppContext;
import com.jiekai.wzglkg.R;
import com.jiekai.wzglkg.adapter.PankuDataListAdapter;
import com.jiekai.wzglkg.config.SqlUrl;
import com.jiekai.wzglkg.entity.PankuDataEntity;
import com.jiekai.wzglkg.entity.PankuDataListEntity;
import com.jiekai.wzglkg.entity.PankuDataNumEntity;
import com.jiekai.wzglkg.test.NFCBaseActivity;
import com.jiekai.wzglkg.utils.StringUtils;
import com.jiekai.wzglkg.utils.dbutils.DBManager;
import com.jiekai.wzglkg.utils.dbutils.DbCallBack;
import com.jiekai.wzglkg.utils.dbutils.DbDeal;
import com.jiekai.wzglkg.utils.localDbUtils.PanKuDataListColumn;
import com.jiekai.wzglkg.utils.localDbUtils.PanKuDataNumColumn;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

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

    private AlertDialog alertDialog;
    private AlertDialog ifContinueDialog;

    private List oldData = new ArrayList();

    private DbDeal dbDeal = null;
    private DbDeal eventDbDeal = null;

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
        alertDialog = new AlertDialog.Builder(mActivity)
                .setTitle("提示").create();
        alertDialog.setMessage("您本次盘库的内容还没有上传，如果您继续退出下次进入可以继续本次盘库操作。");
        alertDialog.setButton(BUTTON_POSITIVE, "上传数据", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                startEnv();
            }
        });
        alertDialog.setButton(BUTTON_NEGATIVE, "继续退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                oldData = null;
                finish();
            }
        });

        ifContinueDialog = new AlertDialog.Builder(mActivity)
                .setTitle("提示").create();
        ifContinueDialog.setMessage("您上次盘库记录没有保存，是否继续上次盘库，还是清空盘库信息重新开始盘库？");
        ifContinueDialog.setButton(BUTTON_POSITIVE, "重新盘库", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deletAllData();
                alertDialog.dismiss();
            }
        });
        ifContinueDialog.setButton(BUTTON_NEGATIVE, "继续上次盘库", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i=0; i<oldData.size(); i++) {
                    PankuDataEntity pankuDataEntity = (PankuDataEntity) oldData.get(i);
                    PankuDataListEntity entity = new PankuDataListEntity();
                    entity.setMC(pankuDataEntity.getMC());
                    entity.setBH(pankuDataEntity.getBH());
                    entity.setLB(pankuDataEntity.getLeibie());
                    entity.setXH(pankuDataEntity.getXinghao());
                    entity.setGG(pankuDataEntity.getGuige());
                    pankuDataListDatas.add(entity);
                }
                alertDialog.dismiss();
            }
        });
        ifContinueDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (oldData != null && oldData.size() != 0) {
                    pankuDataListAdapter.notifyDataSetChanged();
                }
            }
        });
        getPanKuOldData();
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
        getDeviceDataById(nfcString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                closeUi();
                break;
            case R.id.start:    //开始
                nfcEnable = !nfcEnable;
                if (nfcEnable) {
                    alert(getResources().getString(R.string.please_nfc));
                    start.setText("暂停");
                } else {
                    alert(R.string.nfc_stop);
                    start.setText("开始");
                }
                break;
            case R.id.finish:   //结束
                nfcEnable = false;
                clearLocalDB();
                break;
            case R.id.data_upload:  //数据上传
                startEnv();
                break;
            case R.id.data_detail:  //数据详情（统计数据个数）
                startActivity(new Intent(mActivity, PanKuNumActivity.class));
                break;
        }
    }

    private void getPanKuOldData() {
        dbDeal = DBManager.dbDeal(DBManager.SELECT);
                dbDeal.sql(SqlUrl.Get_Old_Panku)
                .clazz(PankuDataEntity.class)
                .execut(mContext, new DbCallBack() {
                    @Override
                    public void onDbStart() {

                    }

                    @Override
                    public void onError(String err) {
                        alert("获取上次盘库数据失败");
                        finish();
                    }

                    @Override
                    public void onResponse(List result) {
                        if (result != null && result.size() != 0) {
                            oldData = result;
                            ifContinueDialog.show();
                        }
                    }
                });
    }

    /**
     * 通过ID卡号获取设备信息
     * @param id
     */
    private void getDeviceDataById(String id) {
        if (StringUtils.isEmpty(id)) {
            return;
        }
        dbDeal = DBManager.dbDeal(DBManager.SELECT);
                dbDeal.sql(SqlUrl.GetPanKuDataByID)
                .params(new String[]{id, id, id})
                .clazz(PankuDataEntity.class)
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
                        if (result != null && result.size() != 0) {
//                            addLocalSqlDB((PankuDataEntity) result.get(0));
//                            addRomteSqlDB((PankuDataEntity) result.get(0));
                            PankuDataEntity pankuDataEntity = (PankuDataEntity) result.get(0);
                            checkIfPanku(pankuDataEntity.getBH(), pankuDataEntity);
                        } else {
                            alert(getResources().getString(R.string.no_data));
                        }
                    }
                });
    }

    private void checkIfPanku(String SBBH, final PankuDataEntity pankuDataEntity) {
        dbDeal = DBManager.dbDeal(DBManager.SELECT);
                dbDeal.sql(SqlUrl.DEVICE_IS_PANKU)
                .params(new String[]{SBBH})
                .clazz(PankuDataEntity.class)
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
                        if (result != null && result.size() != 0) {
                            alert(R.string.device_has_panku);
                            dismissProgressDialog();
                        } else {
                            addRomteSqlDB(pankuDataEntity);
                        }
                    }
                });
    }

    /**
     * 删除所有的没有确定的数据(删除上次盘库但是没有确定的数据，从本次开始进行盘库)
     */
    private void deletAllData() {
        dbDeal = DBManager.dbDeal(DBManager.DELET);
                dbDeal.sql(SqlUrl.DELET_OLD_PANKU)
                .params(new String[]{"0"})
                .execut(mContext, new DbCallBack() {
                    @Override
                    public void onDbStart() {

                    }

                    @Override
                    public void onError(String err) {
                        alert("删除上次盘库数据失败");
                        finish();
                    }

                    @Override
                    public void onResponse(List result) {

                    }
                });
    }

    /**
     * 插入盘库的数据库中
     * @param pankuDataEntity
     */
    private void addRomteSqlDB(final PankuDataEntity pankuDataEntity) {
        if (pankuDataEntity == null || pankuDataEntity.getBH() == null) {
            alert(R.string.update_db_failed);
            dismissProgressDialog();
            return;
        }
        dbDeal = DBManager.dbDeal(DBManager.INSERT);
                dbDeal.sql(SqlUrl.INSERT_PANKU)
                .params(new Object[]{pankuDataEntity.getBH(), pankuDataEntity.getMC(),
                        pankuDataEntity.getLB(), pankuDataEntity.getXH(), pankuDataEntity.getGG(),
                        userData.getUSERID(), new Date(System.currentTimeMillis()), "0"})
                .execut(mContext, new DbCallBack() {
                    @Override
                    public void onDbStart() {

                    }

                    @Override
                    public void onError(String err) {
                        alert(R.string.this_device_panku_failed);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        alert(R.string.this_device_panku_success);
                        dismissProgressDialog();
                        PankuDataListEntity entity = new PankuDataListEntity();
                        entity.setMC(pankuDataEntity.getMC());
                        entity.setBH(pankuDataEntity.getBH());
                        entity.setLB(pankuDataEntity.getLeibie());
                        entity.setXH(pankuDataEntity.getXinghao());
                        entity.setGG(pankuDataEntity.getGuige());
                        pankuDataListAdapter.addItem(recyclerView, entity);
                    }
                });
    }

    /**
     * 开启事务
     */
    private void startEnv() {
        eventDbDeal = DBManager.dbDeal(DBManager.START_EVENT);
                eventDbDeal.execut(mContext, new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(getResources().getString(R.string.uploading_db));
                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        deletDate();
                    }
                });
    }

    private void deletDate() {
        if (eventDbDeal == null) {
            rollback();
            dismissProgressDialog();
            return;
        }
        eventDbDeal.reset(DBManager.EVENT_DELET)
                .sql(SqlUrl.DELET_OLD_PANKU)
                .params(new String[]{"1"})
                .execut(mContext, new DbCallBack() {
                    @Override
                    public void onDbStart() {

                    }

                    @Override
                    public void onError(String err) {
                        rollback();
                        alert(err);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        uploadDate();
                    }
                });
    }

    private void uploadDate() {
        if (eventDbDeal == null) {
            rollback();
            dismissProgressDialog();
            return;
        }
        eventDbDeal.reset(DBManager.EVENT_UPDATA)
                .sql(SqlUrl.UPLOAD_PANKU_DATE)
                .execut(mContext, new DbCallBack() {
                    @Override
                    public void onDbStart() {

                    }

                    @Override
                    public void onError(String err) {
                        rollback();
                        alert(err);
                        dismissProgressDialog();
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
                        alert(R.string.uploading_panku_faild);
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
                        alert(R.string.uploading_panku_faild);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        alert(R.string.uploading_panku_success);
                        dismissProgressDialog();
                        finish();
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
            int num = (int) result.get(0).getNUM();
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

    @Override
    public void onBackPressed() {
        closeUi();
    }

    private void closeUi() {
        if (oldData != null && oldData.size() != 0) {
            alertDialog.show();
        } else {
            finish();
        }
    }
}
