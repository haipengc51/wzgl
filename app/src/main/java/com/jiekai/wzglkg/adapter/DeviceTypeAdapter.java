package com.jiekai.wzglkg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jiekai.wzglkg.R;
import com.jiekai.wzglkg.entity.DeviceTypeEntity;
import com.jiekai.wzglkg.utils.treeutils.Node;
import com.jiekai.wzglkg.utils.treeutils.TreeListViewAdapter;

import java.util.List;

/**
 * Created by laowu on 2017/12/11.
 * 设备类型的adapter，显示的界面是一个树形结构
 */

public class DeviceTypeAdapter extends TreeListViewAdapter<DeviceTypeEntity> {

    /**
     * @param mTree
     * @param context
     * @param datas
     * @param defaultExpandLevel 默认展开几级树
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public DeviceTypeAdapter(ListView mTree, Context context, List datas, int defaultExpandLevel) throws IllegalArgumentException, IllegalAccessException {
        super(mTree, context, datas, defaultExpandLevel);
    }

    @Override
    public View getConvertView(Node node, int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_popup_device_type, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        myViewHolder.deviceTypeName.setText(node.getTEXT());
        if (node.getIcon() == -1) {
            myViewHolder.arrow.setVisibility(View.INVISIBLE);
        } else {
            myViewHolder.arrow.setVisibility(View.VISIBLE);
            myViewHolder.arrow.setImageResource(node.getIcon());
        }
        return convertView;
    }

    private class MyViewHolder {
        private TextView deviceTypeName;
        private ImageView arrow;

        public MyViewHolder(View view) {
            deviceTypeName = (TextView) view.findViewById(R.id.device_type_name);
            arrow = (ImageView) view.findViewById(R.id.arrow);
        }
    }
}
