package com.jiekai.wzgl.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.adapter.base.MyBaseAdapter;
import com.jiekai.wzgl.entity.PankuDataNumEntity;

import java.util.List;

/**
 * Created by laowu on 2017/12/24.
 */

public class PankuNumAdapter extends MyBaseAdapter {
    public PankuNumAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    public View createCellView(ViewGroup parent) {
        return mInflater.inflate(R.layout.adapter_panku_num, null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        return new MyViewHolder(cellView);
    }

    @Override
    public View buildData(int position, View cellView, BusinessHolder viewHolder) {
        PankuDataNumEntity entity = (PankuDataNumEntity) dataList.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.deviceLeibie.setText(entity.getLB());
        myViewHolder.deviceXinghao.setText(entity.getXH());
        myViewHolder.deviceGuige.setText(entity.getGG());
        myViewHolder.deviceNum.setText(entity.getNUM());
        return null;
    }

    private class MyViewHolder extends BusinessHolder {
        private TextView deviceLeibie;
        private TextView deviceXinghao;
        private TextView deviceGuige;
        private TextView deviceNum;

        public MyViewHolder(View view) {
            deviceLeibie = (TextView) view.findViewById(R.id.device_leibie);
            deviceXinghao = (TextView) view.findViewById(R.id.device_xinghao);
            deviceGuige = (TextView) view.findViewById(R.id.device_guige);
            deviceNum = (TextView) view.findViewById(R.id.device_num);
        }
    }
}
