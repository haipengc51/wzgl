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
import com.jiekai.wzgl.adapter.TextPopupAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by LaoWu on 2017/12/8.
 * 仿照spinner的弹窗 工具类
 */

public class SpinnerPopupWindowUtils {
    private PopupWindow popupWindow;
    private ListView popList;
    private TextView popTitle;
    private TextPopupAdapter textPopupAdapter;
    private List<String> popListData = new ArrayList<>();

    public SpinnerPopupWindowUtils(Context context) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.spinner_base_popup_window, null);
        popTitle = (TextView) view.findViewById(R.id.pop_title);
        popList = (ListView) view.findViewById(R.id.pop_list);
        if (textPopupAdapter == null) {
            textPopupAdapter = new TextPopupAdapter(context, popListData);
            popList.setAdapter(textPopupAdapter);
        }
        popupWindow = new PopupWindow(view);
        popupWindow.setWidth(WRAP_CONTENT);
        popupWindow.setHeight(WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.fade_in_popup);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public void showCenter(View parentView) {
        if (popupWindow != null && !popupWindow.isShowing()) {
            popupWindow.showAsDropDown(parentView);//, Gravity.BOTTOM, 0, parentView.getHeight());
        }
    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }

    /**
     * 设置item的点击监听事件
     * @param onItemClickLisen
     */
    public void setOnItemClickLisen(AdapterView.OnItemClickListener onItemClickLisen) {
        if (popList != null) {
            popList.setOnItemClickListener(onItemClickLisen);
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
    public void setPopListData(List<String> listData) {
        popListData.clear();
        popListData.addAll(listData);
        if (textPopupAdapter != null) {
            textPopupAdapter.notifyDataSetChanged();
        }
    }
}
