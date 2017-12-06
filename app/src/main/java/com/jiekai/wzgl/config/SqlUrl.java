package com.jiekai.wzgl.config;

/**
 * Created by LaoWu on 2017/12/6.
 */

public class SqlUrl {
    /**
     * 登录数据库操作
     */
    public static final String LoginSql = "SELECT * FROM userinfo where USERID = ? AND PASSWORD = ?";
}
