package com.testflow.apitest.stepdefinations;

import com.testflow.apitest.business.BufferManager;
import com.testflow.apitest.common.Constants;
import com.testflow.apitest.parser.DataParser;
import com.testflow.apitest.servicesaccess.JavaStringCompiler;
import com.testflow.apitest.servicesaccess.ServiceAccess;
import com.testflow.apitest.utilities.FastJsonUtil;
import com.testflow.apitest.utilities.JAXBUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    /**
     * 根据Buffer中保存的实体，构建实体Json
     *
     */
    public String parseValueViaFile(String convertFileName, String convertMethodName, String sourceData1, String sourceDataParamType1, String sourceData2, String sourceDataParamType2, String sourceData3, String sourceDataParamType3 , String sourceData4, String sourceDataParamType4) throws Exception
    {
        Class<?> clazz = ServiceAccess.reflectClazz(convertFileName);
        Object sourceParam1 = getParameType(sourceData1, sourceDataParamType1);
        Class<?> sourceParamReflectType1 = getParameReflectType(sourceDataParamType1);
        Object sourceParam2 = getParameType(sourceData2, sourceDataParamType2);
        Class<?> sourceParamReflectType2 = getParameReflectType(sourceDataParamType2);
        Object sourceParam3 = getParameType(sourceData3, sourceDataParamType3);
        Class<?> sourceParamReflectType3 = getParameReflectType(sourceDataParamType3);
        Object sourceParam4 = getParameType(sourceData4, sourceDataParamType4);
        Class<?> sourceParamReflectType4 = getParameReflectType(sourceDataParamType4);
        Method fieldGetMet =  ServiceAccess.reflectMethod(clazz, convertMethodName, sourceParamReflectType1, sourceParamReflectType2, sourceParamReflectType3, sourceParamReflectType4);
        String targetJson = FastJsonUtil.toJson(fieldGetMet.invoke(clazz.newInstance(), sourceParam1, sourceParam2, sourceParam3, sourceParam4));
        return targetJson;
    }

    /**
     * 根据Buffer中保存的实体，构建实体Json
     *
     */
    public String parseValueViaFile(String convertFileName, String convertMethodName, String sourceData1, String sourceDataParamType1, String sourceData2, String sourceDataParamType2, String sourceData3, String sourceDataParamType3) throws Exception
    {
        Class<?> clazz = ServiceAccess.reflectClazz(convertFileName);
        Object sourceParam1 = getParameType(sourceData1, sourceDataParamType1);
        Class<?> sourceParamReflectType1 = getParameReflectType(sourceDataParamType1);
        Object sourceParam2 = getParameType(sourceData2, sourceDataParamType2);
        Class<?> sourceParamReflectType2 = getParameReflectType(sourceDataParamType2);
        Object sourceParam3 = getParameType(sourceData3, sourceDataParamType3);
        Class<?> sourceParamReflectType3 = getParameReflectType(sourceDataParamType3);
        Method fieldGetMet =  ServiceAccess.reflectMethod(clazz, convertMethodName, sourceParamReflectType1, sourceParamReflectType2, sourceParamReflectType3);
        String targetJson = FastJsonUtil.toJson(fieldGetMet.invoke(clazz.newInstance(), sourceParam1, sourceParam2, sourceParam3));
        return targetJson;
    }

    /**
     * 根据Buffer中保存的实体，构建实体Json
     *
     */
    public String parseValueViaFile(String convertFileName, String convertMethodName, String sourceData1, String sourceDataParamType1, String sourceData2, String sourceDataParamType2) throws Exception
    {
        Class<?> clazz = ServiceAccess.reflectClazz(convertFileName);
        Object sourceParam1 = getParameType(sourceData1, sourceDataParamType1);
        Class<?> sourceParamReflectType1 = getParameReflectType(sourceDataParamType1);
        Object sourceParam2 = getParameType(sourceData2, sourceDataParamType2);
        Class<?> sourceParamReflectType2 = getParameReflectType(sourceDataParamType2);
        Method fieldGetMet =  ServiceAccess.reflectMethod(clazz, convertMethodName, sourceParamReflectType1, sourceParamReflectType2);
        String targetJson = FastJsonUtil.toJson(fieldGetMet.invoke(clazz.newInstance(), sourceParam1, sourceParam2));
        return targetJson;
    }

    /**
     * 根据Buffer中保存的实体，构建实体Json
     *
     */
    public String parseValueViaFile(String convertFileName, String convertMethodName, String sourceData, String sourceDataParamType) throws Exception
    {
        Class<?> clazz = ServiceAccess.reflectClazz(convertFileName);
        Object sourceParam = getParameType(sourceData, sourceDataParamType);
        Class<?> sourceParamReflectType1 = getParameReflectType(sourceDataParamType);
        Method fieldGetMet =  ServiceAccess.reflectMethod(clazz, convertMethodName, sourceParamReflectType1);
        String targetJson = FastJsonUtil.toJson(fieldGetMet.invoke(clazz.newInstance(), sourceParam));
        return targetJson;
    }

    /**
     * 根据Buffer中保存的str,得出新的str
     *
     */
    public String parseValueViaFile(String convertFileName, String convertMethodName, String sourceData) throws Exception
    {
        Class<?> clazz = ServiceAccess.reflectClazz(convertFileName);
        String sourceParame = BufferManager.getBufferByKey(sourceData);
        Method fieldGetMet =  ServiceAccess.reflectMethod(clazz, convertMethodName, String.class);
        String targetStr = fieldGetMet.invoke(clazz.newInstance(), sourceParame).toString();
        return targetStr;
    }

    /**
     * 根据Buffer中保存的实体，构建实体Json
     *
     */
    public String parseValueVidStr(String convertMethodSource, String convertMethodName, String sourceData, String sourceDataParamType, String targetDataParamType) throws Exception
    {
        String targetJson = "";
        //初始化JavaCompliler(生成文件)
        JavaStringCompiler compiler = new JavaStringCompiler();
        //获取需要转化的类名和函数名
        String className = Constants.PARSE_VALUE_FILE_NAME;
        try {
            Class<?> sourceDataParamTypeClazz = ServiceAccess.reflectClazz(sourceDataParamType);
            Object sourceParame = FastJsonUtil.toBean(BufferManager.getBufferByKey(sourceData), sourceDataParamTypeClazz);
            String convertFileSource = Constants.PARSE_VALUE_FILE_SOURCE.replace(Constants.PARAMTYPE1, sourceDataParamType)
                    .replace(Constants.PARAMTYPE2, targetDataParamType)
                    .replace(Constants.METHOD, convertMethodSource);
            Map<String, byte[]> results = compiler.compile(className, convertFileSource);
            Class<?> clazz = compiler.loadClass(Constants.SERVICES_CLASS_PATH, results);
            Method fieldGetMet =  ServiceAccess.reflectMethod(clazz, convertMethodName, sourceDataParamTypeClazz);
            targetJson = FastJsonUtil.toJson(fieldGetMet.invoke(clazz.newInstance(), sourceParame));
        } catch (Exception e) {
            System.out.println("执行Convert方法错误" + e);
        }
        return targetJson;
    }


    /**
     * 根据重新方法，构建实体Json
     *
     */
    public String parseValueVidMethod(String sourceParemType, String sourceParemKey, DataParser dataParser) throws Exception
    {
        Object sourceParam1 = getParameType(sourceParemKey, sourceParemType);
        Object tarObj = dataParser.parse(sourceParam1);
        String tarStr = FastJsonUtil.toJson(tarObj);
        return tarStr;
    }

    /**
     * 根据重新方法，构建实体Json
     *
     */
    public String parseValueVidMethod(String sourceParemKey, DataParser dataParser) throws Exception
    {
        String srcStr = BufferManager.getBufferByKey(sourceParemKey);
        String tarStr = dataParser.parse(srcStr).toString();
        return tarStr;
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

    public Object getParameType(String parame, String parameType) throws Exception {
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

    public Class<?> getParameReflectType(String parame) throws Exception {
        String patten = "list<";
        Class<?> paramTypeClazz;
        if (parame.toLowerCase().contains(patten)) {
            paramTypeClazz = java.util.List.class;
        }
        else {
            paramTypeClazz = ServiceAccess.reflectClazz(parame);
        }
        return paramTypeClazz;
    }
}
