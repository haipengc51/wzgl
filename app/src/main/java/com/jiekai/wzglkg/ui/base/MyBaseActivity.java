package com.jiekai.wzglkg.ui.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.widget.Toast;

import com.jiekai.wzglkg.R;
import com.jiekai.wzglkg.config.ShareConstants;
import com.jiekai.wzglkg.entity.UserInfoEntity;
import com.jiekai.wzglkg.utils.AnimationUtils;
import com.jiekai.wzglkg.utils.JSONHelper;
import com.jiekai.wzglkg.utils.StringUtils;

import butterknife.ButterKnife;

/**
 * Created by laowu on 2017/12/3.
 * Activity的基类
 */

public abstract class MyBaseActivity extends Activity {
    public abstract void initView();
    public abstract void initData();
    public abstract void initOperation();
    public abstract void progressDialogCancleLisen();

    public boolean isAnimation = true;
    private SharedPreferences sharedPreferences;
    public UserInfoEntity userData;
    public boolean isLogin = false;
    public Activity mActivity;
    public Context mContext;

    private ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        mActivity = this;
        mContext = getApplicationContext();
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

    public void clearLoginData() {
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
            AnimationUtils.setAnimationOfLeft(this);
        }
    }

    public void showProgressDialog(String msg) {
        dismissProgressDialog();
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(getResources().getString(R.string.please_wait));
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    progressDialogCancleLisen();
                    return false;
                }
            });
        }
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void alert(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void alert(int strId) {
        Toast.makeText(this, strId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
    }
}
