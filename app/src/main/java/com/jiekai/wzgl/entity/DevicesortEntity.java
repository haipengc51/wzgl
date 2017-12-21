package com.jiekai.wzgl.entity;

import com.jiekai.wzgl.entity.base.BaseEntity;

/**
 * Created by laowu on 2017/12/21.
 * 设备类别实体
 */

public class DevicesortEntity extends BaseEntity {
    private String COOD;
    private String TEXT;
    private String PARENTCOOD;
    private String PXXH;

    public String getCOOD() {
        return COOD;
    }

    public void setCOOD(String COOD) {
        this.COOD = COOD;
    }

    public String getTEXT() {
        return TEXT;
    }

    public void setTEXT(String TEXT) {
        this.TEXT = TEXT;
    }

    public String getPARENTCOOD() {
        return PARENTCOOD;
    }

    public void setPARENTCOOD(String PARENTCOOD) {
        this.PARENTCOOD = PARENTCOOD;
    }

    public String getPXXH() {
        return PXXH;
    }

    public void setPXXH(String PXXH) {
        this.PXXH = PXXH;
    }
}
