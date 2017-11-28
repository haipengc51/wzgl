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

public class DbDeal {
    private Executor executor;
    private String sql;

    private boolean isSuccess = false;
    private String resultMsg;
    private ResultSet resultSet;

    private void init() {
        executor = Executors.newCachedThreadPool();
    }

    public DbDeal sql(String sql) {
        this.sql = sql;
        return this;
    }

    public void execut(final DbCallBack dbCallBack) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                readDbDealProcess();
                if (isSuccess) {
                    dbCallBack.onResponse(resultSet);
                } else {
                    dbCallBack.onError(resultMsg);
                }
                ExecutorManager.getInstance().execute(dbCallBack);
            }
        });
    }

    private void readDbDealProcess() {
        isSuccess = false;
        try {
            if (sql == null || sql.length() == 0) {
                resultMsg = "sql命令为空";
                return;
            }
            Class.forName(Config.DB_CLASS_NAME);
            String url = "jdbc:oracle:thin:@" + Config.DB_IP + ":" + Config.DB_PORT
                    + ":" + Config.DB_NAME;
            Connection connection = DriverManager.getConnection(url, Config.DB_USER_NAME, Config.DB_USER_PASSWORD);
            String sql = "select * from MY_TABLE";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                isSuccess = true;
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            resultMsg = e.getMessage();
        } catch (SQLException e) {
            e.printStackTrace();
            resultMsg = e.getMessage();
        }
    }
}
