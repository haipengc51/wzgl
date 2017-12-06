package com.jiekai.wzgl.utils;


import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-4-24
 * Time: 上午9:54
 * To change this template use File | Settings | File Templates.
 */
public class JSONHelper {
    public final static Gson gson = new Gson();


    public static String toJSONString(Object o){
        String json = null;
        try {
            json = gson.toJson(o);
        }catch (Exception e){
            e.printStackTrace();
        }
       return json;
    }

    public static <T> T fromJSONObject(String json, Type type){
        T t = null;
        try {
            t = gson.fromJson(json,type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return t;
    }

    public static <T> T fromJSONObject(String json, Class<T> cls){
        T t = null;
        try {
            t = gson.fromJson(json,cls);
        }catch (Exception e){
            e.printStackTrace();
        }
      return t;
    }


}
