package com.jiekai.wzgl.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jiekai.wzgl.AppContext;
import com.jiekai.wzgl.R;
import com.jiekai.wzgl.adapter.PankuNumAdapter;
import com.jiekai.wzgl.entity.PankuDataNumEntity;
import com.jiekai.wzgl.ui.base.MyBaseActivity;
import com.jiekai.wzgl.utils.localDbUtils.PanKuDataNumColumn;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by laowu on 2017/12/24.
 */

public class PanKuNumActivity extends MyBaseActivity implements View.OnClickListener{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.list_view)
    ListView listView;

    private PankuNumAdapter adapter;
    private List<PankuDataNumEntity> dataList = new ArrayList<>();

    @Override
    public void initView() {
        setContentView(R.layout.activity_panku_num);
    }

    @Override
    public void initData() {
        title.setText(getResources().getString(R.string.panku_detail));

        back.setOnClickListener(this);
    }

    @Override
    public void initOperation() {
        if (adapter == null) {
            adapter = new PankuNumAdapter(mActivity, dataList);
            View headerView = LayoutInflater.from(mActivity).inflate(R.layout.adapter_panku_num, null);
            listView.addHeaderView(headerView);
            listView.setAdapter(adapter);
        }

        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    private void getData() {
        String sql = "SELECT * FROM " + PanKuDataNumColumn.TABLE_NAME;
        List<PankuDataNumEntity> result = AppContext.dbHelper.selectAll(sql, PankuDataNumEntity.class, null);
        if (result != null && result.size() != 0) {
            dataList.clear();
            dataList.addAll(result);
            adapter.notifyDataSetChanged();
        } else {
            alert(R.string.no_data);
        }
    }
}