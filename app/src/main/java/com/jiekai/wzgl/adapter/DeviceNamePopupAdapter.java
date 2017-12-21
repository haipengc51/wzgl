package com.jiekai.wzgl.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.adapter.base.MyBaseAdapter;
import com.jiekai.wzgl.entity.DevicesortEntity;

import java.util.List;

/**
 * Created by laowu on 2017/12/7.
 * 一个单独的文字的 spinner 适配器，可以当做公共类来使用
 */

public class DeviceNamePopupAdapter extends MyBaseAdapter {

    public DeviceNamePopupAdapter(Context context, List dataList) {
        super(context, dataList);
    }


    @Override
    public View createCellView(ViewGroup parent) {
        return mInflater.inflate(R.layout.adapter_text_spinner, parent, false);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        return new MyViewHolder(cellView);
    }

    @Override
    public View buildData(int position, View cellView, BusinessHolder viewHolder) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        DevicesortEntity entity = (DevicesortEntity) dataList.get(position);
        myViewHolder.content.setText(entity.getTEXT());
        return null;
    }

    class MyViewHolder extends BusinessHolder {
        private TextView content;

        public MyViewHolder(View view) {
            content = (TextView) view.findViewById(R.id.content);
        }
    }
}
