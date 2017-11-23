package com.jiekai.wzgl.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jiekai.wzgl.R;
import com.jiekai.wzgl.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by LaoWu on 2017/11/17.
 */

public class DbTestActivity extends Activity {
    private Button readDb;
    private Button threadBtn;
    private Button threadPoolBtn;
    private Handler threadHandler;
    private Handler threadPoolHandler;
    private Runnable threadRunnable;
    private Runnable threadPoolRunnable;
    private boolean isThread = false;
    private boolean isThreadPool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_db);

        readDb = (Button) findViewById(R.id.read_db);
        readDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readDb();
            }
        });
        findViewById(R.id.thread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isThread) {
                    if (threadHandler != null) {
                        threadHandler.removeCallbacks(threadRunnable);
                    }
                } else {
                    thread();
                }
                isThread = !isThread;
            }
        });
        findViewById(R.id.thread_pool).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isThreadPool) {
                    if (threadPoolHandler != null) {
                        threadPoolHandler.removeCallbacks(threadPoolRunnable);
                    }
                } else {
                    threadPool();
                }
                isThreadPool = !isThreadPool;
            }
        });
    }

    private void readDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                readDbDealProcess();
            }
        }).start();
    }

    private void readDbDealProcess() {
        try {
            Log.i("liu", "执行了一次查询数据库");
            Class.forName(Config.DB_CLASS_NAME);
            String url = "jdbc:oracle:thin:@" + Config.DB_IP + ":" + Config.DB_PORT
                    + ":" + Config.DB_NAME;
            Connection connection = DriverManager.getConnection(url, Config.DB_USER_NAME, Config.DB_USER_PASSWORD);
            String sql = "select * from MY_TABLE";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Log.i("liu", resultSet.getString("xing")+resultSet.getString("name")+ ":" + resultSet.getString("age"));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void thread() {
        threadHandler = new Handler();
        threadRunnable = new Runnable() {
            @Override
            public void run() {
                readDb();
                threadHandler.postDelayed(threadRunnable, 500);
            }
        };
        threadHandler.postDelayed(threadRunnable, 500);
    }

    private void threadPool() {
        final ExecutorService executorService = Executors.newFixedThreadPool(10);
        threadPoolHandler = new Handler();
        threadPoolRunnable = new Runnable() {
            @Override
            public void run() {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        readDbDealProcess();
                    }
                });
                threadPoolHandler.postDelayed(threadPoolRunnable, 500);
            }
        };
        threadPoolHandler.postDelayed(threadPoolRunnable, 500);
    }
}
