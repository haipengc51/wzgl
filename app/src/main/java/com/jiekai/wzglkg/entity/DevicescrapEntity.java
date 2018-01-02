package com.jiekai.wzglkg.entity;

import com.jiekai.wzglkg.entity.base.BaseEntity;

import java.sql.Date;

/**
 * Created by laowu on 2017/12/28.
 * 设备报废表
 */

public class DevicescrapEntity extends BaseEntity{
    private String SBBH;
    private String BFZP;
    private Date BFSJ;
    private String BFR;
    private String BZ;

    public String getSBBH() {
        return SBBH;
    }

    public void setSBBH(String SBBH) {
        this.SBBH = SBBH;
    }

    public String getBFZP() {
        return BFZP;
    }

    public void setBFZP(String BFZP) {
        this.BFZP = BFZP;
    }

    public Date getBFSJ() {
        return BFSJ;
    }

    public void setBFSJ(Date BFSJ) {
        this.BFSJ = BFSJ;
    }

    public String getBFR() {
        return BFR;
    }

    public void setBFR(String BFR) {
        this.BFR = BFR;
    }

    public String getBZ() {
        return BZ;
    }

    public void setBZ(String BZ) {
        this.BZ = BZ;
    }
}
