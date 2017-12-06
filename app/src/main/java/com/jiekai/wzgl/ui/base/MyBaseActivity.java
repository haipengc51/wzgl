package com.jiekai.wzgl.ui.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jiekai.wzgl.config.ShareConstants;
import com.jiekai.wzgl.entity.UserInfoEntity;
import com.jiekai.wzgl.utils.AnimationUtils;
import com.jiekai.wzgl.utils.JSONHelper;
import com.jiekai.wzgl.utils.StringUtils;

import butterknife.ButterKnife;

/**
 * Created by laowu on 2017/12/3.
 * Activity的基类
 */

public abstract class MyBaseActivity extends AppCompatActivity {
    public abstract void initView();
    public abstract void initData();
    public abstract void initOperation();

    public boolean isAnimation = true;
    private SharedPreferences sharedPreferences;
    public UserInfoEntity userData;
    public boolean isLogin = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        ButterKnife.bind(this);
        getLoginData();
        initData();
        initOperation();
    }

    private void getLoginData() {
        sharedPreferences = getSharedPreferences(ShareConstants.USERINFO, Context.MODE_PRIVATE);
        String userString = sharedPreferences.getString(ShareConstants.USERINFO, "");
        if (!StringUtils.isEmpty(userString)) {
            isLogin = true;
            userData = JSONHelper.fromJSONObject(userString, UserInfoEntity.class);
        }
    }

    private void clearLoginData() {
        sharedPreferences.edit().putString(ShareConstants.USERINFO, "").commit();
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
