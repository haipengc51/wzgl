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
import com.jiekai.wzglkg.entity.DeviceUnCheckEntity;
import com.jiekai.wzglkg.ui.base.MyBaseActivity;

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
    private int selectNum;

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
            switch (entity.getType()) {
                case Config.TYPE_JL:
                    Intent intent = new Intent(mActivity, RecordHistoryDetailActivity.class);
                    intent.putExtra(IntentFlag.DATA, (DevicelogEntity) entity.getData());
                    startActivityForResult(intent, CHENGE_SUCCESS);
                    break;
                case Config.TYPE_MOVE:
                    Intent intent_move = new Intent(mActivity, MoveHistoryDetailActivity.class);
                    intent_move.putExtra(IntentFlag.DATA, (DevicemoveEntity) entity.getData());
                    startActivityForResult(intent_move, CHENGE_SUCCESS);
                    break;
                case Config.TYPE_INSPECTION:    //巡检
                    Intent inspection = new Intent(mActivity, InspectionHistoryDetailActivity.class);
                    inspection.putExtra(IntentFlag.DATA, (DeviceInspectionEntity) entity.getData());
                    startActivityForResult(inspection, CHENGE_SUCCESS);
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
        selectNum = 0;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            setHeaderViewVisible(View.GONE);
        }
        DBManager.NewDbDeal(DBManager.SELECT)
                .sql(SqlUrl.GET_RECORD_CHECK_LIST)
                .params(new String[]{userData.getUSERID()})
                .clazz(DevicelogEntity.class)
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
                                DevicelogEntity devicelogEntity = (DevicelogEntity) result.get(i);
                                DeviceUnCheckEntity deviceUnCheckEntity = new DeviceUnCheckEntity();
                                deviceUnCheckEntity.setType(Config.TYPE_JL);
                                deviceUnCheckEntity.setID(devicelogEntity.getSBBH());
                                deviceUnCheckEntity.setJLZL(devicelogEntity.getJLZLMC());
                                deviceUnCheckEntity.setYJ(devicelogEntity.getSHYJ());
                                deviceUnCheckEntity.setData(devicelogEntity);
                                dataList.add(deviceUnCheckEntity);
                            }
                            adapter.notifyDataSetChanged();
                            setHeaderViewVisible(View.VISIBLE);
                        }
                        allSelectFinish();
                    }
                });
        DBManager.NewDbDeal(DBManager.SELECT)
                .sql(SqlUrl.GET_MOVE_CHECK_LIST)
                .params(new String[]{userData.getUSERID()})
                .clazz(DevicemoveEntity.class)
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
                        if (result != null && result.size() != 0) {
                            for (int i=0; i<result.size(); i++) {
                                DevicemoveEntity devicemoveEntity = (DevicemoveEntity) result.get(i);
                                DeviceUnCheckEntity deviceUnCheckEntity = new DeviceUnCheckEntity();
                                deviceUnCheckEntity.setType(Config.TYPE_MOVE);
                                deviceUnCheckEntity.setID(devicemoveEntity.getSBBH());
                                deviceUnCheckEntity.setJLZL(getResources().getString(R.string.device_move));
                                deviceUnCheckEntity.setYJ(devicemoveEntity.getSHYJ());
                                deviceUnCheckEntity.setData(devicemoveEntity);
                                dataList.add(deviceUnCheckEntity);
                            }
                            adapter.notifyDataSetChanged();
                            setHeaderViewVisible(View.VISIBLE);
                        }
                        allSelectFinish();
                    }
                });
        DBManager.NewDbDeal(DBManager.SELECT)
                .sql(SqlUrl.GET_INSPECTION_CHECK_LIST)
                .params(new String[]{userData.getUSERID()})
                .clazz(DeviceInspectionEntity.class)
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
                        if (result != null && result.size() != 0) {
                            for (int i=0; i<result.size(); i++) {
                                DeviceInspectionEntity devicemoveEntity = (DeviceInspectionEntity) result.get(i);
                                DeviceUnCheckEntity deviceUnCheckEntity = new DeviceUnCheckEntity();
                                deviceUnCheckEntity.setType(Config.TYPE_INSPECTION);
                                deviceUnCheckEntity.setID(devicemoveEntity.getSBBH());
                                deviceUnCheckEntity.setJLZL(getResources().getString(R.string.device_inspection));
                                deviceUnCheckEntity.setYJ(devicemoveEntity.getSHYJ());
                                deviceUnCheckEntity.setData(devicemoveEntity);
                                dataList.add(deviceUnCheckEntity);
                            }
                            adapter.notifyDataSetChanged();
                            setHeaderViewVisible(View.VISIBLE);
                        }
                        allSelectFinish();
                    }
                });
    }

    private void allSelectFinish() {
        selectNum++;
        if (selectNum >= 3) {
            if (adapter != null ){
                if (adapter.dataList == null || adapter.dataList.size() == 0) {
                    alert(R.string.your_all_check_pass);
                    setHeaderViewVisible(View.GONE);
                }
            }
            dismissProgressDialog();
        }
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
