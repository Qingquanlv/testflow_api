package com.testflow.apitest.utilities;

import com.testflow.apitest.business.BufferManager;
import com.zf.zson.ZSON;
import com.zf.zson.result.ZsonResult;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamUtil {

    public static Pattern paramPattern = Pattern.compile("\\$\\{.*?\\}");

    /**
     * 转化参数（Xpath，Json，实体的属性值）
     *
     * @param val 带参数的请求串
     * @return String 返回转化后的参数
     */
    public static String parseJsonParam(String val)
    {
        //获取字符串中所有参数
        List<String> paramList = catchParamList(val);
        for(String param : paramList)
        {
            //转化参数为某缓存内的实体的属性值
            String paramCoverted = convertParam(param);
            String[] bufferKeyAndValue = getBufferKeyAndValue(paramCoverted);
            //从缓存中获取数据
            String jsonStr = BufferManager.getBufferByKey(bufferKeyAndValue[0]).toString();
            //获取配置的值，通过Json匹配，默认匹配第一个
            String targetStr = getMapValViaJson(jsonStr, bufferKeyAndValue[1]);
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

    /**
     * 通过Zson匹配获取Json串
     *
     * @param jsonStr : 要匹配的Json字符串
     * @param mapKey : 匹配的xpath字符串
     * @return String ：返回匹配后的Json串
     */
    private static String getMapValViaJson(String jsonStr, String mapKey)
    {
        ZsonResult zr = ZSON.parseJson(jsonStr);
        zr.getClassTypes();
        List<Object> names = zr.getValues(mapKey);
        return names.get(0).toString();
    }

    private static String updateParameStr(String sourceStr, String matchMapKey, String matchMapVal)
    {
        sourceStr = sourceStr.replace(matchMapKey, matchMapVal);
        return sourceStr;
    }
}
