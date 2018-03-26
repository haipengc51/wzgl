package com.jiekai.wzglkg.ui;

import android.view.View;
import android.widget.TextView;

import com.jiekai.wzglkg.R;
import com.jiekai.wzglkg.ui.base.MyBaseActivity;

import butterknife.BindView;

/**
 * Created by laowu on 2017/12/24.
 */

public class LogOutActivity extends MyBaseActivity implements View.OnClickListener{
    @BindView(R.id.kickout_text)
    TextView kickoutText;
    @BindView(R.id.enter)
    TextView enter;
    @BindView(R.id.cancle)
    TextView cancle;

    @Override
    public void initView() {
        isAnimation = false;
        setContentView(R.layout.activit_logout);
    }

    @Override
    public void initData() {
        enter.setOnClickListener(this);
        cancle.setOnClickListener(this);
    }

    @Override
    public void initOperation() {

    }

    @Override
    public void cancleDbDeal() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enter:
                clearLoginData();
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.cancle:
                finish();
                break;
        }
    }
}
