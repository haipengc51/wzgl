package com.jiekai.wzglkg.entity;

import com.jiekai.wzglkg.entity.base.BaseEntity;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by laowu on 2017/12/28.
 * 设备库存表
 */

public class DevicestoreEntity extends BaseEntity {
    private int ID;
    private String SBBH;    //设备编号
    private Date CZSJ;    //操作时间
    private String CZR;     //操作人
    private String LB;      //类别
    private String JH;      //井号
    private String BZ;      //备注
    private String LYDW;    //领用单位
    private String SHYJ;    //审核意见
    private Timestamp SHSJ;    //审核时间
    private String SHR;     //审核人
    private String SHBZ;    //审核备注
    private String czrname;     //操作人的姓名

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
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

    public String getSHYJ() {
        return SHYJ;
    }

    public void setSHYJ(String SHYJ) {
        this.SHYJ = SHYJ;
    }

    public Timestamp getSHSJ() {
        return SHSJ;
    }

    public void setSHSJ(Timestamp SHSJ) {
        this.SHSJ = SHSJ;
    }

    public String getSHR() {
        return SHR;
    }

    public void setSHR(String SHR) {
        this.SHR = SHR;
    }

    public String getSHBZ() {
        return SHBZ;
    }

    public void setSHBZ(String SHBZ) {
        this.SHBZ = SHBZ;
    }

    public String getCzrname() {
        return czrname;
    }

    public void setCzrname(String czrname) {
        this.czrname = czrname;
    }
}
