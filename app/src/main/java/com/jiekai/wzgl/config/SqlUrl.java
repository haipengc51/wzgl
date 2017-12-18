package com.jiekai.wzgl.config;

/**
 * Created by LaoWu on 2017/12/6.
 */

public class SqlUrl {
    /**
     * 登录数据库操作
     */
    public static final String LoginSql = "SELECT * FROM userinfo where USERID = ? AND PASSWORD = ?";
    /**
     * 获取设备类型
     */
    public static final String GetDeviceType = "SELECT * FROM devicesort";
    /**
     * 获取设备名称（通过第一级 设备类别）
     */
    public static final String GetDeviceMCByLB = "SELECT device.MC FROM device where LB = ?";
    /**
     * 获取设备名称（通过第二级 设备型号）
     */
    public static final String GetDeviceMCByXh = "SELECT device.MC FROM device where XH = ?";
    /**
     * 获取设备名称（通过第三级 设备规格）
     */
    public static final String GetDeviceMCByGG = "SELECT device.MC FROM device where GG = ?";
    /**
     * 获取设备自编号 通过设备名称
     */
    public static final String GetDeviceBHByMC = "SELECT device.BH FROM device where MC = ?";
    /**
     * 根据电子码编号获取设备信息
     */
    public static final String GetDeviceByID = "SELECT * FROM device where IDDZMBH1 = ?";
    /**
     * 往一个设备中添加配件
     */
    public static final String AddDepart = "UPDATE device SET SFPJ = ? , SSSBBH = ? WHERE BH = ?";
    /**
     * 根据设备id查询其配件的列表
     */
    public static final String GetPartListByDeviceId = "SELECT BH, MC, IDDZMBH1 FROM device WHERE SFPJ = \"1\" AND SSSBBH = ?";
    /**
     * 绑定设备
     */
    public static final String BIND_DEVICE = "UPDATE device SET IDDZMBH1 = ? WHERE BH = ?";
    /**
     * 插入设备文档表（绑定图片）
     */
    public static final String SaveDoc = "INSERT INTO devicedoc (SBBH, WJMC, WJDX, WJDZ, WDLX, LB) VALUES (?, ?, ?, ?, ?, ?)";
    /**
     * 获取已经审核通过，的结果
     */
    public static final String GetShenHeList = "SELECT * FROM deviceapply WHERE SPZT = \"1\"";
}
