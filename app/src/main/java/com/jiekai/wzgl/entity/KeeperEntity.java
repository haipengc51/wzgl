package com.jiekai.wzgl.entity;

import com.jiekai.wzgl.entity.base.BaseEntity;

/**
 * Created by laowu on 2017/12/7.
 */

public class KeeperEntity extends BaseEntity {
    private String name;
    private Class activity;

    public KeeperEntity(String name, Class activity) {
        this.name = name;
        this.activity = activity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }
}
