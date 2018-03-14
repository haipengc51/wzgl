package com.jiekai.wzglkg.utils.ftputils;

import com.jiekai.wzglkg.utils.dbutils.PlantFrom;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by laowu on 2017/12/4.
 */

public class FtpManager {
    private static FtpManager mFtpManager;
    private FTPUtils mFtpUtils;
    private Executor mExecutor;
    private PlantFrom plantFrom;

    public FtpManager() {
        if (mFtpUtils == null) {
            mFtpUtils = mFtpUtils.getInstance();
        }
        if (mExecutor == null) {
            mExecutor = Executors.newCachedThreadPool();
        }
        if (plantFrom == null) {
            plantFrom = plantFrom.getInstance();
        }
    }

    public static FtpManager getInstance() {
        if (mFtpManager == null) {
            mFtpManager = new FtpManager();
        }
        return mFtpManager;
    }

    /**
     * FTC上传图片
     * @param localFilePath
     * @param remotePath
     * @param remoteFileName
     * @param ftpCallBack
     */
    public void uploadFile(final String localFilePath, final String remotePath, final String remoteFileName,
                           final FtpCallBack ftpCallBack) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                doStart(ftpCallBack);
                final String result = mFtpUtils.uploadFile(localFilePath, remotePath, remoteFileName);
                {
                    if (result != null && result.contains(FTPUtils.SUCCESS)) {
                        doSuccess(result, ftpCallBack);
                    } else {
                        doFaild(result, ftpCallBack);
                    }
                }
            }
        });
    }

    public void deletFile(final String filePath, final FtpCallBack ftpCallBack) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                doStart(ftpCallBack);
                boolean success = mFtpUtils.deletFile(filePath);
                if (success) {
                    doSuccess("", ftpCallBack);
                } else {
                    doFaild("", ftpCallBack);
                }
            }
        });
    }

    /**
     * 下载文件
     */
    public void downloadFile(final String localFilePath, final String remoteFilePath, final String remoteFileName, final FtpCallBack ftpCallBack) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mFtpUtils.downLoadFile(localFilePath, remoteFilePath, remoteFileName, plantFrom, ftpCallBack);
            }
        });
    }

    public void initFTP(final String FtpUrl, final int FtpPort, final String FtpUserName, final String FtpPassword) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mInitFtpServer(FtpUrl, FtpPort, FtpUserName, FtpPassword);
            }
        });
    }

    private void mInitFtpServer(String FtpUrl, int FtpPort, String FtpUserName, String FtpPassword) {
        if (mFtpUtils == null) {
            mFtpUtils = mFtpUtils.getInstance();
        }
        mFtpUtils.initFTPSetting(FtpUrl, FtpPort, FtpUserName, FtpPassword);
    }

    private void doStart(final FtpCallBack ftpCallBack) {
        plantFrom.execut(new Runnable() {
            @Override
            public void run() {
                ftpCallBack.ftpStart();
            }
        });
    }

    private void doSuccess(final String result, final FtpCallBack ftpCallBack) {
        plantFrom.execut(new Runnable() {
            @Override
            public void run() {
                String filePath = "";
                if (result.indexOf(FTPUtils.DIVITION) != 0 && result.indexOf(FTPUtils.DIVITION) != -1) {
                    filePath = result.substring(result.indexOf(FTPUtils.DIVITION) + FTPUtils.DIVITION.length());
                }
                ftpCallBack.ftpSuccess(filePath);
            }
        });
    }

    private void doFaild(final String result, final FtpCallBack ftpCallBack) {
        plantFrom.execut(new Runnable() {
            @Override
            public void run() {
                ftpCallBack.ftpFaild(result);
            }
        });
    }
}
