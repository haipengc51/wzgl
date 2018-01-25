package com.jiekai.wzglkg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiekai.wzglkg.R;
import com.jiekai.wzglkg.adapter.base.MyBaseAdapter;
import com.jiekai.wzglkg.entity.DevicestoreEntity;
import com.jiekai.wzglkg.utils.CommonUtils;
import com.jiekai.wzglkg.utils.TimeUtils;

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
        return new MyViewHolder(cellView);
    }

    @Override
    public View buildData(int position, View cellView, BusinessHolder viewHolder) {
        DevicestoreEntity item = (DevicestoreEntity) dataList.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
//        myViewHolder.deviceName.setText(CommonUtils.getDataIfNull(item.getMC()));
        myViewHolder.deviceId.setText(CommonUtils.getDataIfNull(item.getSBBH()));
        myViewHolder.outPeople.setText(CommonUtils.getDataIfNull(item.getCzrname()));
        myViewHolder.outTime.setText(TimeUtils.dateToStringYYYYmmdd(item.getCZSJ()));
        myViewHolder.lingyongdanwei.setText(CommonUtils.getDataIfNull(item.getLYDW()));
        myViewHolder.jinghao.setText(CommonUtils.getDataIfNull(item.getJH()));
        return null;
    }

    class MyViewHolder extends BusinessHolder {
        private TextView deviceName;
        private TextView deviceId;
        private TextView outPeople;
        private TextView outTime;
        private TextView lingyongdanwei;
        private TextView jinghao;

        public MyViewHolder(View view) {
            deviceName = (TextView) view.findViewById(R.id.device_name);
            deviceId = (TextView) view.findViewById(R.id.device_id);
            outPeople = (TextView) view.findViewById(R.id.out_people);
            outTime = (TextView) view.findViewById(R.id.out_time);
            lingyongdanwei = (TextView) view.findViewById(R.id.lydw);
            jinghao = (TextView) view.findViewById(R.id.jinghao);
        }
    }
}
