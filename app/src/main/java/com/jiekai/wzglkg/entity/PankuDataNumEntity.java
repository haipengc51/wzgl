package com.jiekai.wzglkg.entity;

import com.jiekai.wzglkg.entity.base.BaseEntity;

/**
 * Created by laowu on 2017/12/24.
 * 盘库的各类设备数量的实体
 */

public class PankuDataNumEntity extends BaseEntity {
    public String LB;  //设备类别
    public String XH;  //设备型号
    public String GG;   //设备规格
    public long NUM; //数量

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

    public long getNUM() {
        return NUM;
    }

    public void setNUM(long NUM) {
        this.NUM = NUM;
    }
}
