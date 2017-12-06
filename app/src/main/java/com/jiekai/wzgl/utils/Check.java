package com.jiekai.wzgl.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Robin
 * Date: 14-8-14
 * Time: 下午3:15
 * To change this template use File | Settings | File Templates.
 */
public class Check {
    /**
     * Check cellphone
     */
    public static boolean isCellphone(String cellphoneNum){
        return cellphoneNum.matches("^(13|14|15|17|18|19)\\d{9}$");
    }
    /**
     　　* 验证邮箱地址是否正确
     　　* @param email
     　　* @return
     　　*/
    public static boolean checkEmail(String email)

    {
        try {
            Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
            Matcher m = p.matcher(email);
            return m.matches();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check chinese
     * @param str
     * @return
     */
    public static boolean isFilter(String str){
        if(str.replaceAll("[a-z]*[A-Z]*\\d*-*_*\\s*", "").length()==0){
            return true;
        }else
            return false;
    }

    /**
     * Check Integer
     */
    public static boolean isNumber(String str){
       try {
          Integer.parseInt(str);
          return true;
       }catch (NumberFormatException e){
          return false;
       }
    }

    /**
     * 获取手机号
     */
    public static String getTelPhone(Context mContext){
        TelephonyManager phoneMgr=(TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String telPhone = phoneMgr.getLine1Number();
        if(telPhone == null && telPhone.length() == 0) {
            return "";
        } else {
            return telPhone.replace("+86", "");
        }
    }

    /**
     * 判断是否含有特殊字符串
     * @param str
     * @return
     */
    public static boolean isExChar(String str){
        String limitEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(limitEx);
        Matcher m = pattern.matcher(str);
        return m.find();
    }
    /**
     * 判断是否含有特殊字符串
     * @param str
     * @return
     */
    public static boolean isExCharEmail(String str){
        String limitEx="[`~!#$%^&*()+=|{}':;',\\[\\]<>/?~！#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(limitEx);
        Matcher m = pattern.matcher(str);
        return m.find();
    }
    /**
     * 判断是否含有特殊字符串
     * @param str
     * @return
     */
    public static boolean isExCharAudioPath(String str){
        String limitEx="[`~!#$%^&*()+=|{}':;',\\[\\]<>/?~！#￥%……&*（）+|{}【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(limitEx);
        Matcher m = pattern.matcher(str);
        return m.find();
    }

    /**
     * Check Integer
     */
    public static boolean isLongNumber(String str){
        try {
            Long.parseLong(str);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
}
