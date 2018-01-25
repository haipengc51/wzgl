package com.jiekai.wzglkg.entity;

import com.jiekai.wzglkg.entity.base.BaseEntity;

/**
 * Created by laowu on 2017/12/26.
 */

public class LastInsertIdEntity extends BaseEntity{
    private long last_insert_id;

    public long getLast_insert_id() {
        return last_insert_id;
    }

    public void setLast_insert_id(long last_insert_id) {
        this.last_insert_id = last_insert_id;
    }
}
