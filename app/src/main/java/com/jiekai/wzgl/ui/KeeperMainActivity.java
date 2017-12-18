package com.jiekai.wzgl.ui;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.adapter.KeeperAdapter;
import com.jiekai.wzgl.entity.KeeperEntity;
import com.jiekai.wzgl.ui.base.MyBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by laowu on 2017/12/7.
 * 库管的主界面
 */

public class KeeperMainActivity extends MyBaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.grid_view)
    GridView gridView;

    private List<KeeperEntity> dataList = new ArrayList<KeeperEntity>(){};
    private KeeperAdapter adapter;

    @Override
    public void initView() {
        setContentView(R.layout.activity_keeper_main);
    }

    @Override
    public void initData() {
        dataList.add(new KeeperEntity(getResources().getString(R.string.device_bind), BindDeviceActivity.class));
        dataList.add(new KeeperEntity(getResources().getString(R.string.device_output), DeviceOutListActivity.class));
        dataList.add(new KeeperEntity(getResources().getString(R.string.device_bind), BindDeviceActivity.class));
        dataList.add(new KeeperEntity(getResources().getString(R.string.device_bind), BindDeviceActivity.class));
        dataList.add(new KeeperEntity(getResources().getString(R.string.device_bind), BindDeviceActivity.class));
        dataList.add(new KeeperEntity(getResources().getString(R.string.device_bind), BindDeviceActivity.class));
        dataList.add(new KeeperEntity(getResources().getString(R.string.device_bind), BindDeviceActivity.class));
        dataList.add(new KeeperEntity(getResources().getString(R.string.device_bind), BindDeviceActivity.class));
    }

    @Override
    public void initOperation() {
        if (adapter == null) {
            adapter = new KeeperAdapter(KeeperMainActivity.this, dataList);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        KeeperEntity keeperEntity = (KeeperEntity) parent.getItemAtPosition(position);
        if (keeperEntity != null) {
            startActivity(new Intent(KeeperMainActivity.this, keeperEntity.getActivity()));
        }
    }
}
