package com.jiekai.wzgl.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.adapter.base.MyBaseAdapter;

import java.util.List;

/**
 * Created by laowu on 2017/12/22.
 */

public class DeviceOutputHistoryAdapter extends MyBaseAdapter {

    public DeviceOutputHistoryAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    public View createCellView(ViewGroup parent) {
        return mInflater.inflate(R.layout.adapter_device_output_history_list, null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        return null;
    }

    @Override
    public View buildData(int position, View cellView, BusinessHolder viewHolder) {
        return null;
    }

    class MyViewHolder extends BusinessHolder {
        public MyViewHolder(View view) {

        }
    }
}
