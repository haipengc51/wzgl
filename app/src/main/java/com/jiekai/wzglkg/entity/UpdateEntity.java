package com.jiekai.wzglkg.entity;

import com.jiekai.wzglkg.entity.base.BaseEntity;

/**
 * Created by LaoWu on 2018/3/12.
 */

public class UpdateEntity extends BaseEntity {
    private String LB;
    private int VERSION;
    private String INFO;
    private String FORCE;
    private String PATH;
    private String localPath;   //本地存放的更新文件路径
    private long localFileSize; //本地存储文件大小

    public String getLB() {
        return LB;
    }

    public void setLB(String LB) {
        this.LB = LB;
    }

    public int getVERSION() {
        return VERSION;
    }

    public void setVERSION(int VERSION) {
        this.VERSION = VERSION;
    }

    public String getINFO() {
        return INFO;
    }

    public void setINFO(String INFO) {
        this.INFO = INFO;
    }

    public String getFORCE() {
        return FORCE;
    }

    public void setFORCE(String FORCE) {
        this.FORCE = FORCE;
    }

    public String getPATH() {
        return PATH;
    }

    public void setPATH(String PATH) {
        this.PATH = PATH;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public long getLocalFileSize() {
        return localFileSize;
    }

    public void setLocalFileSize(long localFileSize) {
        this.localFileSize = localFileSize;
    }
}
