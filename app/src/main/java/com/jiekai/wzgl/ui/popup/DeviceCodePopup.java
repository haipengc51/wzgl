package com.jiekai.wzgl.ui.popup;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.jiekai.wzgl.adapter.TextPopupAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LaoWu on 2017/12/8.
 * 仿照spinner的弹窗 工具类
 */

public class DeviceCodePopup extends BasePopup {
    private TextPopupAdapter textPopupAdapter;
    private List<String> popListData = new ArrayList<>();

    public interface OnDeviceCodeClick {
        void OnDeviceCodeClick(String deviceCode);
    }

    /**
     * popupwindow仿spinner效果，实现设备名称，和设备自编号的选择
     * @param context
     * @param textView
     * @param onDeviceCodeClick
     */
    public DeviceCodePopup(Context context, final TextView textView,
                           final OnDeviceCodeClick onDeviceCodeClick ) {
        super(context);

        if (textPopupAdapter == null) {
            textPopupAdapter = new TextPopupAdapter(context, popListData);
            popList.setAdapter(textPopupAdapter);
            popList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String deviceCode = (String) parent.getItemAtPosition(position);
                    if (deviceCode != null && deviceCode.length() != 0) {
                        textView.setText(deviceCode);
                        onDeviceCodeClick.OnDeviceCodeClick(deviceCode);
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
    public void setPopListData(List<String> listData) {
        popListData.clear();
        popListData.addAll(listData);
        if (textPopupAdapter != null) {
            textPopupAdapter.notifyDataSetChanged();
        }
    }
}
