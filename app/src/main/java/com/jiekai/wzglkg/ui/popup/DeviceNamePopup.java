package com.jiekai.wzglkg.ui.popup;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.jiekai.wzglkg.adapter.DeviceNamePopupAdapter;
import com.jiekai.wzglkg.entity.DevicesortEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LaoWu on 2017/12/8.
 * 仿照spinner的弹窗 设备名称弹窗
 * 设备三级名称的弹窗类
 */

public class DeviceNamePopup extends BasePopup {
    private DeviceNamePopupAdapter popupAdapter;
    private List<DevicesortEntity> popListData = new ArrayList<>();
    public interface OnDeviceNameClick {
        void OnDeviceNameClick(DevicesortEntity devicesortEntity);
    }

    /**
     * popupwindow仿spinner效果，实现设备名称，和设备自编号的选择
     * @param context
     * @param textView
     * @param onDeviceNameClick
     */
    public DeviceNamePopup(Context context, final TextView textView,
                           final OnDeviceNameClick onDeviceNameClick ) {
        super(context);

        if (popupAdapter == null) {
            popupAdapter = new DeviceNamePopupAdapter(context, popListData);
            popList.setAdapter(popupAdapter);
            popList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DevicesortEntity entity = (DevicesortEntity) parent.getItemAtPosition(position);
                    if (entity != null) {
                        textView.setText(entity.getTEXT());
                        onDeviceNameClick.OnDeviceNameClick(entity);
                    }
                    dismiss();
                }
            });
        }
    }

    /**
     * 设置listView的数据
     * @param listData
     */
    public void setPopListData(List<DevicesortEntity> listData) {
        popListData.clear();
        popListData.addAll(listData);
        if (popupAdapter != null) {
            popupAdapter.notifyDataSetChanged();
        }
    }
}
