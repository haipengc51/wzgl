package com.jiekai.wzgl.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.ui.base.MyBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

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
