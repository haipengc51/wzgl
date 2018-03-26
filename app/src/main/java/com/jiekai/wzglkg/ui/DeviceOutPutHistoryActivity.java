package com.jiekai.wzglkg.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jiekai.wzglkg.R;
import com.jiekai.wzglkg.adapter.DeviceOutputHistoryAdapter;
import com.jiekai.wzglkg.config.IntentFlag;
import com.jiekai.wzglkg.config.SqlUrl;
import com.jiekai.wzglkg.entity.DevicestoreEntity;
import com.jiekai.wzglkg.ui.base.MyBaseActivity;
import com.jiekai.wzglkg.utils.StringUtils;
import com.jiekai.wzglkg.utils.TimePickerDialog;
import com.jiekai.wzglkg.utils.dbutils.DBManager;
import com.jiekai.wzglkg.utils.dbutils.DbCallBack;
import com.jiekai.wzglkg.utils.dbutils.DbDeal;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by laowu on 2017/12/21.
 * 设备出库历史查询
 * 数据来源还有数据显示的东西有多少？先写界面吧
 */

public class DeviceOutPutHistoryActivity extends MyBaseActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.start_data)
    TextView startData;
    @BindView(R.id.end_data)
    TextView endData;
    @BindView(R.id.find)
    LinearLayout find;
    @BindView(R.id.listview)
    ListView listview;

    private TimePickerDialog startDataDialog;
    private TimePickerDialog endDataDialog;
    private DeviceOutputHistoryAdapter historyAdapter;
    private List<DevicestoreEntity> dataList = new ArrayList<>();

    private String startTiem;
    private String endTime;

    private DbDeal dbDeal = null;

    @Override
    public void initView() {
        setContentView(R.layout.activity_device_out_history);
    }

    @Override
    public void initData() {
        title.setText(getResources().getString(R.string.device_out_history));
        back.setOnClickListener(this);
        startData.setOnClickListener(this);
        endData.setOnClickListener(this);
        find.setOnClickListener(this);
    }

    @Override
    public void initOperation() {
        startDataDialog = new TimePickerDialog(mActivity, startDataInterface);
        endDataDialog = new TimePickerDialog(mActivity, endDataInterface);

        if (historyAdapter == null) {
            historyAdapter = new DeviceOutputHistoryAdapter(mActivity, dataList);
            View headerView = LayoutInflater.from(mActivity).inflate(R.layout.adapter_device_output_history_list, null);
            listview.addHeaderView(headerView);
            listview.setAdapter(historyAdapter);
            listview.setOnItemClickListener(this);
        }
    }

    @Override
    public void cancleDbDeal() {
        if (dbDeal != null) {
            dbDeal.cancleDbDeal();
            dismissProgressDialog();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.start_data:
                startDataDialog.showDatePickerDialog();
                break;
            case R.id.end_data:
                endDataDialog.showDatePickerDialog();
                break;
            case R.id.find:
                getList();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DevicestoreEntity item = (DevicestoreEntity) parent.getItemAtPosition(position);
        if (item != null) {
            Intent intent = new Intent(mActivity, RecordDeviceOutDetailActivity.class);
            intent.putExtra(IntentFlag.DATA, item);
            startActivity(intent);
        }
    }

    /**
     * 获取出库历史
     */
    private void getList() {
        startTiem = startData.getText().toString();
        endTime = endData.getText().toString();
        if (StringUtils.isEmpty(startTiem)) {
            alert(R.string.input_start_data);
            return;
        }
        if (StringUtils.isEmpty(endTime)) {
            alert(R.string.input_end_data);
            return;
        }
        dbDeal = DBManager.dbDeal(DBManager.SELECT);
                dbDeal.sql(SqlUrl.GET_OUT_HISTORY)
                .params(new Object[]{ Date.valueOf(startTiem), Date.valueOf(endTime)})
                .clazz(DevicestoreEntity.class)
                .execut(mContext, new DbCallBack() {
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
                            dataList.clear();
                            dataList.addAll(result);
                        } else {
                            alert(R.string.no_data);
                            dataList.clear();
                        }
                        historyAdapter.notifyDataSetChanged();
                        dismissProgressDialog();
                    }
                });
    }

    private TimePickerDialog.TimePickerDialogInterface startDataInterface = new TimePickerDialog.TimePickerDialogInterface() {
        @Override
        public void positiveListener() {
            startData.setText(startDataDialog.getYear() + "-"+
                startDataDialog.getMonth() + "-" +
                startDataDialog.getDay());
        }

        @Override
        public void negativeListener() {

        }
    };

    private TimePickerDialog.TimePickerDialogInterface endDataInterface = new TimePickerDialog.TimePickerDialogInterface() {
        @Override
        public void positiveListener() {
            endData.setText(endDataDialog.getYear() + "-"+
                    endDataDialog.getMonth() + "-" +
                    endDataDialog.getDay());
        }

        @Override
        public void negativeListener() {

        }
    };
}
