package com.jiekai.wzglkg.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jiekai.wzglkg.AppContext;
import com.jiekai.wzglkg.R;
import com.jiekai.wzglkg.adapter.PankuNumAdapter;
import com.jiekai.wzglkg.config.SqlUrl;
import com.jiekai.wzglkg.entity.PankuDataNumEntity;
import com.jiekai.wzglkg.ui.base.MyBaseActivity;
import com.jiekai.wzglkg.utils.dbutils.DBManager;
import com.jiekai.wzglkg.utils.dbutils.DbCallBack;
import com.jiekai.wzglkg.utils.localDbUtils.PanKuDataNumColumn;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
//        getData();
        getIntenatData();
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

    private void getIntenatData() {
        DBManager.dbDeal(DBManager.SELECT)
                .sql(SqlUrl.GET_PANKU_GROUP_LIST)
                .clazz(PankuDataNumEntity.class)
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
                        if (result != null && result.size() != 0) {
                            dataList.clear();
                            dataList.addAll(result);
                            adapter.notifyDataSetChanged();
                        } else {
                            alert(R.string.no_data);
                        }
                        dismissProgressDialog();
                    }
                });
    }
}
