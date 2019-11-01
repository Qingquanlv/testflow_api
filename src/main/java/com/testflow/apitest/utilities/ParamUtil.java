package com.testflow.apitest.utilities;

import com.testflow.apitest.business.BufferManager;
import com.testflow.apitest.servicesaccess.ServiceAccess;
import com.zf.zson.ZSON;
import com.zf.zson.result.ZsonResult;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
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
    public static Pattern paramPatternNoXPath = Pattern.compile("\\$P\\{.*?\\}");
    public static Pattern listParamPattern = Pattern.compile("\\$L\\{.*?\\}");
    public static Pattern mapPatternStr = Pattern.compile("^\\{(.*?)\\}$");

    public static String patternStr = ".*\\$\\{(.*?)\\}.*";
    public static String listPatternStr = ".*\\$L\\{(.*?)\\}.*";
    public static String patternStrNoXPath = ".*\\$P\\{(.*?)\\}.*";

    /**
     * 根据匹配Josn，构建多个的请求
     *
     * @param val : 请求参数模板
     * @return String ：返回请求List
     */
    public static List<String> parseParamList(String val) throws Exception
    {
        val = parseEnter(val);
        //List情况requst只能存在一个参数, 若为多个默认取第一个
        List<String> paramList = catchParamList(listParamPattern, val);
        List<String> reqList = new ArrayList<>();
        for (String param : paramList) {
            if (!paramList.isEmpty()) {
                //去掉参数中的大括号
                String paramCoverted = convertParam(listPatternStr, param);
                String[] bufferKeyAndValue = getBufferKeyAndValue(paramCoverted);
                //从缓存中获取数据
                String str = BufferManager.getBufferByKey(bufferKeyAndValue[0]);
                List<Object> objList = getMapValFromStr(str, bufferKeyAndValue[1]);
                if (null == objList || objList.isEmpty()) {
                    System.out.println(String.format("No matiched value for key \"%s\" Json string \"%s\" .", bufferKeyAndValue[1], str));
                }
                else {
                    int i = 0;
                    for (Object value : objList) {
                        if (reqList.size() > i) {
                            updateParameStr(value.toString(), param, value.toString());
                        }
                        else {
                            reqList.add(updateParameStr(val, param, objList.get(i).toString()));
                        }
                        i++;
                    }
                }
            }
        }
        return reqList;
    }

    /**
     * 转化字符串中参数
     *
     * @param val
     * @return
     * @throws Exception
     */
    public static String parseParam(String val) throws Exception
    {
        val = parseParamXpath(val);
        val = parseParamNoXpath(val);
        val = parseEnter(val);
        return val;
    }

    /**
     * 转化字符串中Xpath类型参数
     *
     * @param val
     * @return
     * @throws Exception
     */
    public static String parseParamXpath(String val) throws Exception
    {
        //获取字符串中所有参数
        List<String> paramList = catchParamList(paramPattern, val);
        for(String param : paramList)
        {
            //转化参数为某缓存内的实体的属性值
            String paramCoverted = convertParam(patternStr, param);
            String[] bufferKeyAndValue = getBufferKeyAndValue(paramCoverted);
            //从缓存中获取数据
            String str = BufferManager.getBufferByKey(bufferKeyAndValue[0]);
            List<Object> objList = getMapValFromStr(str, bufferKeyAndValue[1]);
            if (null == objList || objList.isEmpty()) {
                throw new Exception(String.format("No matiched value for key \"%s\" Json string \"%s\" .", bufferKeyAndValue[1], str));
            }
            else {
                val = updateParameStr(val, param, objList.get(0).toString());
            }
        }
        return val;
    }

    /**
     * 转化字符串中非Xpath类型参数
     *
     * @param val
     * @return
     * @throws Exception
     */
    public static String parseParamNoXpath(String val) throws Exception
    {
        //获取字符串中所有参数
        List<String> paramList = catchParamList(paramPatternNoXPath, val);
        for(String param : paramList)
        {
            //转化参数为某缓存内的实体的属性值
            String paramCoverted = convertParam(patternStrNoXPath, param);
            String str = BufferManager.getBufferByKey(paramCoverted);
            if (paramCoverted==null || paramCoverted.length() == 0) {
                throw new Exception(String.format("No matiched value for key \"%s\"  string \"%s\" .", param, str));
            }
            else {
                val = updateParameStr(val, param, str);
            }
        }
        return val;
    }

    /**
     * 去除字符串中的换行符
     *
     * @param val
     * @return
     * @throws Exception
     */
    public static String parseEnter(String val) throws Exception
    {
        return val.replace("\n", "").replace("\r", "").replace("\t", "");
    }

    /**
     * 根据正则匹配，获取参数值
     *
     * @param value : 参数字符串
     * @return String ：返回参数
     */
    private static String convertParam(String pattern, String value)
    {
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
    private static List<String> catchParamList(Pattern pattern, String value){
        Matcher matcher = pattern.matcher(value);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }

    /**
     * 获取字符串中的参数值
     *
     * @param value : 参数字符串
     * @return List<String> ：返回匹配的所有参数
     */
    private static String catchParamMap(String value){
        Matcher matcher = mapPatternStr.matcher(value);
        matcher.find();
        return matcher.group(1);
    }

    /**
     * 更新字符串中的参数字符串
     *
     * @param sourceStr
     * @param matchMapKey
     * @param matchMapVal
     * @return
     */
    private static String updateParameStr(String sourceStr, String matchMapKey, String matchMapVal)
    {
        sourceStr = sourceStr.replace(matchMapKey, matchMapVal);
        return sourceStr;
    }

    /**
     * 通过Xpath匹配获取String串
     *
     * @param str
     * @param mapKey
     * @return
     */
    public static List<Object> getMapValFromStr(String str, String mapKey)
    {
        List<Object> objlist;
        try {
            objlist = getMapValFromJson(str, mapKey);
        }
        catch (Exception e)
        {
            try {
                objlist = getMapValFromXMl(str, mapKey);
            }
            catch (Exception ex) {
                throw new RuntimeException(String.format("\"%s\" is not a legal string.", mapKey));
            }
        }
        if (null != objlist) {
            objlist.stream().forEach(item->item.toString().trim());
        }
        return objlist;
    }


    /**
     * 通过Zson匹配获取Json串
     *
     * @param jsonStr : 要匹配的Json字符串
     * @param mapKey : 匹配的xpath字符串
     * @return String ：返回匹配后的Json串
     */
    public static List<Object> getMapValFromJson(String jsonStr, String mapKey)
    {
        ZsonResult zr = ZSON.parseJson(jsonStr);
        zr.getClassTypes();
        List<Object> names = zr.getValues(mapKey);
        return names;
    }

    /**
     * 通过Dom4J匹配获取XML串
     *
     * @param xmlStr : 要匹配的xml字符串
     * @param mapKey : 匹配的xpath字符串
     * @return  ：返回匹配后的xml串
     */
    public static List<Object> getMapValFromXMl(String xmlStr, String mapKey)
    {
        List<Object> objlist = new ArrayList<>();
        try {
            Document doc = DocumentHelper.parseText(xmlStr);
            List<Element> list = doc.selectNodes(mapKey);

            for (Element ele : list) {
                objlist.add(ele.getText());
            }
        } catch (Exception e) {
            throw new RuntimeException("这不是一个合法的XML串!" + e);
        }
        return objlist;

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
            if (!"".equals(key)) {
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

    public static Map<String, String> parseMapParam(String val)
    {
        Map<String, String> map = new HashMap<>();
        val = catchParamMap(val);
        //获取字符串中所有参数
        String[] keyArray = val.split(",");
        if (!"".equals(keyArray[0])) {
            for (String key : keyArray) {
                String[] mapKeyArray = key.split(":");
                map.put(mapKeyArray[0].trim(), mapKeyArray[1].trim());
            }
        }
        return map;
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

    /**
     *
     * 获取List中类型
     *
     * @param value List类型
     * @return
     */
    private static String getListItemType(String value)
    {
        String targetStr;
        String pattern = "List<(.*)>";
        Pattern r = Pattern.compile(pattern);
        Matcher ma = r.matcher(value);
        boolean rs = ma.find();
        Pattern.matches(pattern, value);
        targetStr = ma.group(1);
        return targetStr;
    }

    public static Object getParameType(String parame, String parameType) throws Exception {
        String patten = "list<";
        Object sourceParame;
        Class<?> paramTypeClazz;
        if (parameType.toLowerCase().contains(patten)) {
            paramTypeClazz = ServiceAccess.reflectClazz(getListItemType(parameType));
            try {
                sourceParame = FastJsonUtil.toListBean(BufferManager.getBufferByKey(parame), paramTypeClazz);
            }
            catch (Exception ex) {
                System.out.println(ex);
                sourceParame = JAXBUtil.formXMLList(BufferManager.getBufferByKey(parame), paramTypeClazz);
            }
        }
        else {
            paramTypeClazz = ServiceAccess.reflectClazz(parameType);
            sourceParame = FastJsonUtil.toBean(BufferManager.getBufferByKey(parame), paramTypeClazz);
        }
        return sourceParame;
    }

    public static String parseJsonDate(String str) {
        String pattern ="\\\\?\\/Date\\(([\\+\\-]?[0-9]{12,15})[\\+\\-][0-9]{4}\\)\\\\?\\/";
        Pattern P = Pattern.compile(pattern);
        Matcher matcher = P.matcher(str);

        StringBuilder jsonStr = new StringBuilder();
        String tempStr = "";
        int start = 0;
        while (matcher.find(start)) {
            tempStr = str.substring(start, matcher.end());
            jsonStr.append(tempStr.replaceFirst(pattern, "$1"));
            start = matcher.end();
        }
        jsonStr.append(str.substring(start, str.length()));
        return jsonStr.toString();
    }

}
