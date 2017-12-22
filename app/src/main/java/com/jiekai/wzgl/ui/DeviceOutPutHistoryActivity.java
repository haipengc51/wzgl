package com.jiekai.wzgl.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.adapter.DeviceOutputHistoryAdapter;
import com.jiekai.wzgl.ui.base.MyBaseActivity;
import com.jiekai.wzgl.utils.TimePickerDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laowu on 2017/12/21.
 * 设备出库历史查询
 * 数据来源还有数据显示的东西有多少？先写界面吧
 */

public class DeviceOutPutHistoryActivity extends MyBaseActivity implements View.OnClickListener {
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
    private List<String> dataList = new ArrayList<>();
    private String[] historyListData = new String[]{"", "", "", "", "", "", ""
            , "", "", "", "", "", ""
            , "", "", "", "", "", ""
            , "", "", "", "", "", ""};

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

        for (int i=0; i<historyListData.length; i++) {
            dataList.add(historyListData[i]);
        }

        if (historyAdapter == null) {
            historyAdapter = new DeviceOutputHistoryAdapter(mActivity, dataList);
            View headerView = LayoutInflater.from(mActivity).inflate(R.layout.adapter_device_output_history_list, null);
            listview.addHeaderView(headerView);
            listview.setAdapter(historyAdapter);
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

                break;
        }
    }

    private TimePickerDialog.TimePickerDialogInterface startDataInterface = new TimePickerDialog.TimePickerDialogInterface() {
        @Override
        public void positiveListener() {
            startData.setText(startDataDialog.getYear() + "年"+
                startDataDialog.getMonth() + "月" +
                startDataDialog.getDay() + "日");
        }

        @Override
        public void negativeListener() {

        }
    };

    private TimePickerDialog.TimePickerDialogInterface endDataInterface = new TimePickerDialog.TimePickerDialogInterface() {
        @Override
        public void positiveListener() {
            endData.setText(endDataDialog.getYear() + "年"+
                    endDataDialog.getMonth() + "月" +
                    endDataDialog.getDay() + "日");
        }

        @Override
        public void negativeListener() {

        }
    };
}
