package com.jiekai.wzgl.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.adapter.base.MyBaseAdapter;
import com.jiekai.wzgl.entity.DeviceOutHistoryEntity;
import com.jiekai.wzgl.utils.StringUtils;

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
        DeviceOutHistoryEntity item = (DeviceOutHistoryEntity) dataList.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.deviceName.setText(item.getMC());
        myViewHolder.deviceId.setText(item.getSBBH());
        myViewHolder.outPeople.setText(item.getUSERNAME());
        myViewHolder.outTime.setText(String.valueOf(item.getCZSJ()));
        myViewHolder.lingyongdanwei.setText(StringUtils.isEmpty(item.getLYDW()) ? "": item.getLYDW());
        myViewHolder.jinghao.setText(item.getJH());
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
