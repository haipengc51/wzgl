package com.jiekai.wzglkg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiekai.wzglkg.R;
import com.jiekai.wzglkg.adapter.base.MyBaseAdapter;

import java.util.List;

/**
 * Created by laowu on 2017/12/22.
 */

public class DeviceDetailAdapter extends MyBaseAdapter {
    public DeviceDetailAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    public View createCellView(ViewGroup parent) {
        return mInflater.inflate(R.layout.adapter_device_detail, null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        return new MyViewHolder(cellView);
    }

    @Override
    public View buildData(int position, View cellView, BusinessHolder viewHolder) {
        DeviceDetailAdapterEntity entity = (DeviceDetailAdapterEntity) dataList.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        String title = entity.getTitle();
        String content = entity.getContent();
        if (content.equals("点击查看详情")) {
            myViewHolder.content.setTextColor(context.getResources().getColor(R.color.main_color));
        } else {
            myViewHolder.content.setTextColor(context.getResources().getColor(R.color.text_content_color));
        }
        myViewHolder.title.setText(title == null ? "" : title);
        myViewHolder.content.setText(content == null ? "" : content);
        return null;
    }

    private class MyViewHolder extends BusinessHolder {
        private TextView title;
        private TextView content;

        public MyViewHolder(View view) {
            title = (TextView) view.findViewById(R.id.title);
            content = (TextView) view.findViewById(R.id.content);
        }
    }
}
