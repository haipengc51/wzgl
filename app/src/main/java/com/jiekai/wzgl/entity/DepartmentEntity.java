package com.jiekai.wzgl.entity;

import com.jiekai.wzgl.entity.base.BaseEntity;

/**
 * Created by LaoWu on 2017/12/5.
 */

public class DepartmentEntity extends BaseEntity{
    private String DWBM;
    private String DWMC;
    private String SJDW;
    private String PXXH;

    public String getDWBM() {
        return DWBM;
    }

    public void setDWBM(String DWBM) {
        this.DWBM = DWBM;
    }

    public String getDWMC() {
        return DWMC;
    }

    public void setDWMC(String DWMC) {
        this.DWMC = DWMC;
    }

    public String getSJDW() {
        return SJDW;
    }

    public void setSJDW(String SJDW) {
        this.SJDW = SJDW;
    }

    public String getPXXH() {
        return PXXH;
    }

    public void setPXXH(String PXXH) {
        this.PXXH = PXXH;
    }
}
