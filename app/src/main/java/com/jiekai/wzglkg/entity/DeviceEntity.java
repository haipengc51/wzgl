package com.jiekai.wzglkg.entity;

import com.jiekai.wzglkg.entity.base.BaseEntity;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by laowu on 2017/12/12.
 * device表的全部内容
 */

public class DeviceEntity extends BaseEntity {
    private String BH;      //自编号
    private String MC;      //设备名称
    private String LB;      //设备类别
    private String XH;      //设备型号
    private String GG;      //设备规格
    private String JLQDMC;      //物资记录清单名称
    private String LZJLBH;      //物资流转记录编号
    private String TMBH;      //条码编号
    private String EWMBH;      //二维码编号
    private String KWZT;      //物资库位状态
    private String CCBH;      //出厂编号
    private String GYS;      //供应商
    private String GYSGB;      //供应商国别
    private String IDDZMBH1;      //ID电子码编号1
    private String IDDZMBH2;      //ID电子码编号2
    private String IDDZMBH3;      //ID电子码编号3
    private String ZCLY;      //物资资产来源
    private String SYDW;      //物资所有单位
    private String SHIYDW;      //物资使用单位
    private String GYSDH;      //供应商电话
    private String GYSDZ;      //供应商地址
    private String GYSJSFWDH;      //供应商技术服务电话
    private Date SCRQ;      //生产日期
    private Date DHRQ;      //到货日期
    private Date JHRQ;      //进货日期
    private Date TCRQ;      //投产日期
    private String JLDW;      //计量单位
    private String JBR;      //经办人
    private String YSJCBG;      //原始检测报告
    private String AZJCXM;      //安装检查项目
    private String FFDJ;      //防腐等级
    private String CCHGZ;      //出厂合格证
    private String ZJFS;      //折旧方式
    private String SYSM;      //物资使用寿命
    private String WZMP;      //物资铭牌
    private String WZYZ;      //物资原值
    private String WZJZ;      //物资净值
    private String WZCZ;      //物资残值
    private String JGTZ;      //结构图纸
    private String SMS;      //使用维护说明书
    private String YSPJ;      //易损配件
    private String QTFJ;      //其他附件
    private String SFPJ;      //是否配件
    private String SSSBBH;      //如果是配件，所属主设备编号
    private String SBZT;      //设备状态
    private Timestamp DJSJ;      //登记时间

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

    public String getJLQDMC() {
        return JLQDMC;
    }

    public void setJLQDMC(String JLQDMC) {
        this.JLQDMC = JLQDMC;
    }

    public String getLZJLBH() {
        return LZJLBH;
    }

    public void setLZJLBH(String LZJLBH) {
        this.LZJLBH = LZJLBH;
    }

    public String getTMBH() {
        return TMBH;
    }

    public void setTMBH(String TMBH) {
        this.TMBH = TMBH;
    }

    public String getEWMBH() {
        return EWMBH;
    }

    public void setEWMBH(String EWMBH) {
        this.EWMBH = EWMBH;
    }

    public String getKWZT() {
        return KWZT;
    }

    public void setKWZT(String KWZT) {
        this.KWZT = KWZT;
    }

    public String getCCBH() {
        return CCBH;
    }

    public void setCCBH(String CCBH) {
        this.CCBH = CCBH;
    }

    public String getGYS() {
        return GYS;
    }

    public void setGYS(String GYS) {
        this.GYS = GYS;
    }

    public String getGYSGB() {
        return GYSGB;
    }

    public void setGYSGB(String GYSGB) {
        this.GYSGB = GYSGB;
    }

    public String getIDDZMBH1() {
        return IDDZMBH1;
    }

    public void setIDDZMBH1(String IDDZMBH1) {
        this.IDDZMBH1 = IDDZMBH1;
    }

    public String getIDDZMBH2() {
        return IDDZMBH2;
    }

    public void setIDDZMBH2(String IDDZMBH2) {
        this.IDDZMBH2 = IDDZMBH2;
    }

    public String getIDDZMBH3() {
        return IDDZMBH3;
    }

    public void setIDDZMBH3(String IDDZMBH3) {
        this.IDDZMBH3 = IDDZMBH3;
    }

    public String getZCLY() {
        return ZCLY;
    }

    public void setZCLY(String ZCLY) {
        this.ZCLY = ZCLY;
    }

    public String getSYDW() {
        return SYDW;
    }

    public void setSYDW(String SYDW) {
        this.SYDW = SYDW;
    }

    public String getSHIYDW() {
        return SHIYDW;
    }

    public void setSHIYDW(String SHIYDW) {
        this.SHIYDW = SHIYDW;
    }

    public String getGYSDH() {
        return GYSDH;
    }

    public void setGYSDH(String GYSDH) {
        this.GYSDH = GYSDH;
    }

    public String getGYSDZ() {
        return GYSDZ;
    }

    public void setGYSDZ(String GYSDZ) {
        this.GYSDZ = GYSDZ;
    }

    public String getGYSJSFWDH() {
        return GYSJSFWDH;
    }

    public void setGYSJSFWDH(String GYSJSFWDH) {
        this.GYSJSFWDH = GYSJSFWDH;
    }

    public Date getSCRQ() {
        return SCRQ;
    }

    public void setSCRQ(Date SCRQ) {
        this.SCRQ = SCRQ;
    }

    public Date getDHRQ() {
        return DHRQ;
    }

    public void setDHRQ(Date DHRQ) {
        this.DHRQ = DHRQ;
    }

    public Date getJHRQ() {
        return JHRQ;
    }

    public void setJHRQ(Date JHRQ) {
        this.JHRQ = JHRQ;
    }

    public Date getTCRQ() {
        return TCRQ;
    }

    public void setTCRQ(Date TCRQ) {
        this.TCRQ = TCRQ;
    }

    public String getJLDW() {
        return JLDW;
    }

    public void setJLDW(String JLDW) {
        this.JLDW = JLDW;
    }

    public String getJBR() {
        return JBR;
    }

    public void setJBR(String JBR) {
        this.JBR = JBR;
    }

    public String getYSJCBG() {
        return YSJCBG;
    }

    public void setYSJCBG(String YSJCBG) {
        this.YSJCBG = YSJCBG;
    }

    public String getAZJCXM() {
        return AZJCXM;
    }

    public void setAZJCXM(String AZJCXM) {
        this.AZJCXM = AZJCXM;
    }

    public String getFFDJ() {
        return FFDJ;
    }

    public void setFFDJ(String FFDJ) {
        this.FFDJ = FFDJ;
    }

    public String getCCHGZ() {
        return CCHGZ;
    }

    public void setCCHGZ(String CCHGZ) {
        this.CCHGZ = CCHGZ;
    }

    public String getZJFS() {
        return ZJFS;
    }

    public void setZJFS(String ZJFS) {
        this.ZJFS = ZJFS;
    }

    public String getSYSM() {
        return SYSM;
    }

    public void setSYSM(String SYSM) {
        this.SYSM = SYSM;
    }

    public String getWZMP() {
        return WZMP;
    }

    public void setWZMP(String WZMP) {
        this.WZMP = WZMP;
    }

    public String getWZYZ() {
        return WZYZ;
    }

    public void setWZYZ(String WZYZ) {
        this.WZYZ = WZYZ;
    }

    public String getWZJZ() {
        return WZJZ;
    }

    public void setWZJZ(String WZJZ) {
        this.WZJZ = WZJZ;
    }

    public String getWZCZ() {
        return WZCZ;
    }

    public void setWZCZ(String WZCZ) {
        this.WZCZ = WZCZ;
    }

    public String getJGTZ() {
        return JGTZ;
    }

    public void setJGTZ(String JGTZ) {
        this.JGTZ = JGTZ;
    }

    public String getSMS() {
        return SMS;
    }

    public void setSMS(String SMS) {
        this.SMS = SMS;
    }

    public String getYSPJ() {
        return YSPJ;
    }

    public void setYSPJ(String YSPJ) {
        this.YSPJ = YSPJ;
    }

    public String getQTFJ() {
        return QTFJ;
    }

    public void setQTFJ(String QTFJ) {
        this.QTFJ = QTFJ;
    }

    public String getSFPJ() {
        return SFPJ;
    }

    public void setSFPJ(String SFPJ) {
        this.SFPJ = SFPJ;
    }

    public String getSSSBBH() {
        return SSSBBH;
    }

    public void setSSSBBH(String SSSBBH) {
        this.SSSBBH = SSSBBH;
    }

    public String getSBZT() {
        return SBZT;
    }

    public void setSBZT(String SBZT) {
        this.SBZT = SBZT;
    }

    public Timestamp getDJSJ() {
        return DJSJ;
    }

    public void setDJSJ(Timestamp DJSJ) {
        this.DJSJ = DJSJ;
    }
}
