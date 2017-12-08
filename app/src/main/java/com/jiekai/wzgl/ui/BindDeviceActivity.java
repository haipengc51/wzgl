package com.jiekai.wzgl.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.adapter.TextPopupAdapter;
import com.jiekai.wzgl.ui.base.MyBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laowu on 2017/12/7.
 * 设备绑定界面
 */

public class BindDeviceActivity extends MyBaseActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener {
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
    @BindView(R.id.parent)
    LinearLayout parent;

    private String[] deviceCache = new String[]{
//            "抛光机", "粉碎机", "除湿机", "等等机器",
//            "抛光机", "粉碎机", "除湿机", "等等机器",
//            "抛光机", "粉碎机", "除湿机", "等等机器",
//            "抛光机", "粉碎机", "除湿机", "等等机器",
//            "抛光机", "粉碎机", "除湿机", "等等机器",
            "抛光机", "粉碎机", "除湿机", "等等机器"
    };
    private PopupWindowUtils popupWindowUtils;
    private TextView popTitle;
    private ListView popList;
    private TextPopupAdapter textPopupAdapter;
    private List<String> popListData = new ArrayList<>();

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

        initPopupWindow();
    }

    private void initPopupWindow() {
        View view = LayoutInflater.from(this)
                .inflate(R.layout.spinner_base_popup_window, null);
        popTitle = (TextView) view.findViewById(R.id.pop_title);
        popList = (ListView) view.findViewById(R.id.pop_list);
        popupWindowUtils = new PopupWindowUtils(this, view);
        if (textPopupAdapter == null) {
            textPopupAdapter = new TextPopupAdapter(BindDeviceActivity.this, popListData);
            popList.setAdapter(textPopupAdapter);
            popList.setOnItemClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.device_type:
                popTitle.setText(getResources().getString(R.string.device_type));
                popListData.clear();
                for (int i = 0; i < deviceCache.length; i++) {
                    popListData.add(i, deviceCache[i]);
                }
                textPopupAdapter.notifyDataSetChanged();
                popupWindowUtils.showCenter(v);
                break;
            case R.id.device_name:

                break;
            case R.id.device_id:

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
