package com.jiekai.wzglkg.utils.dbutils;

import java.util.List;

/**
 * Created by laowu on 2017/11/29.
 */

public abstract class AsynCallBack {
    public abstract void onError(String errorMsg);

    public abstract void onSuccess(List result);
}
