package com.jiekai.wzglkg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiekai.wzglkg.R;
import com.jiekai.wzglkg.adapter.base.MyBaseAdapter;
import com.jiekai.wzglkg.config.Config;
import com.jiekai.wzglkg.entity.DeviceUnCheckEntity;
import com.jiekai.wzglkg.entity.DevicestoreEntity;

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
            case Config.TYPE_STOR:    //入库，出库，维修
            {
                DevicestoreEntity devicestoreEntity = (DevicestoreEntity) item.getData();
                if (Config.LB_OUT.equals(devicestoreEntity.getLB())) {
                    myViewHolder.recordType.setText(context.getResources().getString(R.string.device_output));
                } else if (Config.LB_IN.equals(devicestoreEntity.getLB())) {
                    myViewHolder.recordType.setText(context.getResources().getString(R.string.device_input));
                } else if (Config.LB_WEIXIU.equals(devicestoreEntity.getLB())) {
                    myViewHolder.recordType.setText(context.getResources().getString(R.string.device_weixiu));
                } else if (Config.LB_DAXIU.equals(devicestoreEntity.getLB())) {
                    myViewHolder.recordType.setText(context.getResources().getString(R.string.device_daxiu));
                } else if (Config.LB_FANCHANG.equals(devicestoreEntity.getLB())) {
                    myViewHolder.recordType.setText(context.getResources().getString(R.string.device_fanchang));
                }
                myViewHolder.deviceId.setText(getDataIfNull(devicestoreEntity.getSBBH()));
                myViewHolder.jinghao.setText(getDataIfNull(devicestoreEntity.getJH()));
                String shyj = devicestoreEntity.getSHYJ();
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
