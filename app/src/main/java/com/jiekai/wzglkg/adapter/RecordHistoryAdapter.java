package com.jiekai.wzglkg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiekai.wzglkg.R;
import com.jiekai.wzglkg.adapter.base.MyBaseAdapter;
import com.jiekai.wzglkg.config.Config;
import com.jiekai.wzglkg.entity.DeviceUnCheckEntity;

import java.util.List;

import static com.jiekai.wzglkg.utils.CommonUtils.getDataIfNull;


/**
 * Created by LaoWu on 2018/1/5.
 */

public class RecordHistoryAdapter extends MyBaseAdapter {

    public RecordHistoryAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    public View createCellView(ViewGroup parent) {
        return mInflater.inflate(R.layout.adapter_record_history, parent, false);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        return new MyViewHolder(cellView);
    }

    @Override
    public View buildData(int position, View cellView, BusinessHolder viewHolder) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        DeviceUnCheckEntity item = (DeviceUnCheckEntity) dataList.get(position);
        switch (item.getType()) {
            case Config.TYPE_JL:    //记录
            {
                DevicelogEntity devicelogEntity = (DevicelogEntity) item.getData();
                myViewHolder.recordType.setText(getDataIfNull(devicelogEntity.getJLZLMC()));
                myViewHolder.deviceId.setText(getDataIfNull(devicelogEntity.getSBBH()));
                myViewHolder.duihao.setText(getDataIfNull(devicelogEntity.getDH()));
                myViewHolder.jinghao.setText(getDataIfNull(devicelogEntity.getJH()));
                String shyj = devicelogEntity.getSHYJ();
                if ("1".equals(shyj)) {
                    myViewHolder.checkResult.setText("通过");
                } else if ("0".equals(shyj)) {
                    myViewHolder.checkResult.setText("未通过");
                } else {
                    myViewHolder.checkResult.setText("待审核");
                }
            }
                break;
            case Config.TYPE_MOVE:      //转场
            {
                DevicemoveEntity devicemoveEntity = (DevicemoveEntity) item.getData();
                myViewHolder.recordType.setText(getDataIfNull(item.getJLZL()));
                myViewHolder.deviceId.setText(getDataIfNull(devicemoveEntity.getSBBH()));
                myViewHolder.duihao.setText(getDataIfNull(devicemoveEntity.getDH()));
                myViewHolder.jinghao.setText(getDataIfNull(devicemoveEntity.getJH()));
                String shyj = devicemoveEntity.getSHYJ();
                if ("1".equals(shyj)) {
                    myViewHolder.checkResult.setText("通过");
                } else if ("0".equals(shyj)) {
                    myViewHolder.checkResult.setText("未通过");
                } else {
                    myViewHolder.checkResult.setText("待审核");
                }
            }
                break;
            case Config.TYPE_INSPECTION:        //巡检
            {
                DeviceInspectionEntity devicemoveEntity = (DeviceInspectionEntity) item.getData();
                myViewHolder.recordType.setText(getDataIfNull(item.getJLZL()));
                myViewHolder.deviceId.setText(getDataIfNull(devicemoveEntity.getSBBH()));
//                myViewHolder.duihao.setText(getDataIfNull(devicemoveEntity.getDH()));
//                myViewHolder.jinghao.setText(getDataIfNull(devicemoveEntity.getJH()));
                String shyj = devicemoveEntity.getSHYJ();
                if ("1".equals(shyj)) {
                    myViewHolder.checkResult.setText("通过");
                } else if ("0".equals(shyj)) {
                    myViewHolder.checkResult.setText("未通过");
                } else {
                    myViewHolder.checkResult.setText("待审核");
                }
            }
                break;
        }
        return null;
    }

    private class MyViewHolder extends BusinessHolder {
        private TextView recordType;
        private TextView deviceId;
        private TextView duihao;
        private TextView jinghao;
        private TextView checkResult;

        public MyViewHolder(View view) {
            recordType = (TextView) view.findViewById(R.id.record_type);
            deviceId = (TextView) view.findViewById(R.id.device_id);
            duihao = (TextView) view.findViewById(R.id.duihao);
            jinghao = (TextView) view.findViewById(R.id.jinghao);
            checkResult = (TextView) view.findViewById(R.id.check_result);
        }
    }
}
