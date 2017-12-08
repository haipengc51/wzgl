package com.jiekai.wzgl.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.adapter.TextSpinnerAdapter;
import com.jiekai.wzgl.ui.base.MyBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by laowu on 2017/12/7.
 * 设备绑定界面
 */

public class BindDeviceActivity extends MyBaseActivity implements View.OnClickListener{
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

    private String[] deviceCache = new String[]{"抛光机", "粉碎机", "除湿机", "等等机器",
            "抛光机", "粉碎机", "除湿机", "等等机器",
            "抛光机", "粉碎机", "除湿机", "等等机器",
            "抛光机", "粉碎机", "除湿机", "等等机器",
            "抛光机", "粉碎机", "除湿机", "等等机器",
            "抛光机", "粉碎机", "除湿机", "等等机器"};
    private List<String> deviceTypeList = new ArrayList<>();
    private TextSpinnerAdapter deviceTypeAdapter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_bind_device);
    }

    @Override
    public void initData() {
        initDeviceType();
        title.setText(getResources().getString(R.string.device_bind));
    }

    @Override
    public void initOperation() {
        back.setOnClickListener(this);
        deviceType.setOnClickListener(this);
        deviceName.setOnClickListener(this);
        deviceId.setOnClickListener(this);
    }

    private void initDeviceType() {
        for (int i = 0; i < deviceCache.length; i++) {
            deviceTypeList.add(deviceCache[i]);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.device_type:

                break;
            case R.id.device_name:

                break;
            case R.id.device_id:

                break;
        }
    }
}
