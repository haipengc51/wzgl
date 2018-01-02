package com.jiekai.wzglkg.entity;

import com.jiekai.wzglkg.entity.base.BaseEntity;

import java.sql.Date;

/**
 * Created by laowu on 2017/12/28.
 */

public class DeviceOutHistoryEntity extends BaseEntity {
    private String MC;  //设备名称
    private String SBBH;    //设备编号
    private Date CZSJ;  //操作时间
    private String JH;      //井号
    private String LYDW;        //领用单位
    private String USERNAME;    //操作人

    public String getMC() {
        return MC;
    }

    public void setMC(String MC) {
        this.MC = MC;
    }

    public String getSBBH() {
        return SBBH;
    }

    public void setSBBH(String SBBH) {
        this.SBBH = SBBH;
    }

    public Date getCZSJ() {
        return CZSJ;
    }

    public void setCZSJ(Date CZSJ) {
        this.CZSJ = CZSJ;
    }

    public String getJH() {
        return JH;
    }

    public void setJH(String JH) {
        this.JH = JH;
    }

    public String getLYDW() {
        return LYDW;
    }

    public void setLYDW(String LYDW) {
        this.LYDW = LYDW;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }
}
