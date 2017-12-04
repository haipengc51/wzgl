package com.jiekai.wzgl.ftputils;

import com.jiekai.wzgl.utils.LogUtils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
            // TODO Auto-generated catch block
            e.printStackTrace();
            LogUtils.e(e.getMessage());
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
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
                return "连接服务器失败";
            }
        }
        try {
            //设置存储路径
            if (ftpClient.listDirectories(remotePath).length == 0) {
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
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.getMessage();
        }
    }

    /**
     * 下载文件
     * @param FilePath  要存放的文件的路径
     * @param FileName   远程FTP服务器上的那个文件的名字
     * @return   true为成功，false为失败
     */
    public boolean downLoadFile(String FilePath, String FileName) {
        if (!ftpClient.isConnected()) {
            if (!initFTPSetting(FTPUrl,  FTPPort,  UserName,  UserPassword)) {
                return false;
            }
        }
        try {
            // 转到指定下载目录
            ftpClient.changeWorkingDirectory("/data");
            // 列出该目录下所有文件
            FTPFile[] files = ftpClient.listFiles();
            // 遍历所有文件，找到指定的文件
            for (FTPFile file : files) {
                if (file.getName().equals(FileName)) {
                    //根据绝对路径初始化文件
                    File localFile = new File(FilePath);
                    // 输出流
                    OutputStream outputStream = new FileOutputStream(localFile);
                    // 下载文件
                    ftpClient.retrieveFile(file.getName(), outputStream);
                    //关闭流
                    outputStream.close();
                }
            }
            //退出登陆FTP，关闭ftpCLient的连接
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }
}
