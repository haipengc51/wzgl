package com.jiekai.wzgl.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.adapter.base.MyBaseAdapter;
import com.jiekai.wzgl.entity.PartListEntity;

import java.util.List;

/**
 * Created by LaoWu on 2017/12/15.
 * 配件列表标签的适配器
 */

public class PartListAdapter extends MyBaseAdapter {

    public PartListAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    public View createCellView(ViewGroup parent) {
        return mInflater.inflate(R.layout.header_depart_list, null);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        return new MyViewHolder(cellView);
    }

    @Override
    public View buildData(int position, View cellView, BusinessHolder viewHolder) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        PartListEntity entity = (PartListEntity) dataList.get(position);
        myViewHolder.num.setText(String.valueOf(position).toString());
        myViewHolder.partName.setText(entity.getMC());
        myViewHolder.partCardId.setText(entity.getIDDZMBH1());
        myViewHolder.partId.setText(entity.getBH());
        return null;
    }

    class MyViewHolder extends BusinessHolder {
        private TextView num;
        private TextView partName;
        private TextView partCardId;
        private TextView partId;

        public MyViewHolder(View view) {
            num = (TextView) view.findViewById(R.id.num);
            partName = (TextView) view.findViewById(R.id.part_name);
            partCardId = (TextView) view.findViewById(R.id.part_card_id);
            partId = (TextView) view.findViewById(R.id.part_id);
        }
    }
}
