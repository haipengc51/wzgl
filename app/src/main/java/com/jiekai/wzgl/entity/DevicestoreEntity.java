package com.jiekai.wzgl.entity;

import com.jiekai.wzgl.entity.base.BaseEntity;

import java.sql.Date;

/**
 * Created by laowu on 2017/12/28.
 * 设备库存表
 */

public class DevicestoreEntity extends BaseEntity {
    private String ID;
    private String SBBH;    //设备编号
    private Date CZSJ;    //操作时间
    private String CZR;     //操作人
    private String LB;      //类别
    private String JH;      //井号
    private String BZ;      //备注
    private String LYDW;    //领用单位

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

    public Date getCZSJ() {
        return CZSJ;
    }

    public void setCZSJ(Date CZSJ) {
        this.CZSJ = CZSJ;
    }

    public String getCZR() {
        return CZR;
    }

    public void setCZR(String CZR) {
        this.CZR = CZR;
    }

    public String getLB() {
        return LB;
    }

    public void setLB(String LB) {
        this.LB = LB;
    }

    public String getJH() {
        return JH;
    }

    public void setJH(String JH) {
        this.JH = JH;
    }

    public String getBZ() {
        return BZ;
    }

    public void setBZ(String BZ) {
        this.BZ = BZ;
    }

    public String getLYDW() {
        return LYDW;
    }

    public void setLYDW(String LYDW) {
        this.LYDW = LYDW;
    }
}
