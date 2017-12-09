package com.jiekai.wzgl.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.test.NFCBaseActivity;
import com.jiekai.wzgl.utils.SpinnerPopupWindowUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laowu on 2017/12/7.
 * 设备绑定界面
 */

public class BindDeviceActivity extends NFCBaseActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener {
    private static final int READ_DEVICE_NFC = 0;
    private static final int READ_PART_NFC = 1;

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
    @BindView(R.id.device_card)
    TextView deviceCard;
    @BindView(R.id.read_device_nfc)
    TextView readDeviceNfc;
    @BindView(R.id.read_part_nfc)
    TextView readPartNfc;
    @BindView(R.id.part_card)
    TextView partCard;

    private int readNfcType;

    private String[] deviceCache = new String[]{
//            "抛光机", "粉碎机", "除湿机", "等等机器",
//            "抛光机", "粉碎机", "除湿机", "等等机器",
//            "抛光机", "粉碎机", "除湿机", "等等机器",
//            "抛光机", "粉碎机", "除湿机", "等等机器",
//            "抛光机", "粉碎机", "除湿机", "等等机器",
            "抛光机", "粉碎机", "除湿机", "等等机器"
    };
    private String[] deviceNameCache = new String[]{
            "1", "2", "3", "4", "5", "6",
            "7", "8", "9", "0", "-", "=",
    };
    private String[] deviceIdCache = new String[]{
            "11", "22", "33", "44", "55", "66",
            "77", "88", "99", "00", "--", "==",
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
        readDeviceNfc.setOnClickListener(this);
        readPartNfc.setOnClickListener(this);

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
                for (int i = 0; i < deviceNameCache.length; i++) {
                    popListData.add(i, deviceNameCache[i]);
                }
                popupWindowUtils.setPopListData(popListData);
                popupWindowUtils.showCenter(v);
                break;
            case R.id.device_id:
                popupWindowUtils.setPopTitle(getResources().getString(R.string.device_id));
                popListData.clear();
                ;
                for (int i = 0; i < deviceIdCache.length; i++) {
                    popListData.add(i, deviceIdCache[i]);
                }
                popupWindowUtils.setPopListData(popListData);
                popupWindowUtils.showCenter(v);
                break;
            case R.id.read_device_nfc:
                //TODO 检查一下NFC是否启动
                readNfcType = READ_DEVICE_NFC;
                nfcEnable = true;
                alert(R.string.wait_read_nfc);
                break;
            case R.id.read_part_nfc:
                nfcEnable = true;
                readNfcType = READ_PART_NFC;
                alert(R.string.wait_read_nfc);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void getNfcData(String nfcString) {
        if (readNfcType == READ_DEVICE_NFC) {
            deviceCard.setText(nfcString);
            nfcEnable = false;
        } else if (readNfcType == READ_PART_NFC) {
            partCard.setText(nfcString);
            nfcEnable = false;
        }
    }
}
