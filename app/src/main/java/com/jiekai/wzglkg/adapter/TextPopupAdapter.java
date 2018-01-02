package com.jiekai.wzglkg.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiekai.wzglkg.R;
import com.jiekai.wzglkg.adapter.base.MyBaseAdapter;

import java.util.List;

/**
 * Created by laowu on 2017/12/7.
 * 一个单独的文字的 spinner 适配器，可以当做公共类来使用
 */

public class TextPopupAdapter extends MyBaseAdapter {

    public TextPopupAdapter(Context context, List dataList) {
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
        String entity = (String) dataList.get(position);
        myViewHolder.content.setText(entity);
        return null;
    }

    class MyViewHolder extends BusinessHolder {
        private TextView content;

        public MyViewHolder(View view) {
            content = (TextView) view.findViewById(R.id.content);
        }
    }
}
