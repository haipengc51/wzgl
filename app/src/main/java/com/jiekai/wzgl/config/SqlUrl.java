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
     * 查询全部的设备类别
     */
    public static final String GetAllLeiBie = "SELECT * FROM devicesort WHERE PARENTCOOD = \"0\"";
    /**
     * 查询对应类别的设备型号
     */
    public static final String GetXingHaoByLeiBie = "SELECT * FROM devicesort WHERE PARENTCOOD = ?";
    /**
     * 查询对应设备型号的规格
     */
    public static final String GetGuiGeByXingHao = "SELECT * FROM devicesort WHERE PARENTCOOD = ?";
    /**
     * 通过类别，型号，规格获取设备信息
     */
    public static final String GetBHByLeiBieXinghaoGuige = "SELECT device.BH FROM device where LB = ? AND XH = ? AND GG = ?";
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
    /**
     * 执行设备出库操作 (设备自编码，操作时间，操作人，类别， 井号)
     */
    public static final String OUT_DEVICE = "INSERT INTO devicestore (SBBH, CZSJ, CZR, LB, JH) VALUES (?, ?, ?, ?, ?);";
    /**
     * 执行设备入库操作 (设备自编码，操作时间，操作人，类别)
     */
    public static final String IN_DEVICE = "INSERT INTO devicestore (SBBH, CZSJ, CZR, LB) VALUES (?, ?, ?, ?);";
    /**
     * 执行设备维修操作 (设备自编码，操作时间，操作人，类别)
     */
    public static final String REPAIR_DEVICE = "INSERT INTO devicestore (SBBH, CZSJ, CZR, LB) VALUES (?, ?, ?, ?);";
    /**
     * 查找设备出库表
     */
    public static final String GetDeviceOut = "SELECT * FROM devicestore WHERE SBBH = ? AND LB = 0";
    /**
     * 查找设备入库表
     */
    public static final String GetDeviceIN = "SELECT * FROM devicestore WHERE SBBH = ? AND LB = 1";
    /**
     * 根据盘库的需求查询数据库
     */
    public static final String GetPanKuDataByID = "SELECT " +
            "dv.BH, dv.MC, leibie.TEXT AS LB,xinghao.TEXT AS XH,guige.TEXT AS GG" +
            " FROM " +
            "devicesort AS leibie, devicesort AS xinghao, devicesort AS guige, device as dv" +
            " WHERE " +
            "dv.IDDZMBH1 = ? " +
            "AND leibie.COOD = dv.LB " +
            "AND xinghao.COOD = dv.XH " +
            "AND guige.COOD = dv.GG";
    /**
     * 插入图片到服务器中（ID, 文件名称， 文件大小， 文件地址，文件类型，类别）
     */
    public static final String INSERT_IAMGE = "INSERT INTO devicedoc (SBBH, WJMC, WJDX, WJDZ, WDLX, LB) VALUES (?, ?, ?, ?, ?, ?)";
    /**
     * 查找上次插入数据所返回的ID
     */
    public static final String SELECT_INSERT_ID = "SELECT LAST_INSERT_ID() AS last_insert_id";
    /**
     * 插入设备报废信息
     */
    public static final String ADD_DEVICE_SCRAP = "INSERT INTO devicescrap (SBBH, BFZP, BFSJ, BFR) VALUES (?, ?, ?, ?)";
    /**
     * 查找报废设备
     */
    public static final String GET_SCRAP_DEVICE = "SELECT * FROM devicescrap WHERE SBBH = ?";
    /**
     * 更改设备状态
     */
    public static final String CHANGE_DEVICE_STATE = "UPDATE device SET SBZT = ? WHERE BH = ?";
}
