package com.jiekai.wzglkg.utils.localDbUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by laowu on 2017/12/23.
 * 盘库的每一类设备数量的本地数据表
 */

public class PanKuDataNumColumn extends DataBaseColumn {
    public final static String TABLE_NAME = "PanKuDataNum";
    private Map<String, String> columnMap;

    public final static String LB = "LB";  //设备类别
    public final static String XH = "XH";  //设备型号
    public final static String GG = "GG";   //设备规格
    public final static String NUM = "NUM"; //数量

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Map<String, String> getColumnMap() {
        if(columnMap == null) {
            columnMap = new HashMap<String, String>();
        }
        columnMap.put(LB, "text");
        columnMap.put(XH, "text");
        columnMap.put(GG, "text");
        columnMap.put(NUM, "text");
        return columnMap;
    }
}
