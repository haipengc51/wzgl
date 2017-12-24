package com.jiekai.wzgl.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.adapter.base.BaseRecycleViewAdapter;
import com.jiekai.wzgl.adapter.base.ViewHolderHelper;
import com.jiekai.wzgl.entity.PankuDataListEntity;

import java.util.List;

/**
 * Created by laowu on 2017/12/24.
 */

public class PankuDataListAdapter extends BaseRecycleViewAdapter{

    public PankuDataListAdapter(Context context, List dataList) {
        super(context, dataList);
    }

    @Override
    public void onMyBindView(ViewHolderHelper holder, int position) {
        PankuDataListEntity dataListEntity = (PankuDataListEntity) dataList.get(position);
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.deviceName.setText(dataListEntity.getMC());
        viewHolder.deviceId.setText(dataListEntity.getBH());
        viewHolder.deviceLeibie.setText(dataListEntity.getLB());
        viewHolder.deviceXinghao.setText(dataListEntity.getXH());
        viewHolder.deviceGuige.setText(dataListEntity.getGG());
    }

    @Override
    public ViewHolderHelper onMyCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.adapter_panu_data_list, parent, false));
    }

    public void addItem(RecyclerView recyclerView, PankuDataListEntity pankuDataListEntity) {
        dataList.add(pankuDataListEntity);
        notifyItemChanged(getItemCount() - 1);
        recyclerView.scrollToPosition(getItemCount() - 1);
    }

    public void clearData() {
        dataList.clear();
        notifyDataSetChanged();
    }

    class MyViewHolder extends ViewHolderHelper {
        private TextView deviceName;
        private TextView deviceId;
        private TextView deviceLeibie;
        private TextView deviceXinghao;
        private TextView deviceGuige;

        public MyViewHolder(View itemView) {
            super(itemView);
            deviceName = (TextView) itemView.findViewById(R.id.device_name);
            deviceId = (TextView) itemView.findViewById(R.id.device_id);
            deviceLeibie = (TextView) itemView.findViewById(R.id.device_leibie);
            deviceXinghao = (TextView) itemView.findViewById(R.id.device_xinghao);
            deviceGuige = (TextView) itemView.findViewById(R.id.device_guige);
        }
    }
}
