package com.jiekai.wzglkg.entity;

import com.jiekai.wzglkg.entity.base.BaseEntity;

/**
 * Created by laowu on 2017/12/24.
 */

public class PankuDataListEntity extends BaseEntity {
    public String BH = "BH";  //设备自编号
    public String MC = "MC";  //设备名称
    public String LB = "LB";  //设备类别
    public String XH = "XH";  //设备型号
    public String GG = "GG";   //设备规格
    public String IDDZMBH1 = "IDDZMBH1"; //ID电子码编号1

    public String getBH() {
        return BH;
    }

    public void setBH(String BH) {
        this.BH = BH;
    }

    public String getMC() {
        return MC;
    }

    public void setMC(String MC) {
        this.MC = MC;
    }

    public String getLB() {
        return LB;
    }

    public void setLB(String LB) {
        this.LB = LB;
    }

    public String getXH() {
        return XH;
    }

    public void setXH(String XH) {
        this.XH = XH;
    }

    public String getGG() {
        return GG;
    }

    public void setGG(String GG) {
        this.GG = GG;
    }

    public String getIDDZMBH1() {
        return IDDZMBH1;
    }

    public void setIDDZMBH1(String IDDZMBH1) {
        this.IDDZMBH1 = IDDZMBH1;
    }
}
