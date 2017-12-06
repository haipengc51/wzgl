package com.jiekai.wzgl.ui;

import android.content.Intent;
import android.os.Handler;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.ui.base.MyBaseActivity;

/**
 * Created by laowu on 2017/12/5.
 * 欢迎页面
 */

public class WelcomActivity extends MyBaseActivity {
    @Override
    public void initView() {
        setContentView(R.layout.activity_welcom);
        isAnimation = false;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initOperation() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomActivity.this, LoginActivity.class));
                finish();
            }
        }, 2000);
    }
}
