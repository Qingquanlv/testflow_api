package com.testflow.apitest.stepdefinations;

import com.testflow.apitest.business.BufferManager;
import com.testflow.apitest.common.Constants;
import com.testflow.apitest.servicesaccess.JavaStringCompiler;
import com.testflow.apitest.servicesaccess.ServiceAccess;
import com.testflow.apitest.utilities.FastJsonUtil;

import java.lang.reflect.Method;
import java.util.Map;

public class Parser {
    /**
     * 根据Buffer中保存的实体，构建实体Json
     *
     */
    public void parseValueViaFile(String convertFileName, String convertMethodName, String sourceData, String sourceDataParamType, String targetDataParamType)
    {
        String targetJson = "";
        try {
            Class<?> clazz = ServiceAccess.reflectClazz(convertFileName);
            Class<?> sourceDataParamTypeClazz = ServiceAccess.reflectClazz(sourceDataParamType);
            Object sourceParame = FastJsonUtil.toBean(BufferManager.getBufferByKey(sourceData), sourceDataParamTypeClazz);
            Method fieldGetMet =  ServiceAccess.reflectMethod(clazz, convertMethodName, sourceDataParamTypeClazz);
            targetJson = FastJsonUtil.toJson(fieldGetMet.invoke(clazz.newInstance(), sourceParame));
        } catch (Exception e) {
            System.out.println("执行Convert方法错误" + e);
        }
        BufferManager.addBufferByKey(targetDataParamType, targetJson);
    }

    /**
     * 根据Buffer中保存的实体，构建实体Json
     *
     */
    public void parseValueVidStr(String convertMethodSource, String convertMethodName, String sourceData, String sourceDataParamType, String targetDataParamType)
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
        BufferManager.addBufferByKey(targetDataParamType, targetJson);
    }
}
