package com.jiekai.wzglkg.entity;

import com.jiekai.wzglkg.entity.base.BaseEntity;

/**
 * Created by laowu on 2018/1/9.
 * 未审核通过的实体类
 */

public class DeviceUnCheckEntity extends BaseEntity {
    private int type;
    private Object data;
    private String JLZL;    //记录种类
    private String ID;  //设备自编码
    private String YJ;  //审核意见

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getJLZL() {
        return JLZL;
    }

    public void setJLZL(String JLZL) {
        this.JLZL = JLZL;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getYJ() {
        return YJ;
    }

    public void setYJ(String YJ) {
        this.YJ = YJ;
    }
}
