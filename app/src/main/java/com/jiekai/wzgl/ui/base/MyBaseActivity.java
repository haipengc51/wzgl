package com.jiekai.wzgl.ui.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.jiekai.wzgl.utils.AnimationUtils;

/**
 * Created by laowu on 2017/12/3.
 * Activity的基类
 */

public class MyBaseActivity extends AppCompatActivity {
    private boolean isAnimation = true;

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (isAnimation) {
            AnimationUtils.setAnimationOfRight(this);
        }
    }
}
