package com.jiekai.wzgl.utils.localDbUtils;


import java.util.ArrayList;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-6-11
 * Time: 上午9:32
 * To change this template use File | Settings | File Templates.
 */
public abstract class DataBaseColumn {
    public static final String[] columns = new String[]{PanKuDataListColumn.class.getName(),
            PanKuDataNumColumn.class.getName()};

    public static final Class<DataBaseColumn>[] getSubClass() {
        ArrayList<Class<DataBaseColumn>> arrayList = new ArrayList<Class<DataBaseColumn>>();
        for (String column : columns) {
            try {
                arrayList.add((Class<DataBaseColumn>) Class.forName(column));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return arrayList.toArray(new Class[0]);
    }


    public String getCreatorSql(Map<String, String> columnMap) {
        return getCreator(getTableName(), columnMap);
    }

    private String getCreator(String tableName, Map<String, String> columnMap) {
        String[] keys = (String[]) columnMap.keySet().toArray(new String[0]);
        StringBuffer buffer = new StringBuffer("CREATE TABLE " + tableName + "(");
        for (int position = 0; position < keys.length; position++) {
            buffer.append(keys[position]).append(" ").append(columnMap.get(keys[position]));
            if (position < keys.length - 1) {
                buffer.append(",");
            }
        }
        buffer.append(")");
        return buffer.toString();
    }

    /**
     * 根据数据类型创建字段存储类型
     *
     * @return
     */
    public String getFieldType(String field) {
        if ("id".equals(field)) {
            return "text primary key";//设置ID为主键
        }
        return "text";
    }

    abstract public String getTableName();

    abstract public Map<String, String> getColumnMap();
}
