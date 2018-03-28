package com.jiekai.wzglkg.utils.ftputils;

import android.util.Log;

import com.jiekai.wzglkg.utils.LogUtils;
import com.jiekai.wzglkg.utils.dbutils.PlantFrom;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

/**
 * Created by laowu on 2017/12/3.
 * FTP上传图片的工具类
 */

public class FTPUtils {
    public static final String SUCCESS = "succ-ess";
    public static final String DIVITION = ":::";

    private static FTPUtils ftpUtilsInstance = null;
    private FTPClient ftpClient = null;
    private String FTPUrl;
    private int FTPPort;
    private String UserName;
    private String UserPassword;

    private FTPUtils() {
        ftpClient = new FTPClient();
    }
    /*
     * 得到类对象实例（因为只能有一个这样的类对象，所以用单例模式）
     */
    public  static FTPUtils getInstance() {
        if (ftpUtilsInstance == null) {
            ftpUtilsInstance = new FTPUtils();
        }
        return ftpUtilsInstance;
    }

    /**
     * 设置FTP服务器
     * @param FTPUrl   FTP服务器ip地址
     * @param FTPPort   FTP服务器端口号
     * @param UserName    登陆FTP服务器的账号
     * @param UserPassword    登陆FTP服务器的密码
     * @return
     */
    public boolean initFTPSetting(String FTPUrl, int FTPPort, String UserName, String UserPassword) {
        this.FTPUrl = FTPUrl;
        this.FTPPort = FTPPort;
        this.UserName = UserName;
        this.UserPassword = UserPassword;
        int reply;
        try {
            //1.要连接的FTP服务器Url,Port
            ftpClient.connect(FTPUrl, FTPPort);
            //2.登陆FTP服务器
            if(!ftpClient.login(UserName, UserPassword)) {
                LogUtils.e("用户名或密码错误");
                return false;
            }
            //3.看返回的值是不是230，如果是，表示登陆成功
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {//断开
                ftpClient.disconnect();
                LogUtils.e("登录ftp服务器失败");
                return false;
            }
            return true;
        } catch (SocketException e) {
            e.printStackTrace();
            LogUtils.e(e.getMessage());
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.e(e.getMessage());
            return false;
        }
    }

    /**
     * 上传文件
     * @param localFilePath    要上传文件所在的路径
     * @param remotePath    要上传的文件的文件路径
     * @param remoteFileName 要上传的文件的文件名(如：Sim唯一标识码)
     * @return    true为成功，false为失败
     */
    public String uploadFile(String localFilePath, String remotePath, String remoteFileName) {
        String upFielPath = remotePath;
        if (!ftpClient.isConnected()) {
            if (!initFTPSetting(FTPUrl,  FTPPort,  UserName,  UserPassword)) {
                return "连接服务器失败，请检查网络";
            }
        }
        try {
            //设置存储路径
            if (!ftpClient.changeWorkingDirectory(remotePath)) {
                ftpClient.makeDirectory(remotePath);
            }
            if (!ftpClient.changeWorkingDirectory(remotePath)) {
                upFielPath = "/";
            }
            //设置上传文件需要的一些基本信息
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            //文件上传吧～
            File file = new File(localFilePath);
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(localFilePath);
                if (ftpClient.storeFile(remoteFileName, fileInputStream)) {
                    upFielPath += remoteFileName;
                    //关闭文件流
                    fileInputStream.close();
                    //退出登陆FTP，关闭ftpCLient的连接
                    ftpClient.logout();
                    ftpClient.disconnect();
                    return SUCCESS + DIVITION + upFielPath;
                } else {
                    //关闭文件流
                    fileInputStream.close();
                    ftpClient.logout();
                    ftpClient.disconnect();
                    return "上传失败";
                }
            } else {
                LogUtils.e("上传的文件不存在");
                //退出登陆FTP，关闭ftpCLient的连接
                ftpClient.logout();
                ftpClient.disconnect();
                return "上传的文件不存在";
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e1) {
                e1.printStackTrace();
                try {
                    ftpClient.disconnect();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            return e.getMessage();
        }
    }

    /**
     * 下载文件
     * @param localFilePath  要存放的文件的路径
     * @param remoteFilePath   远程FTP服务器上的那个文件的路径
     * @param remoteFileName   远程FTP服务器上的那个文件的名字
     * @return   true为成功，false为失败
     */
    public String downLoadFile(String localFilePath, String remoteFilePath, String remoteFileName, PlantFrom plantFrom, FtpCallBack ftpCallBack) {
        doStart(plantFrom, ftpCallBack);
        if (!ftpClient.isConnected()) {
            if (!initFTPSetting(FTPUrl,  FTPPort,  UserName,  UserPassword)) {
                doFail(plantFrom, ftpCallBack, "连接服务器失败，请检查网络");
                return "连接服务器失败，请检查网络";
            }
        }
        try {
            //判断远程服务器的文件名是否存在
            if (!ftpClient.changeWorkingDirectory(remoteFilePath)){
                doFail(plantFrom, ftpCallBack, "服务器中没有此文件");
                return "服务器中没有此文件";
            }
            //判断本地服务器的文件名是否存在
            File localPath = new File(localFilePath);
            if (!localPath.exists()) {
                localPath.mkdirs();
            }
            //下载文件
            FTPFile[] files = ftpClient.listFiles();
            for (FTPFile file : files) {
                if (file.getName().equals(remoteFileName)) {
                    File localFile = new File(localFilePath + remoteFileName);
                    if (!localFile.exists()) {
                        localFile.createNewFile();
                    }
                    long allSize = file.getSize();
                    OutputStream outputStream = new FileOutputStream(localFile);
                    ftpClient.enterLocalPassiveMode();
                    ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                    InputStream inputStream = ftpClient.retrieveFileStream(new String(file.getName()));

                    byte[] bytes = new byte[1024];
                    long step = allSize / 100;
                    long localSize = 0;
                    int onceReadSize;
                    while ((onceReadSize = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, onceReadSize);
                        localSize += onceReadSize;
                        int process = (int) (localSize / step);
                        if (process % 3 == 0){
                            doProgress(plantFrom, ftpCallBack, allSize, localSize, process);
                        }
                    }
                    inputStream.close();
                    outputStream.flush();
                    outputStream.close();
                    ftpClient.completePendingCommand();
                    ftpClient.logout();
                    ftpClient.disconnect();
                    doSuccess(plantFrom, ftpCallBack, SUCCESS + DIVITION + localFilePath + remoteFileName);
                    return SUCCESS + DIVITION + localFilePath + remoteFileName;
                }
            }
            doFail(plantFrom, ftpCallBack, "服务器中没有此文件");
            return "服务器中没有此文件";
        } catch (IOException e) {
            e.printStackTrace();
            doFail(plantFrom, ftpCallBack, e.getMessage());
            return e.getMessage();
        }
    }

    /**
     * 删除FTP服务器上的文件
     * @param pathName  服务器上的文件路径
     * @return
     */
    public boolean deletFile(String pathName) {
        if (!ftpClient.isConnected()) {
            if (!initFTPSetting(FTPUrl,  FTPPort,  UserName,  UserPassword)) {
                return false;
            }
        }
        try {
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            boolean deleted = ftpClient.deleteFile(pathName);
            //退出登陆FTP，关闭ftpCLient的连接
            ftpClient.logout();
            ftpClient.disconnect();
            return  deleted;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void doStart(PlantFrom plantFrom, final FtpCallBack ftpCallBack) {
        plantFrom.execut(new Runnable() {
            @Override
            public void run() {
                ftpCallBack.ftpStart();
            }
        });
    }

    private void doProgress(PlantFrom plantFrom, final FtpCallBack ftpCallBack, final long allSize, final long currentSize, final int process) {
        plantFrom.execut(new Runnable() {
            @Override
            public void run() {
                ftpCallBack.ftpProgress(allSize, currentSize, process);
            }
        });
    }

    private void doSuccess(PlantFrom plantFrom, final FtpCallBack ftpCallBack, final String result) {
        plantFrom.execut(new Runnable() {
            @Override
            public void run() {
                String filePath = "";
                if (result.indexOf(DIVITION) != 0 && result.indexOf(DIVITION) != -1) {
                    filePath = result.substring(result.indexOf(DIVITION)+ DIVITION.length());
                }
                ftpCallBack.ftpSuccess(filePath);
            }
        });
    }

    private void doFail(PlantFrom plantFrom, final FtpCallBack ftpCallBack, final String result) {
        plantFrom.execut(new Runnable() {
            @Override
            public void run() {
                ftpCallBack.ftpFaild(result);
            }
        });
    }
}
