package com.jiekai.wzglkg.utils.dbutils;

import android.content.Context;

import com.jiekai.wzglkg.R;
import com.jiekai.wzglkg.utils.NetWorkUtils;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by LaoWu on 2017/11/23.
 * 线程池的管理类
 * 线程池的创建
 */

public class DBManager {
    public static final int SELECT = 0;
    public static final int UPDATA = 1;
    public static final int INSERT = 2;
    public static final int DELET = 3;
    public static final int START_EVENT = 4;
    public static final int COMMIT = 5;
    public static final int ROLLBACK = 6;
    public static final int EVENT_SELECT = 7;
    public static final int EVENT_UPDATA = 8;
    public static final int EVENT_INSERT = 9;
    public static final int EVENT_DELET = 10;

    private static DBManager DBManager = null;
    private PlantFrom plantFrom;
    private Executor executor;

    public DBManager() {
        if (plantFrom == null) {
            plantFrom = PlantFrom.getInstance();
        }
        if (executor == null) {
            executor = Executors.newCachedThreadPool();
        }
    }

    public static DbDeal dbDeal(int dbType) {
        DbDeal dbDeal = new DbDeal();
        dbDeal.type(dbType);
        dbDeal.sql(null);
        dbDeal.params(null);
        dbDeal.clazz(null);
        return dbDeal;
    }

    /**
     * 新建一个DbDeal
     * @param dbType
     * @return
    public static DbDeal NewDbDeal(int dbType) {
        DbDeal dbDeal = new DbDeal();
        dbDeal.type(dbType);
        dbDeal.sql(null);
        dbDeal.params(null);
        dbDeal.clazz(null);
        return dbDeal;
    }
     */

    /**
     * 创建单例, 并初始化
     */
    public static DBManager getInstance() {
        if (DBManager == null) {
            synchronized (DBManager.class) {
                DBManager = new DBManager();
            }
        }
        return DBManager;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void execute(Context context, final AsynInterface asynInterface, final DbCallBack callBack) {
        if (!NetWorkUtils.isNetworkConnected(context)) {
            doFailed(callBack, context.getResources().getString(R.string.network_break));
            return;
        }
        executor.execute(new Runnable() {
            @Override
            public void run() {
                onDbStart(callBack);
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

    private void onDbStart(final DbCallBack dbCallBack) {
        plantFrom.execut(new Runnable() {
            @Override
            public void run() {
                dbCallBack.onDbStart();
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
