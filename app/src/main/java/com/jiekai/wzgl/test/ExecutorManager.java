package com.jiekai.wzgl.test;

/**
 * Created by LaoWu on 2017/11/23.
 * 线程池的方式访问数据库
 * 对数据库的方式尽量封装，方便后来的时候使用
 */

public class ExecutorManager {
    private static ExecutorManager executorManager = null;

    public ExecutorManager() {
    }

    /**
     * 创建单例, 并初始化
     */
    public void init() {
        if (executorManager == null) {
            synchronized (ExecutorManager.this) {
                executorManager = new ExecutorManager();
            }
        }
    }
}
