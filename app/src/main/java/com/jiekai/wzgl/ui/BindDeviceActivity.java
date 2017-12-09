package com.jiekai.wzgl.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.ui.base.MyBaseActivity;
import com.jiekai.wzgl.utils.SpinnerPopupWindowUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
    private String[] deviceNameCache = new String[] {
            "1", "2", "3", "4","5", "6",
            "7", "8","9", "0","-", "=",
    };
    private String[] deviceIdCache = new String[] {
            "11", "22", "33", "44","55", "66",
            "77", "88","99", "00","--", "==",
    };
    private SpinnerPopupWindowUtils popupWindowUtils;
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
        popupWindowUtils = new SpinnerPopupWindowUtils(this);
        popupWindowUtils.setOnItemClickLisen(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.device_type:
                popupWindowUtils.setPopTitle(getResources().getString(R.string.device_type));
                popListData.clear();
                for (int i = 0; i < deviceCache.length; i++) {
                    popListData.add(i, deviceCache[i]);
                }
                popupWindowUtils.setPopListData(popListData);
                popupWindowUtils.showCenter(v);
                break;
            case R.id.device_name:
                popupWindowUtils.setPopTitle(getResources().getString(R.string.device_name));
                popListData.clear();
                for (int i=0; i<deviceNameCache.length; i++) {
                    popListData.add(i, deviceNameCache[i]);
                }
                popupWindowUtils.setPopListData(popListData);
                popupWindowUtils.showCenter(v);
                break;
            case R.id.device_id:
                popupWindowUtils.setPopTitle(getResources().getString(R.string.device_id));
                popListData.clear();;
                for (int i=0; i<deviceIdCache.length; i++) {
                    popListData.add(i, deviceIdCache[i]);
                }
                popupWindowUtils.setPopListData(popListData);
                popupWindowUtils.showCenter(v);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
