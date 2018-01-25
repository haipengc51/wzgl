package com.jiekai.wzglkg.config;

/**
 * Created by LaoWu on 2017/12/6.
 */

public class SqlUrl {
    /**
     * 登录数据库操作
     */
    public static final String LoginSql = "SELECT * FROM userinfo where USERID = ? AND PASSWORD = ?";
    /**
     * 获取登录用户的权限
     */
    public static final String LoginRule = "SELECT * FROM userrole WHERE USERID = ?";
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
    public static final String GetDeviceByID = "SELECT * FROM device where IDDZMBH1 = ? OR IDDZMBH2 = ? OR IDDZMBH3 = ?";
    /**
     * 根据二维码获取设备信息
     */
    public static final String GetDeviceBySAOMA = "SELECT * FROM device where EWMBH = ?";
    /**
     * 根据自编码获取设备信息
     */
    public static final String GetDeviceByBH = "SELECT * FROM device where BH = ?";
    /**
     * 往一个设备中添加配件
     */
    public static final String AddDepart = "UPDATE device SET SFPJ = ? , SSSBBH = ? WHERE BH = ?";
    /**
     * 根据设备id查询其配件的列表
     */
    public static final String GetPartListByDeviceId = "SELECT BH, MC, IDDZMBH1 FROM device WHERE SFPJ = 1 AND SSSBBH = ?";
    /**
     * 绑定设备
     */
    public static final String BIND_DEVICE = "UPDATE device SET IDDZMBH1 = ?, IDDZMBH2 = ?, IDDZMBH3 = ? WHERE BH = ?";
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
    public static final String OUT_DEVICE = "INSERT INTO devicestore (SBBH, CZSJ, CZR, LB, JH, LYDW) VALUES (?, ?, ?, ?, ?, ?);";
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
            "dv.BH, dv.MC, dv.LB, dv.XH, dv.GG, leibie.TEXT AS leibie,xinghao.TEXT AS xinghao,guige.TEXT AS guige" +
            " FROM " +
            "devicesort AS leibie, devicesort AS xinghao, devicesort AS guige, device as dv" +
            " WHERE " +
            "(dv.IDDZMBH1 = ? OR dv.IDDZMBH2 = ? OR dv.IDDZMBH3 = ?)" +
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
    public static final String ADD_DEVICE_SCRAP = "INSERT INTO devicescrap (SBBH, BFSJ, BFR) VALUES (?, ?, ?)";
    /**
     * 查找报废设备
     */
    public static final String GET_SCRAP_DEVICE = "SELECT * FROM devicescrap WHERE SBBH = ?";
    /**
     * 更改设备状态
     */
    public static final String CHANGE_DEVICE_STATE = "UPDATE device SET SBZT = ? WHERE BH = ?";
    /**
     * 根据时间段 获取设备出库历史
     */
    public static final String GET_OUT_HISTORY = "SELECT " +
            "device.MC, " +
            "devicestore.SBBH, devicestore.CZSJ, devicestore.JH, devicestore.LYDW, " +
            "userinfo.USERNAME " +
            "FROM devicestore, device, userinfo WHERE " +
            "devicestore.CZSJ >=?  AND devicestore.CZSJ <=? AND devicestore.LB = 0 " +
            "AND device.BH = devicestore.SBBH AND userinfo.USERID = devicestore.CZR";
    /**
     * 获取设备详情，根据id卡卡号
     */
    public static final String GET_DEVICE_DETAIL = "SELECT " +
            "*, lb.TEXT AS leibie, xh.TEXT AS xinghao, gg.TEXT AS guige " +
            "FROM device, devicesort AS lb, devicesort as xh, devicesort as gg " +
            "WHERE (device.IDDZMBH1 = ? OR device.IDDZMBH2 = ? OR device.IDDZMBH3 = ?) " +
            "AND lb.COOD = device.LB AND xh.COOD = device.XH AND gg.COOD = device.GG";
    /**
     * 获取设备详情，根据二维码
     */
    public static final String GET_DEVICE_DETAIL_BY_SAOMA = "SELECT " +
            "*, lb.TEXT AS leibie, xh.TEXT AS xinghao, gg.TEXT AS guige " +
            "FROM device, devicesort AS lb, devicesort as xh, devicesort as gg " +
            "WHERE (device.EWMBH = ?) " +
            "AND lb.COOD = device.LB AND xh.COOD = device.XH AND gg.COOD = device.GG";
    /**
     * 查询数据库中上次是否有盘库的数据,返回上次盘库的全部数据
     */
    public static final String Get_Old_Panku = "SELECT user.USERNAME as CZR, devicepanku.*, tableibie.TEXT as leibie, " +
            "tabxinghao.TEXT AS xinghao, tabguige.TEXT AS guige FROM devicepanku, userinfo as user, " +
            "devicesort as tableibie, devicesort as tabxinghao, devicesort as tabguige " +
            "WHERE devicepanku.SFQD = 0 AND user.USERID = devicepanku.CZR " +
            "AND tableibie.COOD = devicepanku.LB AND tabxinghao.COOD = devicepanku.XH " +
            "AND tabguige.COOD = devicepanku.GG";
    /**
     * 插入盘库信息
     */
    public static final String INSERT_PANKU = "INSERT INTO " +
            "devicepanku (BH, MC, LB, XH, GG, CZR, CZSJ, SFQD) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    /**
     * 删除上次用户盘库的信息
     */
    public static final String DELET_OLD_PANKU = "DELETE FROM devicepanku WHERE SFQD = ?";
    /**
     * 更新盘库信息
     */
    public static final String UPLOAD_PANKU_DATE = "UPDATE devicepanku SET devicepanku.SFQD = 1 WHERE SFQD = 0";
    /**
     * 获取盘库列表的所有分类信息，每个类别的个数
     */
    public static final String GET_PANKU_GROUP_LIST = "SELECT lb.TEXT as LB, xh.TEXT as XH, gg.TEXT as GG, COUNT(*) as NUM FROM " +
            "devicesort AS lb, devicesort as xh, devicesort as gg, " +
            "devicepanku WHERE devicepanku.SFQD = 0 AND lb.COOD = devicepanku.LB " +
            "AND xh.COOD = devicepanku.XH AND gg.COOD = devicepanku.GG " +
            "GROUP BY LB, XH, GG";
    /**
     * 获取该设备是否盘库
     */
    public static final String DEVICE_IS_PANKU = "SELECT * FROM devicepanku WHERE SFQD = 0 AND BH = ?";
    /**
     * 获取照片的地址
     */
    public static final String Get_Image_Path = "SELECT * FROM devicedoc WHERE SBBH = ? AND LB = ?";
}
