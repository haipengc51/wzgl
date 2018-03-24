package com.jiekai.wzglkg.ui.popup;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.jiekai.wzglkg.R;
import com.jiekai.wzglkg.adapter.DeviceTypeAdapter;
import com.jiekai.wzglkg.config.SqlUrl;
import com.jiekai.wzglkg.entity.DeviceTypeEntity;
import com.jiekai.wzglkg.utils.dbutils.DBManager;
import com.jiekai.wzglkg.utils.dbutils.DbCallBack;
import com.jiekai.wzglkg.utils.treeutils.TreeListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LaoWu on 2017/12/8.
 * 设备类型的弹窗
 * 设备类型的树形弹窗类，后来放弃了
 */

public class DeviceTypePopup extends BasePopup{
    private TextView deviceType;
    private DeviceTypeAdapter deviceTypeAdapter;
    private List<DeviceTypeEntity> deviceTypeDatas = new ArrayList<>();

    public DeviceTypePopup(Context context, TextView deviceType,
                           TreeListViewAdapter.OnTreeNodeClickListener onTreeNodeClickListener) {
        super(context);
        this.deviceType = deviceType;
        this.deviceType.setOnClickListener(lisen);
        try {
            if (deviceTypeAdapter == null) {
                deviceTypeAdapter = new DeviceTypeAdapter(popList, context, deviceTypeDatas, 3);
                deviceTypeAdapter.setOnTreeNodeClickListener(onTreeNodeClickListener);
                popList.setAdapter(deviceTypeAdapter);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener lisen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == deviceType) {
                if (deviceTypeDatas == null || deviceTypeDatas.size() == 0) {
                    getDeviceType();
                } else {
                    showCenter(deviceType);
                }
            }
        }
    };

    /**
     * 设置listView的数据
     */
    public void setPopListData() {
        try {
            if (deviceTypeAdapter != null) {
                deviceTypeAdapter.setDatas(deviceTypeDatas);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void getDeviceType() {
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GetDeviceType)
                .clazz(DeviceTypeEntity.class)
                .execut(context, new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(context.getResources().getString(R.string.loding_device_type));
                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
//                        deviceTypePopupWindowUtilsl.setPopTitle(getResources().getString(R.string.device_type));
                        deviceTypeDatas.clear();
                        deviceTypeDatas.addAll(result);
                        setPopListData();
                        showCenter(deviceType);
                        dismissProgressDialog();
                    }
                });
    }
}
