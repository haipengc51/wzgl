package com.jiekai.wzglkg.utils.localDbUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by laowu on 2017/12/23.
 * 盘库的单条数据存储数据库
 */

public class PanKuDataListColumn extends DataBaseColumn {
    public final static String TABLE_NAME = "PanKuDataList";
    private Map<String, String> columnMap;

    public final static String BH = "BH";  //设备自编号
    public final static String MC = "MC";  //设备名称
    public final static String LB = "LB";  //设备类别
    public final static String XH = "XH";  //设备型号
    public final static String GG = "GG";   //设备规格
    public final static String IDDZMBH1 = "IDDZMBH1"; //ID电子码编号1

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Map<String, String> getColumnMap() {
        if(columnMap == null) {
            columnMap = new HashMap<String, String>();
        }
        columnMap.put(BH, "text");
        columnMap.put(MC, "text");
        columnMap.put(LB, "text");
        columnMap.put(XH, "text");
        columnMap.put(GG, "text");
        columnMap.put(IDDZMBH1, "text");
        return columnMap;
    }
}
