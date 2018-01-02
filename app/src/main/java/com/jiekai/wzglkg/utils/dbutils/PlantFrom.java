package com.jiekai.wzglkg.utils.dbutils;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by laowu on 2017/11/24.
 */

public class PlantFrom {
    private static PlantFrom plantFrom = null;

    public static PlantFrom getInstance() {
        if (plantFrom == null) {
            synchronized (PlantFrom.class) {
                try {
                    Class.forName("android.os.Build");
                    if (Build.VERSION.SDK_INT != 0) {
                        plantFrom = new MainPlantForm();
                    } else {
                        plantFrom = new PlantFrom();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return plantFrom;
    }

    public Executor defualtCallBackExecutor() {
        return Executors.newCachedThreadPool();
    }

    public void execut(Runnable runnable) {
        defualtCallBackExecutor().execute(runnable);
    }

    static class MainPlantForm extends PlantFrom {

        @Override
        public Executor defualtCallBackExecutor() {
            return new MainThreadExecutor();
        }

        class MainThreadExecutor implements Executor {
            Handler handler = new Handler(Looper.getMainLooper());

            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        }
    }
}
