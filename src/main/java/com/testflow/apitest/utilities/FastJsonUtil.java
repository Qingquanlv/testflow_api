package com.testflow.apitest.utilities;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.TypeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FastJsonUtil {
    private static final SerializeConfig config;

    static {
        config = new SerializeConfig();
        // 使用和json-lib兼容的日期输出格式
        config.put(java.util.Date.class, new JSONLibDataFormatSerializer());
        // 使用和json-lib兼容的日期输出格式
        config.put(java.sql.Date.class, new JSONLibDataFormatSerializer());
        TypeUtils.compatibleWithJavaBean=true;
    }

    /**
     * NULL值的字段不输出
     * @param object
     * @return
     */
    public static String toJSONStringNoNULLColumn(Object object){
        return JSON.toJSONString(object, SerializerFeature.NotWriteDefaultValue);
    }

    public static <T> T toBean(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    public static Object jsonToBean(String jsonString, Object beanCalss) {
        Object newObj = JSON.parseObject(jsonString, Object.class);
        return newObj;
    }

    public static String toJson(Object beanCalss) {
        return JSON.toJSONString(beanCalss);
    }

    /**
     * 序列化JsonList
     * @param JsonList : JSON字符串List
     * @param clazz : 序列化类
     * @return List ： 反馈序列化List
     */
    public static List<?> parseJsonListToObjList(Object JsonList, Class<?> clazz) {
        List<Object> tarObjList = new ArrayList<>();
        if (JsonList != null && JsonList.getClass() != null && JsonList.getClass().getSimpleName().equals("ArrayList")) {
            try {
                List<String> tarJsonList = (ArrayList) JsonList;
                for (String item : tarJsonList) {
                    String finalReponseJsonStr = parseDateString(item);
                    tarObjList.add(JSON.parseObject(finalReponseJsonStr, clazz));
                }
            }
            catch (Exception ex)
            {
                System.out.println(String.format("parse Json List To Object List failed.") + ex);
            }
        }
        return tarObjList;
    }

    /**
     * 反序列化JsonList
     * @param objList : JSON字符串List
     * @return List<String> ： 返回JSON序列化后的List
     */
    public static <T> List<String> parseObjListToJsonList(Object objList) {
        List<String> tarObjList = new ArrayList<String>();
        if (objList != null && objList.getClass() != null && objList.getClass().getSimpleName().equals("ArrayList")) {
            try {
                List<T> sourceObjList = (List<T>) objList;
                for (T item : sourceObjList) {
                    tarObjList.add(JSON.toJSONString(item));
                }
            }
            catch (Exception ex)
            {
                System.out.println(String.format("parse Object List To Json List failed.") + ex);
            }
        }
        return tarObjList;
    }

    /**
     * 系列化JsonList
     * @param JSONStr : JSON字符串
     * @param clazz : 序列化类
     * @return List ： 反馈序列化List
     */
    public static List<?> parseJSONToList(String JSONStr, Class<?> clazz){
        return JSON.parseArray(JSONStr, clazz);
    }

    // 转换为数组
    public static <T> Object[] toArray(String text) {
        return toArray(text, null);
    }

    // 转换为数组
    public static <T> Object[] toArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz).toArray();
    }

    // 转换为List
    public static <T> List<T> toList(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

    /**
     * 系列化JsonList
     * @param sourceString : JSON字符串List
     * @return String ： 处理后的String
     */
    public static String parseDateString(String sourceString) {
        //String pattern = "\\/Date\\(([0-9]{13})([+|-][0-9]{4})\\)\\/|\\\\/Date\\(([0-9]{13})([+-][0-9]{4})\\)\\\\\\/";
        String pattern = "\\\\/Date\\(([0-9]{13})([+-][0-9]{4})\\)\\\\\\/";
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(sourceString);

        // 循环处理正则表达式匹配项
        StringBuilder finalReponseJsonStr = new StringBuilder();
        String tempStr = "";
        int start = 0;
        while (matcher.find(start)) {
            tempStr = sourceString.substring(start, matcher.end());
            finalReponseJsonStr.append(tempStr.replaceFirst(pattern, "$1"));
            start = matcher.end();
        }
        finalReponseJsonStr.append(sourceString.substring(start, sourceString.length()));
        return finalReponseJsonStr.toString();
    }
}
