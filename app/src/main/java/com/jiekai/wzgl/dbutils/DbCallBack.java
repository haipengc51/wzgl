package com.jiekai.wzgl.dbutils;

import java.util.Objects;

/**
 * Created by laowu on 2017/11/24.
 * 读取远程数据库的回调
 */

public abstract class DbCallBack {
    public abstract void onError(String err);

    public abstract void onResponse(Objects response);
}
