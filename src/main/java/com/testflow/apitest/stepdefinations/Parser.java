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
    public void convertSourceToTarget(String convertFileName, String convertFileSource, String convertMethodName, Object sourceData, Class<?> sourceDataParamType, Class<?> targetDataParamType)
    {
        Object targetObj = new Object();
        //初始化JavaCompliler(生成文件)
        JavaStringCompiler compiler = new JavaStringCompiler();
        //获取需要转化的类名和函数名
        String className = Constants.SERVICES_PATH + convertFileName + ".jar";
        try {
            Map<String, byte[]> results = compiler.compile(className, convertFileSource);
            Class<?> clazz = compiler.loadClass(className, results);
            Method fieldGetMet =  ServiceAccess.getObjMethod(clazz, convertMethodName, sourceDataParamType, targetDataParamType);
            targetObj = fieldGetMet.invoke(clazz.newInstance(), sourceData);
        } catch (Exception e) {
            System.out.println("执行Convert方法错误" + e);
        }
        BufferManager.addBufferByKey(targetDataParamType.toString(), targetObj);
    }

    /**
     * 根据转化参数类型，转换实体
     *
     */
    private Object getBufferViaKEY(String sourceDataType, String sourceDataParamType, String sourceData) {
        Object sourceBean = BufferManager.getBufferByKey(sourceData);
        try {
            sourceBean = FastJsonUtil.jsonToBean(sourceBean.toString(), sourceBean);
        } catch (Exception ex) {
            System.out.println("Convert Data List To Entity List Failed" + ex);
        }
        return sourceBean;
    }
}
