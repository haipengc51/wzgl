package com.jiekai.wzgl.utils.dbutils;


import com.jiekai.wzgl.config.Config;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by laowu on 2017/11/26.
 */

public class DbDeal extends AsynInterface{
    private Executor executor;
    private String sql;
    private Class mClass;

    private ResultSet resultSet;

    public DbDeal sql(String sql) {
        this.sql = sql;
        return this;
    }

    public DbDeal clazz(Class clazz) {
        this.mClass = clazz;
        return this;
    }

    public void execut(DbCallBack dbCallBack) {
        ExecutorManager.getInstance().execute(DbDeal.this, dbCallBack);
    }

    private void readDbDealProcess(AsynCallBack asynCallBack) {
        try {
            if (sql == null || sql.length() == 0) {
                asynCallBack.onError("sql命令为空");
                return;
            }
            if (mClass == null) {
                asynCallBack.onError("sql模型为空");
                return;
            }
            Class.forName(Config.DB_CLASS_NAME);
            Connection connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER_NAME, Config.DB_USER_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            List list = transformData(resultSet, mClass);
            asynCallBack.onSuccess(list);
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            asynCallBack.onError(e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            asynCallBack.onError(e.getMessage());
        }
    }

    @Override
    public void doExecutor(AsynCallBack asynCallBack) {
        readDbDealProcess(asynCallBack);
    }

    private <T> List<T> transformData(ResultSet resultSet, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                try {
                    T t = clazz.newInstance();
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        field.set(t, resultSet.getObject(field.getName()));
                    }
                    list.add(t);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return list;
    }
}
