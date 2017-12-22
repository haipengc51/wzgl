package com.jiekai.wzgl.adapter;

import com.jiekai.wzgl.entity.base.BaseEntity;

/**
 * Created by laowu on 2017/12/22.
 * 设备详情页的 实体-- 统一个数据结构方便使用listview展示
 */

public class DeviceDetailAdapterEntity extends BaseEntity {
    private String title;
    private String content;

    public DeviceDetailAdapterEntity(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
