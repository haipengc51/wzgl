package com.jiekai.wzgl;

import android.app.Application;

/**
 * Created by LaoWu on 2017/11/27.
 */

public class AppContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initDbFrame();
    }

    /**
     * 初始化数据库框架
     */
    private void initDbFrame() {

    }
}
