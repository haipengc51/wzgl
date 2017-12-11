package com.jiekai.wzgl.utils;

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
import com.jiekai.wzgl.adapter.DeviceTypeAdapter;
import com.jiekai.wzgl.adapter.TextPopupAdapter;
import com.jiekai.wzgl.entity.DeviceTypeEntity;
import com.jiekai.wzgl.utils.treeutils.TreeListViewAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by LaoWu on 2017/12/8.
 * 设备类型弹窗工具类（弹出的窗口是一个树形结构）
 */

public class DeviceTypePopupWindowUtils {
    private PopupWindow popupWindow;
    private ListView popList;
    private TextView popTitle;
    private DeviceTypeAdapter deviceTypeAdapter;

    public DeviceTypePopupWindowUtils(Context context) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.spinner_base_popup_window, null);
        popTitle = (TextView) view.findViewById(R.id.pop_title);
        popList = (ListView) view.findViewById(R.id.pop_list);

        popupWindow = new PopupWindow(view);
        popupWindow.setWidth(WRAP_CONTENT);
        popupWindow.setHeight(WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.fade_in_popup);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        try {
            if (deviceTypeAdapter == null) {
                List<DeviceTypeEntity> popListData = new ArrayList<>();
                deviceTypeAdapter = new DeviceTypeAdapter(popList, context, popListData, 3);
                popList.setAdapter(deviceTypeAdapter);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void showCenter(View parentView) {
        if (popupWindow != null && !popupWindow.isShowing()) {
            popupWindow.showAsDropDown(parentView);//, Gravity.BOTTOM, 0, parentView.getHeight());
        }
    }

    public void dismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    /**
     * 设置树的点击事件
     * @param treeNodeClickListener
     */
    public void setOnItemClickLisen(TreeListViewAdapter.OnTreeNodeClickListener treeNodeClickListener) {
        if (deviceTypeAdapter != null) {
            deviceTypeAdapter.setOnTreeNodeClickListener(treeNodeClickListener);
        }
    }

    public void setPopTitle(String title) {
        if (popTitle != null && title != null) {
            popTitle.setText(title);
        }
    }

    /**
     * 设置listView的数据
     * @param listData
     */
    public void setPopListData(List<DeviceTypeEntity> listData) {
        try {
            if (deviceTypeAdapter != null) {
                deviceTypeAdapter.setDatas(listData);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
