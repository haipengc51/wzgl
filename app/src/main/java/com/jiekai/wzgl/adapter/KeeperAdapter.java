package com.jiekai.wzgl.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.adapter.base.MyBaseAdapter;
import com.jiekai.wzgl.entity.KeeperEntity;
import com.jiekai.wzgl.utils.CommonUtils;

import java.util.List;

/**
 * Created by laowu on 2017/12/7.
 * 库管功能列表页
 */

public class KeeperAdapter extends MyBaseAdapter {
    private int screenHightPx = 0;  //屏幕高度

    public KeeperAdapter(Context context, List dataList) {
        super(context, dataList);
        screenHightPx = CommonUtils.getScreentHeight(context) -
                        CommonUtils.getStatusBarHeight(context);
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
        private LinearLayout linear;

        public MyViewHolder(View view) {
            linear = (LinearLayout) view.findViewById(R.id.linear);
            name = (TextView) view.findViewById(R.id.name);
            int count = getCount();
            ViewGroup.LayoutParams layoutParams = linear.getLayoutParams();
            layoutParams.height = screenHightPx / (count % 2 == 0 ? count / 2 : count / 2 + 1);
            linear.setLayoutParams(layoutParams);
        }
    }
}
