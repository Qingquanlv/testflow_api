package com.testflow.apitest.servicesaccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ServiceAccess {

    private static Logger logger = LoggerFactory.getLogger(ServiceAccess.class);
//反射字节码

    /**
     * 根据类名获取类字节码
     *
     * @param objectName 反射类名
     * @return Class<?> 返回类Class
     */
    public static Class<?> reflectClazz(String objectName)  {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(objectName);
        }
        catch (Exception e) {
            logger.debug(String.format("Init object: create class \"%s\" failed.", objectName) + e);
        }
        return clazz;
    }

    //反射类

    /**
     * 根据类名初始化无参数实例
     *
     * @param objectName : 反射类名
     * @return Object : 返回实例化实体
     */
    public static Object createObject(String objectName)  {
        Object targerObj = null;
        try {
            Class<?> clazz = Class.forName(objectName);
            targerObj = clazz.newInstance();
        }
        catch (Exception e) {
            logger.debug(String.format("Init object: create object \"%s\" failed.", objectName) + e);
        }
        return targerObj;
    }

    /**
     * 根据类名初始化只有一个参数的实例
     *
     * @param objectName : 反射类名
     * @param param : 构造函数参数
     * @return Object ：返回实例化实体
     */
    public static Object createObject(String objectName, String param)  {
        Object targerObj = null;
        try {
            Class<?> clazz = Class.forName(objectName);
            Constructor cons = clazz.getDeclaredConstructor(String.class);
            cons.setAccessible(true);
            // 为构造方法传递参数
            targerObj = cons.newInstance(param);
        }
        catch (Exception e) {
            logger.warn(String.format("Init object: create object \"%s\" with \"%s\" failed.", objectName, param) + e);
        }
        return targerObj;
    }

    //反射方法


    /**
     * 根据类实例获取所有方法名
     *
     * @param bean : 类实例
     * @return Method : 方法字节码集合
     */
    public static Method[] getDeclaredMethod(Object bean)  {
        Method[] methods = null;
        try {
            methods = bean.getClass().getDeclaredMethods();
        }
        catch (Exception e) {
            logger.warn(String.format("Init object: Get object \"%s\" declared methods \"%s\" failed.", bean) + e);
        }
        return methods;
    }

    /**
     * 根据类实例和方法名，获取方法
     *
     * @param bean : 类实例
     * @param methodName : 要反射的类方法
     * @return Method : 方法字节码
     */
    public static Method getObjMethod(Object bean, String methodName)  {
        Class<?> clazz = bean.getClass();
        Method method = null;
        try {
            method = clazz.getMethod(methodName);
        }
        catch (Exception e) {
            logger.warn(String.format("Init object: Get object \"%s\" method \"%s\" failed.", bean, methodName) + e);
        }
        return method;
    }

    /**
     * 根据类实例和方法名和方法形参，获取方法
     *
     * @param bean : 类实例
     * @param methodName : 要反射的类方法
     * @return Method : 方法字节码
     */
    public static Method getObjMethod(Object bean, String methodName, String firstParam)  {
        Class<?> clazz = bean.getClass();
        Method method = null;
        try {
            method = clazz.getMethod(methodName, firstParam.getClass());
        }
        catch (Exception e) {
            logger.warn(String.format("Init object: Get object \"%s\" method \"%s\" failed.", bean, methodName) + e);
        }
        return method;
    }

    /**
     * 根据类实例和方法名和方法的两个形参，获取方法
     *
     * @param bean : 类实例
     * @param methodName : 要反射的类方法
     * @return Method : 方法字节码
     */
    public static Method getObjMethod(Object bean, String methodName, Class<?> firstParamType, Class<?> secondParamType)  {
        Class<?> clazz = bean.getClass();
        Method method = null;
        try {
            method = clazz.getMethod(methodName, firstParamType, secondParamType);
        }
        catch (Exception ex) {
            logger.warn(String.format("Get object %s method %s failed.", bean, method) + ex);
        }
        return method;
    }

    /**
     * 根据类实例和方法名和方法的四个形参，获取方法
     *
     * @param bean : 类实例
     * @param methodName : 要反射的类方法
     * @return Method : 方法字节码
     */
    public static Method getObjMethod(Object bean, String methodName, String firstParam, String secondParam, String thirdParam, String fourthParam)  {
        Class<?> clazz = bean.getClass();
        Method method = null;
        try {
            method = clazz.getMethod(methodName, firstParam.getClass(), secondParam.getClass(), thirdParam.getClass(), fourthParam.getClass());
        }
        catch (Exception ex) {
            logger.warn(String.format("Get object %s method %s failed.", bean, method) + ex);
        }
        return method;
    }

    /**
     * 根据类实例和方法名，执行方法
     *
     * @param bean : 类实例
     * @param method : 要反射的类方法
     * @return Object : 返回实体
     */
    public static Object execMethod(Object bean, Method method) {
        Object v = null;
        try {
            v = method.invoke(bean);
        }
        catch (Exception ex)
        {
            logger.warn(String.format("Exec object %s method %s  failed.", bean, method) + ex);
        }
        return v;
    }

    //反射属性

    /**
     * 根据实例获取所有属性
     *
     * @param bean : 类实例
     * @return Field[] : 返回所有属性
     */
    public static Field[] getDeclaredFields(Object bean)  {
        Class<?> cls = bean.getClass();
        Field[] fields = null;
        try {
            // 取出bean里的所有方法
            fields = cls.getDeclaredFields();
        }
        catch (Exception e) {
            logger.warn(String.format("Get Declared Fields failed.", bean) + e);
        }
        return fields;
    }

    //反射属性值

    /**
     * 根据实例获取相应属性的值
     *
     * @param bean : 类实例
     * @return Object : 返回相应属性值
     */
    public static Object GetFieldValueInObj(Object bean, Field fieldName) {
        Object mBean = null;
        try {
            //设置访问性，反射类的方法，设置为true就可以访问private修饰的东西，否则无法访问
            fieldName.setAccessible(true);
            mBean = fieldName.get(bean);
        }
        catch (Exception ex)
        {
            logger.warn(String.format("Get field: Get object \"%s\" field \"%s\" failed.", bean, fieldName) + ex);
        }
        return mBean;
    }

    /**
     * 根据实例获取相应属性List的值和的字符串
     *
     * @param bean : 类实例
     * @return Object : 返回相应属性值List
     */
    public static Object getFieldValueInObjStr(Object bean, List<Field> fieldName) {
        String fieldListStr = null;
        for (Field field : fieldName)
        {
            String fieldItemStr = GetFieldValueInObj(bean, field).toString();
            fieldListStr += String.format(", \"%s\"", fieldItemStr);
        }
        return fieldListStr;
    }

    /**
     * 根据属性名List获取所有主键属性
     *
     * @param bean : 类实例
     * @param pkList : 主键List
     * @return Field : 返回相应注解值
     */
    public static List<Field> getPrimaryFields(Object bean, List<String> pkList) {
        List<Field> reFields = new ArrayList<>();
        try {
            for(String fieldName : pkList) {
                Field field = bean.getClass().getDeclaredField(fieldName);
                reFields.add(field);
            }
        }
        catch (Exception e) {
            logger.warn(String.format("Get field failed: Get object \"%s\" primary key fields \"%s\" failed.", bean, pkList) + e);
        }
        return reFields;
    }


    /**
     * 获取对象内的属性
     *
     * @param instance : 类实例
     * @param filedName : 属性Name
     * @return Field : 返回相应注解值
     */
    public static String getPrivateField(Object instance, String filedName) {
        Field field = null;
        try {
            field = instance.getClass().getDeclaredField(filedName);
            field.setAccessible(true);
        }
        catch (Exception e) {
            System.out.print(e);
        }
        if(field == null || field.getType()  == null)
            return "";
        else
            return field.getType().getName();
    }

    /**
     * 根据属性名List获取所有主键属性
     * @param pkList : 主键List
     * @return String : 拼接后的主键字符串
     */
    public static String getPrimaryFieldsStrViaList(List<String> pkList) {
        String fieldStr = "";
        if (pkList != null) {
            for (String pkItemStr : pkList) {
                if (fieldStr == "") {
                    fieldStr = pkItemStr;
                } else {
                    fieldStr = fieldStr + "," + pkItemStr;
                }
            }
        }
        return fieldStr;
    }
}

