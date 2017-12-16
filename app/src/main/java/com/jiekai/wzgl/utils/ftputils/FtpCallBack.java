package com.jiekai.wzgl.utils.ftputils;

/**
 * Created by laowu on 2017/12/4.
 * ftp的回调
 */

public interface FtpCallBack {
    /**
     * 开始上传之前的回调
     */
    public void ftpStart();
    /**
     * 上传成功的回调
     * @param remotePath 上传成功后服务器端的地址（路径+文件名）
     */
    public void ftpSuccess(String remotePath);

    /**
     * 上传失败的回调
     */
    public void ftpFaild(String error);
}
