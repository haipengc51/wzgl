package com.jiekai.wzglkg.entity;

import com.jiekai.wzglkg.entity.base.BaseEntity;

/**
 * Created by laowu on 2018/1/1.
 * 设备文档
 */

public class DevicedocEntity extends BaseEntity {
    private String ID;
    private String SBBH;
    private String WJMC;    //文件名称
    private String WJDX;    //文件大小
    private String WJDZ;    //文件地址
    private String WDLX;    //文件类型
    private String LB;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSBBH() {
        return SBBH;
    }

    public void setSBBH(String SBBH) {
        this.SBBH = SBBH;
    }

    public String getWJMC() {
        return WJMC;
    }

    public void setWJMC(String WJMC) {
        this.WJMC = WJMC;
    }

    public String getWJDX() {
        return WJDX;
    }

    public void setWJDX(String WJDX) {
        this.WJDX = WJDX;
    }

    public String getWJDZ() {
        return WJDZ;
    }

    public void setWJDZ(String WJDZ) {
        this.WJDZ = WJDZ;
    }

    public String getWDLX() {
        return WDLX;
    }

    public void setWDLX(String WDLX) {
        this.WDLX = WDLX;
    }

    public String getLB() {
        return LB;
    }

    public void setLB(String LB) {
        this.LB = LB;
    }
}
