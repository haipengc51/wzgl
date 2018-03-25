package com.jiekai.wzglkg.utils.dbutils;


import android.content.Context;
import android.widget.Toast;

import com.jiekai.wzglkg.config.Config;
import com.jiekai.wzglkg.utils.NetWorkUtils;

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

public class DbDeal extends AsynInterface {
    private static DbDeal mDbDeal = null;
    private Connection connection = null;
    private PreparedStatement preparedStatement;
    private Executor executor;
    private String sql;
    private Object[] params;
    private int dbType;
    private Class mClass;

    public static DbDeal getInstance() {
        if (mDbDeal == null) {
            mDbDeal = new DbDeal();
        }
        return mDbDeal;
    }

    public DbDeal sql(String sql) {
        this.sql = sql;
        return this;
    }

    public DbDeal clazz(Class clazz) {
        this.mClass = clazz;
        return this;
    }

    public DbDeal params(Object[] params) {
        this.params = params;
        return this;
    }

    public DbDeal type(int dbType) {
        this.dbType = dbType;
        return this;
    }

    public DbDeal reset(int dbType) {
        this.dbType = dbType;
        this.sql = null;
        this.params = null;
        this.mClass = null;
        return this;
    }

    public void execut(Context context, DbCallBack dbCallBack) {
        if (!NetWorkUtils.isNetworkConnected(context)) {
            Toast.makeText(context, "没有网络连接，请打开网络连接", Toast.LENGTH_SHORT).show();
            return;
        }
        DBManager.getInstance().execute(DbDeal.this, dbCallBack);
    }

    @Override
    public void doExecutor(AsynCallBack asynCallBack) {
        if (dbType == DBManager.SELECT || dbType == DBManager.EVENT_SELECT) {
            readDbDealProcess(asynCallBack);
        } else if (dbType == DBManager.INSERT || dbType == DBManager.UPDATA || dbType == DBManager.DELET
                || dbType == DBManager.EVENT_INSERT || dbType == DBManager.EVENT_UPDATA || dbType == DBManager.EVENT_DELET) {
            readBdUpdata(asynCallBack);
        } else if (dbType == DBManager.START_EVENT) {   //开启事务
            startEvent(asynCallBack);
        } else if (dbType == DBManager.COMMIT) {    //提交事务
            commitEvent(asynCallBack);
        } else if (dbType == DBManager.ROLLBACK) {      //回滚事务
            rollbackEvent(asynCallBack);
        }
    }

    /**
     * 数据库连接
     * @return
     */
    private boolean initConnection() {
        try {
            Class.forName(Config.DB_CLASS_NAME);
            connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER_NAME, Config.DB_USER_PASSWORD);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
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
            if (connection == null || connection.isClosed()) {
                if (dbType == DBManager.EVENT_SELECT) {
                    asynCallBack.onError("数据库连接失败");
                    return;
                } else if (!initConnection()) {
                    asynCallBack.onError("数据库连接失败");
                    return;
                }
            }
            preparedStatement = connection.prepareStatement(sql);
            if (params != null && params.length != 0) {
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject(i + 1, params[i]);
                }
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            List list = transformData(resultSet, mClass);
            resultSet.close();
            preparedStatement.close();
            if (dbType != DBManager.EVENT_SELECT) {
                connection.close();
            }
            asynCallBack.onSuccess(list);
        } catch (SQLException e) {
            try {
                if (dbType != DBManager.EVENT_SELECT) {
                    connection.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            asynCallBack.onError(e.getMessage());
        }
    }

    private void readBdUpdata(AsynCallBack asynCallBack) {
        try {
            if (sql == null || sql.length() == 0) {
                asynCallBack.onError("sql命令为空");
                return;
            }
                if (connection == null || connection.isClosed()) {
                    if (dbType == DBManager.EVENT_INSERT ||
                            dbType == DBManager.EVENT_UPDATA ||
                            dbType == DBManager.EVENT_DELET) {
                        asynCallBack.onError("数据库连接失败");
                        return;
                    } else if (!initConnection()) {
                        asynCallBack.onError("数据库连接失败");
                        return;
                    }
                }
            preparedStatement = connection.prepareStatement(sql);
            if (params != null && params.length != 0) {
                for (int i = 0; i < params.length; i++) {
                    preparedStatement.setObject(i + 1, params[i]);
                }
            }
            int result = preparedStatement.executeUpdate();
//            if (result > 0) {
//                asynCallBack.onSuccess(null);
//            } else {
//                asynCallBack.onError("数据库操作失败");
//            }
            preparedStatement.close();
            if (!(dbType == DBManager.EVENT_INSERT ||
                    dbType == DBManager.EVENT_UPDATA ||
                    dbType == DBManager.EVENT_DELET)) {
                connection.close();
            }
            asynCallBack.onSuccess(null);
        } catch (SQLException e) {
            try {
                if (!(dbType == DBManager.EVENT_INSERT ||
                        dbType == DBManager.EVENT_UPDATA ||
                        dbType == DBManager.EVENT_DELET)) {
                    connection.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            asynCallBack.onError(e.getMessage());
        }
    }

    private void startEvent(AsynCallBack asynCallBack) {
        try {
            if (connection == null || connection.isClosed()) {
                if (!initConnection()) {
                    asynCallBack.onError("数据库连接失败");
                    return;
                }
            }
            connection.setAutoCommit(false);
            asynCallBack.onSuccess(null);
        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            asynCallBack.onError(e.getMessage());
        }
    }

    private void commitEvent(AsynCallBack asynCallBack) {
        try {
            if (connection == null || connection.isClosed()) {
                asynCallBack.onError("数据库连接失败");
                return;
            }
            preparedStatement = connection.prepareStatement("COMMIT;");
            int resultSet = preparedStatement.executeUpdate();
            connection.setAutoCommit(true);
            preparedStatement.close();
            connection.close();
            asynCallBack.onSuccess(null);
        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            asynCallBack.onError(e.getMessage());
        }
    }

    private void rollbackEvent(AsynCallBack asynCallBack) {
        try {
            if (connection == null || connection.isClosed()) {
                asynCallBack.onError("数据库连接失败");
                return;
            }
            preparedStatement = connection.prepareStatement("ROLLBACK;");
            int resultSet = preparedStatement.executeUpdate();
            connection.setAutoCommit(true);
            preparedStatement.close();
            connection.close();
            asynCallBack.onSuccess(null);
        } catch (SQLException e) {
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            asynCallBack.onError(e.getMessage());
        }
    }

    /**
     * 取消网络请求
     */
    public void cancleDbDeal() {
        try {
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.cancel();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private <T> List<T> transformData(ResultSet resultSet, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                try {
                    T t = clazz.newInstance();
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        if (!field.isSynthetic() && !field.getName().equals("serialVersionUID")) {
                            field.setAccessible(true);
                            try {
                                field.set(t, resultSet.getObject(field.getName()));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (IllegalArgumentException e) {
                                e.printStackTrace();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
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
