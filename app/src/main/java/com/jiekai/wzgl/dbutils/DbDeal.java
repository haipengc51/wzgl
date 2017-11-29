package com.jiekai.wzgl.dbutils;


import com.jiekai.wzgl.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by laowu on 2017/11/26.
 */

public class DbDeal extends AsynInterface{
    private Executor executor;
    private String sql;

    private ResultSet resultSet;

    public DbDeal sql(String sql) {
        this.sql = sql;
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
            Class.forName(Config.DB_CLASS_NAME);
            Connection connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER_NAME, Config.DB_USER_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            asynCallBack.onSuccess(resultSet);
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
}
