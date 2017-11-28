package com.jiekai.wzgl.dbutils;

/**
 * Created by LaoWu on 2017/11/23.
 * 线程池的管理类
 * 线程池的创建
 */

public class ExecutorManager {
    private static ExecutorManager executorManager = null;
    private PlantFrom plantFrom;
    private DbDeal dbDeal;

    public ExecutorManager() {
        if (plantFrom == null) {
            plantFrom = PlantFrom.getInstance();
        }
        if (dbDeal == null) {
            dbDeal = DbDeal.getInstance();
        }
    }

    /**
     * 创建单例, 并初始化
     */
    public static ExecutorManager getInstance() {
        if (executorManager == null) {
            synchronized (ExecutorManager.class) {
                executorManager = new ExecutorManager();
            }
        }
        return executorManager;
    }

    public DbDeal dbDeal() {
        return dbDeal;
    }

    public void execute(DbCallBack callBack) {

    }
}
