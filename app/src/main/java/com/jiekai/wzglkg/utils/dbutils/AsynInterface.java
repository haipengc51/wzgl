package com.jiekai.wzglkg.utils.dbutils;

/**
 * Created by laowu on 2017/11/29.
 */

public abstract class AsynInterface {
    /**
     * 异步执行数据库操作
     */
    public abstract void doExecutor(AsynCallBack asynCallBack);
}
