/**
 * keydom.com.cn Inc.
 * Copyright (c) 2005-2017 All Rights Reserved.
 */
package cn.com.czcb.pub;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * Json解析工具
 * @author liufei
 * @version $Id: GsonUtil.java, v 0.1 2017年7月3日 上午11:30:14 liufei Exp $
 */
public class GsonUtil {

    /**gson对象  */
    private static Gson gson = null;
    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    /**
     * 
     */
    private GsonUtil() {
    }

    /** 
     * 将object对象转成json字符串 
     *  
     * @param object 
     * @return  String
     */
    public static String gsonString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /** 
     * 将gsonString转成泛型bean 
     * @param <T> 
     *  
     * @param gsonString 
     * @param cls 
     * @return <T> Object<T>
     */
    public static <T> T gsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    /** 
     * 转成list 
     * 泛型在编译期类型被擦除导致报错 
     * @param <T> 
     * @param gsonString 
     * @param cls 
     * @return  <T> List<T>
     */
    public static <T> List<T> gsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    /** 
     * 转成list 
     * 解决泛型问题 
     * @param json 
     * @param cls 
     * @param <T> 
     * @return  <T> List<T>
     */
    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }

    /** 
     * 转成list中有map的 
     * @param <T> 
     * @param gsonString 
     * @return  <T> List<Map<String, T>>
     */
    public static <T> List<Map<String, T>> gsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        }
        return list;
    }

    /** 
     * 转成map的 
     * @param <T> 
     * @param gsonString 
     * @return <T> <Map<String, T>
     */
    public static <T> Map<String, T> gsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
}
