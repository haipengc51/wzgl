package com.jiekai.wzgl.entity;

import com.jiekai.wzgl.entity.base.BaseEntity;

/**
 * Created by laowu on 2017/12/24.
 * 盘库的各类设备数量的实体
 */

public class PankuDataNumEntity extends BaseEntity {
    public String LB = "LB";  //设备类别
    public String XH = "XH";  //设备型号
    public String GG = "GG";   //设备规格
    public String NUM = "NUM"; //数量

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

    public String getNUM() {
        return NUM;
    }

    public void setNUM(String NUM) {
        this.NUM = NUM;
    }
}
