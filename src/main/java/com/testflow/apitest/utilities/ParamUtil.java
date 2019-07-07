package com.testflow.apitest.utilities;

import com.alibaba.fastjson.JSON;
import com.testflow.apitest.business.BufferManager;
import com.testflow.apitest.servicesaccess.ServiceAccess;
import com.zf.zson.ZSON;
import com.zf.zson.result.ZsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author qq.lv
 * @date 2019/6/22
 */
public class ParamUtil {
    private static Logger logger = LoggerFactory.getLogger(ParamUtil.class);
    public static Pattern paramPattern = Pattern.compile("\\$\\{.*?\\}");

    public static String parseParam(String val)
    {
        //获取字符串中所有参数
        List<String> paramList = catchParamList(val);
        for(String param : paramList)
        {
            //转化参数为某缓存内的实体的属性值
            String paramCoverted = convertParam(param);
            String[] bufferKeyAndValue = getBufferKeyAndValue(paramCoverted);
            //从缓存中获取数据
            Object obj = BufferManager.getBufferByKey(bufferKeyAndValue[0]);
            if (! (obj instanceof String)) {
                obj = FastJsonUtil.toJson(obj);
            }
            String targetStr = getMapValFromJson(obj.toString(), bufferKeyAndValue[1]);
            val = updateParameStr(val, param, targetStr);
        }
        return val;
    }

    /**
     * 根据正则匹配，获取参数值
     *
     * @param value : 参数字符串
     * @return String ：返回参数
     */
    private static String convertParam(String value)
    {
        String pattern = ".*\\$\\{(.*?)\\}.*";
        Pattern r = Pattern.compile(pattern);
        Matcher ma = r.matcher(value);
        boolean rs = ma.find();
        if (Pattern.matches(pattern, value)) {
            value = ma.group(1);
        }
        return value;
    }

    /**
     * 根据字符串格式XX：XX，拆分出XX，XX
     *
     * @param param : 参数字符串
     * @return String[] ：返回key，value string数组
     */
    private static String[] getBufferKeyAndValue(String param)
    {
        String[] bufferKeyAndValue = param.split(":");
        if(bufferKeyAndValue.length != 2)
        {
            System.out.print(String.format("param \"%s\" type is not invalid.", param));
        }
        return bufferKeyAndValue;
    }

    /**
     * 获取字符串中的参数值
     *
     * @param value : 参数字符串
     * @return List<String> ：返回匹配的所有参数
     */
    private static List<String> catchParamList(String value){
        Matcher matcher = paramPattern.matcher(value);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }

    private static String updateParameStr(String sourceStr, String matchMapKey, String matchMapVal)
    {
        sourceStr = sourceStr.replace(matchMapKey, matchMapVal);
        return sourceStr;
    }

    /**
     * 通过Zson匹配获取Json串
     *
     * @param jsonStr : 要匹配的Json字符串
     * @param mapKey : 匹配的xpath字符串
     * @return String ：返回匹配后的Json串
     */
    public static String getMapValFromJson(String jsonStr, String mapKey)
    {
        ZsonResult zr = ZSON.parseJson(jsonStr);
        zr.getClassTypes();
        List<Object> names = zr.getValues(mapKey);
        return names.get(0).toString();
    }

    /**
     * 通过A:B:C格式匹配实体
     *
     * @param obj : 要匹配的对象实体
     * @param mapKey : 匹配的mapKey字符串
     * @return Object ：返回匹配后的实例
     */
    private static Object getMapValFromInstance(Object obj, String mapKey)
    {
        String[] keyList = mapKey.split("\\/");
        for (String key : keyList) {
            if (!"".equals(key) || null != key) {
                if ("".equals(isListItem(key))) {}
                Method fieldSetMet = getValueViaGetMet(key, obj);
                obj = ServiceAccess.execMethod(obj, fieldSetMet);
            }
        }
        return obj;
    }

    /**
     * 判断是否存在某属性的 get方法
     *
     * @param fieldSetName : 需要获取方法对应的属性
     * @param obj ： 需要获取方法对应的实例
     * @return Method 返回get方法字节码
     */
    private static Method getValueViaGetMet(String fieldSetName, Object obj) {
        fieldSetName = parGetName(fieldSetName);
        Method[] methods = ServiceAccess.reflectDeclaredMethod(obj);
        Method fieldSetMet = getGetMet(methods, fieldSetName);
        return fieldSetMet;
    }

    /**
     * 获取某属性的 get方法
     *
     * @param methods
     * @param fieldGetMet
     * @return boolean
     */
    private static Method getGetMet(Method[] methods, String fieldGetMet) {
        Method tarMet = null;
        for (Method met : methods) {
            if (fieldGetMet.equals(met.getName())) {
                tarMet = met;
            }
        }
        if (tarMet == null)
        {
            logger.info(String.format("Get method \"%s\" fieldGetMet failed, please check if method \"%s\" is exist.", fieldGetMet, fieldGetMet));
        }
        return tarMet;
    }

    /**
     * 拼接属性的 get方法
     *
     * @param fieldName
     * @return String
     */
    private static String parGetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_') {
            startIndex = 1;
        }
        return "get"
                + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }

    private static String isListItem(String value)
    {
        String pattern = "\\[(.*)\\]";
        Pattern r = Pattern.compile(pattern);
        Matcher ma = r.matcher(value);
        boolean rs = ma.find();
        if (Pattern.matches(pattern, value))
            value = ma.group(1);
        return value;
    }

    public static Map<String, List<String>> parseVerifyParam(String val)
    {
        Map<String, List<String>> map = new HashMap<>();
        val = val.replaceAll("\\s*", "");
        //获取字符串中所有参数
        String[] keyArray = val.split("\\}\\,");
        if (!"".equals(keyArray[0])) {
            for (String key : keyArray) {
                String[] mapKeyArray = key.split("\\:\\{");
                String[] mapItemArray = mapKeyArray[1].replace("}", "").split("\\,");
                ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(mapItemArray));
                map.put(mapKeyArray[0], arrayList);
            }
        }
        return map;
    }

}
