package com.jiekai.wzgl.utils.dbutils;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by LaoWu on 2017/11/23.
 * 线程池的管理类
 * 线程池的创建
 */

public class ExecutorManager {
    public static final int SELECT = 0;
    public static final int UPDATA = 1;
    public static final int INSERT = 2;
    public static final int DELET = 3;

    private static ExecutorManager executorManager = null;
    private PlantFrom plantFrom;
    private Executor executor;

    public ExecutorManager() {
        if (plantFrom == null) {
            plantFrom = PlantFrom.getInstance();
        }
        if (executor == null) {
            executor = Executors.newCachedThreadPool();
        }
    }

    public static DbDeal dbDeal(int dbType) {
        return new DbDeal(dbType);
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

    public Executor getExecutor() {
        return executor;
    }

    public void execute(final AsynInterface asynInterface, final DbCallBack callBack) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                asynInterface.doExecutor(new AsynCallBack() {
                    @Override
                    public void onError(String errorMsg) {
                        doFailed(callBack, errorMsg);
                    }

                    @Override
                    public void onSuccess(List result) {
                        doSuccess(callBack, result);
                    }
                });
            }
        });
    }

    private void doSuccess(final DbCallBack dbCallBack, final List result) {
        plantFrom.execut(new Runnable() {
            @Override
            public void run() {
                dbCallBack.onResponse(result);
            }
        });
    }

    private void doFailed(final DbCallBack dbCallBack, final String errorMsg) {
        plantFrom.execut(new Runnable() {
            @Override
            public void run() {
                dbCallBack.onError(errorMsg);
            }
        });
    }
}
