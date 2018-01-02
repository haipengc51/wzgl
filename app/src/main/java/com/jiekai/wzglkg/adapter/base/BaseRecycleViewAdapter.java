package com.jiekai.wzglkg.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by laowu on 2017/9/19.
 */

public abstract class BaseRecycleViewAdapter extends RecyclerView.Adapter<ViewHolderHelper>{
    private static final int HEADER_TYPE = 0;
    private static final int FOOT_TYPE = 1;
    private static final int NORMAL_TYPE = 2;
    private int dataListSize = 0;

    public List dataList;
    public Context context;
    public LayoutInflater mInflater;

    private View headerView;
    private View footView;

    public abstract void onMyBindView(ViewHolderHelper holder, int position);
    public abstract ViewHolderHelper onMyCreateViewHolder(ViewGroup parent, int viewType);

    public BaseRecycleViewAdapter(Context context, List dataList) {
        this.context = context;
        this.dataList = dataList;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolderHelper onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType)  {
            case HEADER_TYPE:
                return new ViewHolderHelper(headerView);
            case FOOT_TYPE:
                return new ViewHolderHelper(footView);
            case NORMAL_TYPE:
                return onMyCreateViewHolder(parent, viewType);
            default:
                return onMyCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolderHelper holder, int position) {
        if (getItemViewType(position) != NORMAL_TYPE) {
            return;
        }
        if (headerView != null) {
            position--;
        }
        onMyBindView(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        if (headerView != null && position == 0) {
            return HEADER_TYPE;
        }
        if (footView != null && position == dataList.size()+1) {
            return FOOT_TYPE;
        }
        return NORMAL_TYPE;
    }

    @Override
    public int getItemCount() {
        dataListSize = dataList == null ? 0 : dataList.size();
        if (headerView != null && dataListSize != 0) {
            dataListSize++;
        }
        if (footView != null && dataListSize != 0) {
            dataListSize++;
        }
        return dataListSize;
    }

    public void addHeaderView(View view) {
        headerView = view;
        notifyItemChanged(0);
    }

    public void addFootView(View view) {
        this.footView = view;
        notifyItemChanged(dataList.size());
    }

    /**
     * 根据列表中的数据的位置得到列表的问题纸
     * 就是加上headerView的数量
     * @param position
     * @return
     */
    public int getListDataPosition(int position) {
        if (headerView != null) {
            return position + 1;
        } else {
            return position;
        }
    }
}
