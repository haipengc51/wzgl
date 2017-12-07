package com.jiekai.wzgl.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.adapter.base.MyBaseAdapter;
import com.jiekai.wzgl.entity.KeeperEntity;
import com.jiekai.wzgl.ui.base.MyBaseActivity;

import java.util.List;

/**
 * Created by laowu on 2017/12/7.
 */

public class KeeperAdapter extends MyBaseAdapter {
    public KeeperAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    public View createCellView(ViewGroup parent) {
        return mInflater.inflate(R.layout.adapter_keeper, parent, false);
    }

    @Override
    public BusinessHolder createCellHolder(View cellView) {
        return new MyViewHolder(cellView);
    }

    @Override
    public View buildData(int position, View cellView, BusinessHolder viewHolder) {
        ((MyViewHolder) viewHolder).name.setText(((KeeperEntity) dataList.get(position)).getName());
        return null;
    }

    class MyViewHolder extends BusinessHolder {
        private TextView name;

        public MyViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.name);
        }
    }
}
