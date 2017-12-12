package com.jiekai.wzgl.ui.popup;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.adapter.TextPopupAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by LaoWu on 2017/12/8.
 * 仿照spinner的弹窗 设备名称弹窗
 */

public class DeviceNamePopup extends BasePopup {
    private TextPopupAdapter textPopupAdapter;
    private List<String> popListData = new ArrayList<>();
    public interface OnDeviceNameClick {
        void OnDeviceNameClick(String deviceName);
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

        if (textPopupAdapter == null) {
            textPopupAdapter = new TextPopupAdapter(context, popListData);
            popList.setAdapter(textPopupAdapter);
            popList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String deviceName = (String) parent.getItemAtPosition(position);
                    if (deviceName != null && deviceName.length() != 0) {
                        textView.setText(deviceName);
                        onDeviceNameClick.OnDeviceNameClick(deviceName);
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
