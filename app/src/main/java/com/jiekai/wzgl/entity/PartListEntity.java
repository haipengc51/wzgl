package com.jiekai.wzgl.entity;

import com.jiekai.wzgl.entity.base.BaseEntity;

/**
 * Created by LaoWu on 2017/12/15.
 * 配件列表的实体
 */

public class PartListEntity extends BaseEntity {
    private String BH;      //配件自编码
    private String MC;      //配件名称
    private String IDDZMBH1;    //配件的标签号

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

    public String getIDDZMBH1() {
        return IDDZMBH1;
    }

    public void setIDDZMBH1(String IDDZMBH1) {
        this.IDDZMBH1 = IDDZMBH1;
    }
}
