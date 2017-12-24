package com.jiekai.wzgl.utils.localDbUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Field;
import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: laowu
 * Date: 14-6-11
 * Time: 上午8:56
 * To change this template use File | Settings | File Templates.
 */
public class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private static final String DB_NAME = "wzgl";
    private static final int DB_VERSION = 1;
    public static DBHelper dbHelper;
    public Context mContext;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
    }

    public static DBHelper getInstance(Context mContext) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(mContext, DB_NAME, null, DB_VERSION);
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.db = sqLiteDatabase;
        buildTables("", sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        buildTables("DROP TABLE IF EXISTS", sqLiteDatabase);
        onCreate(sqLiteDatabase);
    }

    public void execSQL(String sql) {
        if (db == null) {
            db = getWritableDatabase();
        }
        db.execSQL(sql);
    }

    public Cursor rawQuery(String sql, String[] args) {
        if (db == null) {
            db = getWritableDatabase();
        }
        return db.rawQuery(sql, args);
    }

    public long insertSql(String tableName, ContentValues values) {
        if (db == null) {
            db = getWritableDatabase();
        }
        return db.insert(tableName, null, values);
    }

    public int update(String tableName, ContentValues values, String whereClause, String[] whereArgs) {
        if (db == null) {
            db = getWritableDatabase();
        }
        return db.update(tableName, values, whereClause, whereArgs);
    }

    public int delete(String tableName, String whereClaus, String[] whereArgs) {
        if (db == null) {
            db = getWritableDatabase();
        }
        return db.delete(tableName, whereClaus, whereArgs);
    }

    private void buildTables(String action, SQLiteDatabase db) {
        Class<DataBaseColumn>[] dataBaseColumns = DataBaseColumn.getSubClass();
        for (Class<DataBaseColumn> columnClass : dataBaseColumns) {
            try {
                DataBaseColumn column = columnClass.newInstance();
                if ("".equals(action)) {
                    db.execSQL(column.getCreatorSql(column.getColumnMap()));
                } else {
                    db.execSQL(action + " " + column.getTableName());
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        };
    }


    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
                        String orderBy) {
        if (db == null) {
            db = getWritableDatabase();
        }
        return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
                        String orderBy, String limit) {
        if (db == null) {
            db = getWritableDatabase();
        }
        return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    @Override
    public synchronized void close() {
        super.close();
        dbHelper = null;
    }

    /**
     * 解析行政区域XML
     * @throws Exception
     */
//    private void prefetchData() throws Exception {
//        InputStream is = mContext.getAssets().open("china.xml");
//        OperateXml operateXml = new OperateXml();
//        List<Province> provinceList = operateXml.parse(is);
//        if(provinceList != null && provinceList.size() != 0){
//            for(Province province : provinceList){
//                province.setId(UUIDFactory.random());
//                province.setParentsId("86");
//                List<City> cityList = province.getCityList();
//                for(City city : cityList){
//                    List<County> countyList = city.getCountyList();
//                    city.setId(UUIDFactory.random());
//                    city.setParentsId(province.getId());
//                    for(County county : countyList){
//                        county.setId(UUIDFactory.random());
//                        county.setParentsId(city.getId());
//                        insertSql("county",county.getContentVales());
//                    }
//                    insertSql("city",city.getContentValues());
//                }
//                insertSql("province",province.getContentValues());
//            }
//        }
//    }


    public <T> List<T> selectAll(String sql, Class<T> clazz, String[] params) throws SQLiteException {
        List<T> list = new ArrayList<T>();
        Cursor cursor =  dbHelper.rawQuery(sql, params);
        while (cursor.moveToNext()) {
            try {
                T t = clazz.newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for(Field field : fields) {
                    if (!field.isSynthetic() && !field.getName().equals("serialVersionUID")) {
                        field.setAccessible(true);
                        if (List.class.getSimpleName().equals(field.getType().getSimpleName())) {
                            continue;
                        }
                        try {
                            field.set(t, cursor.getString(cursor.getColumnIndex(field.getName())));
                        } catch (IllegalAccessException e) {
                            //can not happen field.setAccessible(true);
                        }
                    }
                }
                list.add(t);
            } catch (InstantiationException e) {
                throw new IllegalArgumentException(e);
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return list;
    }

}
