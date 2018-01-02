package com.jiekai.wzglkg.entity;

import com.jiekai.wzglkg.entity.base.BaseEntity;
import com.jiekai.wzglkg.utils.treeutils.TreeNodeId;
import com.jiekai.wzglkg.utils.treeutils.TreeNodePXXH;
import com.jiekai.wzglkg.utils.treeutils.TreeNodePid;
import com.jiekai.wzglkg.utils.treeutils.TreeNodeTEXT;

/**
 * Created by laowu on 2017/12/11.
 */

public class DeviceTypeEntity extends BaseEntity {
    @TreeNodeId
    private String COOD;
    @TreeNodeTEXT
    private String TEXT;
    @TreeNodePid
    private String PARENTCOOD;
    @TreeNodePXXH
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
