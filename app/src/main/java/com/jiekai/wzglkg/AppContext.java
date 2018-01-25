package com.jiekai.wzglkg;

import android.app.Application;

import com.jiekai.wzglkg.config.Config;
import com.jiekai.wzglkg.utils.PictureSelectUtils;
import com.jiekai.wzglkg.utils.dbutils.DbDeal;
import com.jiekai.wzglkg.utils.ftputils.FtpManager;
import com.jiekai.wzglkg.utils.localDbUtils.DBHelper;

/**
 * Created by LaoWu on 2017/11/27.
 */

public class AppContext extends Application {
    public static DBHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        initDbFrame();
        initFTP();
        dbHelper = DBHelper.getInstance(getApplicationContext());
        DbDeal.getInstance();   //初始化dbDeal
        PictureSelectUtils.getCompressFile();
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
