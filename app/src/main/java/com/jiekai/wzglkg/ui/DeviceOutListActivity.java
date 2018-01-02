package com.jiekai.wzglkg.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jiekai.wzglkg.R;
import com.jiekai.wzglkg.adapter.DeviceOutListAdapter;
import com.jiekai.wzglkg.config.SqlUrl;
import com.jiekai.wzglkg.entity.DeviceapplyEntity;
import com.jiekai.wzglkg.ui.base.MyBaseActivity;
import com.jiekai.wzglkg.utils.dbutils.DBManager;
import com.jiekai.wzglkg.utils.dbutils.DbCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by laowu on 2017/12/18.
 */

public class DeviceOutListActivity extends MyBaseActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener{
    private final static int START_OUT = 0;

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.menu)
    ImageView menu;
    @BindView(R.id.list)
    ListView list;

    private DeviceOutListAdapter adapter;
    private List<DeviceapplyEntity> dataList = new ArrayList<>();

    @Override
    public void initView() {
        setContentView(R.layout.activity_device_out_list);
    }

    @Override
    public void initData() {
        title.setText(getResources().getString(R.string.device_output));
        back.setOnClickListener(this);
    }

    @Override
    public void initOperation() {
        if (adapter == null) {
            adapter = new DeviceOutListAdapter(mActivity, dataList);
            View headerView = LayoutInflater.from(mActivity).inflate(R.layout.adapter_device_output_list, null);
            list.addHeaderView(headerView);
            list.setAdapter(adapter);
            list.setOnItemClickListener(this);
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
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GetShenHeList)
                .clazz(DeviceapplyEntity.class)
                .execut(new DbCallBack() {
                    @Override
                    public void onDbStart() {
                        showProgressDialog(getResources().getString(R.string.loading_data));
                    }

                    @Override
                    public void onError(String err) {
                        alert(err);
                        dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(List result) {
                        dismissProgressDialog();
                        if (result != null && result.size() != 0) {
                            dataList.clear();
                            dataList.addAll(result);
                            if (adapter != null) {
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DeviceapplyEntity entity = (DeviceapplyEntity) parent.getItemAtPosition(position);
        if (entity != null) {
//            DeviceOutputActivity.startForResult(DeviceOutListActivity.this, START_OUT,
//                    entity.getSBMC(), entity.getSBXH());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == START_OUT && resultCode == RESULT_OK) {
            getData();
        }
    }
}
