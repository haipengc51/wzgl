package com.jiekai.wzgl.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jiekai.wzgl.utils.AnimationUtils;

import butterknife.ButterKnife;

/**
 * Created by laowu on 2017/12/3.
 * Activity的基类
 */

public abstract class MyBaseActivity extends AppCompatActivity {
    public abstract void initView();
    public abstract void initData();
    public abstract void initOperation();

    private boolean isAnimation = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        ButterKnife.bind(this);
        initData();
        initOperation();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (isAnimation) {
            AnimationUtils.setAnimationOfRight(this);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (isAnimation) {
            AnimationUtils.setAnimationOfRight(this);
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (isAnimation) {
            AnimationUtils.setAnimationOfRight(this);
        }
    }

    public void alert(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void alert(int strId) {
        Toast.makeText(this, strId, Toast.LENGTH_SHORT).show();
    }
}
