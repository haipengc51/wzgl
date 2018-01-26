package com.jiekai.wzglkg.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jiekai.wzglkg.R;
import com.jiekai.wzglkg.adapter.RecordHistoryAdapter;
import com.jiekai.wzglkg.config.Config;
import com.jiekai.wzglkg.config.IntentFlag;
import com.jiekai.wzglkg.config.SqlUrl;
import com.jiekai.wzglkg.entity.DeviceUnCheckEntity;
import com.jiekai.wzglkg.entity.DevicestoreEntity;
import com.jiekai.wzglkg.ui.base.MyBaseActivity;
import com.jiekai.wzglkg.utils.dbutils.DBManager;
import com.jiekai.wzglkg.utils.dbutils.DbCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by LaoWu on 2018/1/5.
 * 现场申请--申请记录
 */

public class RecordHistoryActivity extends MyBaseActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener{
    private static final int CHENGE_SUCCESS = 0;

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.list_view)
    ListView listView;

    private View headerView;
    private RecordHistoryAdapter adapter;
    private List<DeviceUnCheckEntity> dataList = new ArrayList<>();

    @Override
    public void initView() {
        setContentView(R.layout.activity_record_history);
    }

    @Override
    public void initData() {
        title.setText(getResources().getString(R.string.record_check_result));
        back.setOnClickListener(this);
    }

    @Override
    public void initOperation() {
        if (adapter == null) {
            headerView = LayoutInflater.from(mActivity).inflate(R.layout.adapter_record_history, null);
            listView.addHeaderView(headerView);
            adapter = new RecordHistoryAdapter(mActivity, dataList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }
        setHeaderViewVisible(View.GONE);
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DeviceUnCheckEntity entity = (DeviceUnCheckEntity) parent.getItemAtPosition(position);
        if (entity != null) {
            DevicestoreEntity item = (DevicestoreEntity) entity.getData();
            switch (item.getLB()) {
                case Config.LB_IN:
                    Intent intent = new Intent(mActivity, DeviceInDetailActivity.class);
                    intent.putExtra(IntentFlag.DATA, item);
                    startActivityForResult(intent, CHENGE_SUCCESS);
                    break;
                case Config.LB_OUT:
                    Intent intentOut = new Intent(mActivity, DeviceOutDetailActivity.class);
                    intentOut.putExtra(IntentFlag.DATA, item);
                    startActivityForResult(intentOut, CHENGE_SUCCESS);
                    break;
                case Config.LB_WEIXIU:
                case Config.LB_DAXIU:
                case Config.LB_FANCHANG:
                    Intent intentRepair = new Intent(mActivity, DeviceRepairDetailActivity.class);
                    intentRepair.putExtra(IntentFlag.DATA, item);
                    startActivityForResult(intentRepair, CHENGE_SUCCESS);
                    break;
            }
        }
    }

    private void getData() {
        if (dataList != null) {
            dataList.clear();
        } else {
            dataList = new ArrayList<>();
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            setHeaderViewVisible(View.GONE);
        }
        DBManager.NewDbDeal(DBManager.SELECT)
                .sql(SqlUrl.GET_STORE_CHECK_LIST)
                .params(new String[]{userData.getUSERID()})
                .clazz(DevicestoreEntity.class)
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(getResources().getString(R.string.loading_data));
                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        if (result != null && result.size() != 0) {
                            for (int i=0; i<result.size(); i++) {
                                DevicestoreEntity devicestoreEntity = (DevicestoreEntity) result.get(i);
                                DeviceUnCheckEntity deviceUnCheckEntity = new DeviceUnCheckEntity();
                                deviceUnCheckEntity.setType(Config.TYPE_STOR);
                                deviceUnCheckEntity.setID(devicestoreEntity.getSBBH());
                                deviceUnCheckEntity.setYJ(devicestoreEntity.getSHYJ());
                                deviceUnCheckEntity.setData(devicestoreEntity);
                                dataList.add(deviceUnCheckEntity);
                            }
                            adapter.notifyDataSetChanged();
                            setHeaderViewVisible(View.VISIBLE);
                        } else {
                            alert(R.string.your_all_check_pass);
                        }
                        dismissProgressDialog();
                    }
                });
    }

    private void setHeaderViewVisible(int visible) {
        if (headerView != null) {
            headerView.setVisibility(visible);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHENGE_SUCCESS && resultCode == RESULT_OK) {
            getData();
        }
    }
}
