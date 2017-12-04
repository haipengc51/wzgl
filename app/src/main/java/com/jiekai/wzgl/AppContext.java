package com.jiekai.wzgl;

import android.app.Application;

import com.jiekai.wzgl.config.Config;
import com.jiekai.wzgl.ftputils.FTPUtils;
import com.jiekai.wzgl.ftputils.FtpManager;

/**
 * Created by LaoWu on 2017/11/27.
 */

public class AppContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initDbFrame();
        initFTP();
    }

    /**
     * 初始化数据库框架
     */
    private void initDbFrame() {

    }

    /**
     * 初始化FTP上传
     */
    private void initFTP() {
        FtpManager.getInstance().initFTP(Config.IP, Config.FTP_PORT, Config.FTP_USER_NAME, Config.FTP_PASSWORD);
    }
}
