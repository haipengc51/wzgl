package com.jiekai.wzgl.dbutils;

/**
 * Created by laowu on 2017/11/29.
 */

public abstract class AsynInterface {
    /**
     * 异步执行的接口
     */
    public abstract void doExecutor(AsynCallBack asynCallBack);
}
