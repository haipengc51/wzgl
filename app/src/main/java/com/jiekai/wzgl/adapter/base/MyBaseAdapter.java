package com.jiekai.wzgl.adapter.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by laowu on 2017/12/7.
 */

public abstract class MyBaseAdapter extends BaseAdapter {
    private Context context;
    public List dataList;
    public LayoutInflater mInflater;

    public abstract View createCellView(ViewGroup parent);

    public abstract BusinessHolder createCellHolder(View cellView);

    public abstract View buildData(int position, View cellView, BusinessHolder viewHolder);

    public MyBaseAdapter(Context context, List dataList) {
        this.context = context;
        this.dataList = dataList;
        mInflater = LayoutInflater.from(context);
    }

    public void setDataList(List dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BusinessHolder businessHolder;
        if (convertView == null) {
            convertView = createCellView(parent);
            businessHolder = createCellHolder(convertView);
            convertView.setTag(businessHolder);
        } else {
            businessHolder = (BusinessHolder) convertView.getTag();
        }
        buildData(position, convertView,businessHolder);
        return convertView;
    }

    public class BusinessHolder {

    }
}
