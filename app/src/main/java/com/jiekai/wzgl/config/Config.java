package com.jiekai.wzgl.config;

import android.os.Environment;

/**
 * Created by LaoWu on 2017/11/19.
 * 程序的配置文件都这道这个里面
 */

public class Config {
    public static final String DB_CLASS_NAME = "com.mysql.jdbc.Driver";
    public static final String IP = "114.115.171.225";
    public static final String DB_URL = "jdbc:mysql://" + IP + ":3306/wzgl";   //mysql数据库url
    public static final String DB_USER_NAME = "root";  //用户名
    public static final String DB_USER_PASSWORD = "admin2017@";
    public static final int FTP_PORT = 21;
    public static final String FTP_USER_NAME = "FtpTest";
    public static final String FTP_PASSWORD = "haipengc51~";
    public static final String FTP_PATH_HANDLER = "/View/AppImage";
    public static final String BINDIMAGE_PATH = FTP_PATH_HANDLER + "/bind/";
    public static final String PICTURE_COMPRESS_PATH = Environment.getExternalStorageDirectory().toString() + "/wzgl/imag/compress";
}
