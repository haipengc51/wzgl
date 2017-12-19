package com.jiekai.wzgl.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.adapter.base.MyBaseAdapter;
import com.jiekai.wzgl.config.Constants;
import com.jiekai.wzgl.entity.DeviceapplyEntity;

import java.util.List;

/**
 * Created by laowu on 2017/12/18.
 */

public class DeviceOutListAdapter extends MyBaseAdapter {

    public DeviceOutListAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    public View createCellView(ViewGroup parent) {
        return mInflater.inflate(R.layout.adapter_device_output_list, null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        return new MyViewHolder(cellView);
    }

    @Override
    public View buildData(int position, View cellView, BusinessHolder viewHolder) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        DeviceapplyEntity entity = (DeviceapplyEntity) dataList.get(position);
        myViewHolder.xuhao.setText(entity.getSQID());
        myViewHolder.deviceName.setText(entity.getSBMC());
        myViewHolder.shebeixinghao.setText(entity.getSBXH());
        myViewHolder.lingyongdanwei.setText(entity.getLYDW());
        myViewHolder.shiyongjinghao.setText(entity.getSYJH());
        if (entity.getSQZT().equals(Constants.TONG_YI)) {
            myViewHolder.shenqingzhuangtai.setText(Constants.tongyi);
        }
        return null;
    }

    class MyViewHolder extends BusinessHolder {
        private TextView xuhao;
        private TextView deviceName;
        private TextView shebeixinghao;
        private TextView lingyongdanwei;
        private TextView shiyongjinghao;
        private TextView shenqingzhuangtai;

        public MyViewHolder(View view) {
            xuhao = (TextView) view.findViewById(R.id.xuhao);
            deviceName = (TextView) view.findViewById(R.id.device_name);
            shebeixinghao = (TextView) view.findViewById(R.id.shebeixinghao);
            lingyongdanwei = (TextView) view.findViewById(R.id.lingyongdanwei);
            shiyongjinghao = (TextView) view.findViewById(R.id.shiyongjinghao);
            shenqingzhuangtai = (TextView) view.findViewById(R.id.shenqingzhuangtai);
        }
    }
}
