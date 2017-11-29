package com.jiekai.wzgl.dbutils;

import java.sql.ResultSet;

/**
 * Created by laowu on 2017/11/29.
 */

public abstract class AsynCallBack {
    public abstract void onError(String errorMsg);

    public abstract void onSuccess(ResultSet resultSet);
}
