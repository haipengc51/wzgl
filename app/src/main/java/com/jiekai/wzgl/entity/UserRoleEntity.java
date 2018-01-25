package com.jiekai.wzgl.entity;

import com.jiekai.wzgl.entity.base.BaseEntity;

/**
 * Created by laowu on 2018/1/2.
 * 用户权限id
 */

public class UserRoleEntity extends BaseEntity {
    private String USERID;
    private String ROLEID;

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getROLEID() {
        return ROLEID;
    }

    public void setROLEID(String ROLEID) {
        this.ROLEID = ROLEID;
    }
}
