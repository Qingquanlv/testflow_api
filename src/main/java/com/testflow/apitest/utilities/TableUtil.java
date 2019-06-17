package com.testflow.apitest.utilities;

import com.testflow.apitest.common.Constants;
import com.testflow.apitest.repository.EntityTree;
import com.testflow.apitest.servicesaccess.ServiceAccess;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author qq.lv
 * @date 2019/6/2
 */
public class TableUtil {

    public static String convertListMapToJSON(String rootName, List<Map<String, String>> requestListMap) {
        Object sourceObj = new Object();
        EntityTree tarEntityTree = convertTableToTree(rootName, requestListMap);
        Object obj = initRequestObj(sourceObj, tarEntityTree, "");
        return FastJsonUtil.toJson(obj);
    }

    private static EntityTree convertTableToTree(String rootName, List<Map<String, String>> requestListMap) {
        EntityTree tempOjb;
        //Json Object Map
        EntityTree rootOjb = new EntityTree(rootName);
        for (Map<String, String> objMap : requestListMap) {
            tempOjb = rootOjb;
            String[] objectList = objMap.get(Constants.MAPKEY).split(Constants.COLON);
            for (int i = 0; i < objectList.length; i++) {
                if (tempOjb.hasSpecifyChild(objectList[i].trim()) == false) {
                    EntityTree objTree = new EntityTree(objectList[i].trim());
                    tempOjb.addNode(objTree);
                    tempOjb = objTree;
                } else {
                    tempOjb = tempOjb.getSpecifyChild(objectList[i].trim());
                }
                if (i == objectList.length - 1) {
                    tempOjb.setNodeValue(objMap.get(Constants.MAPVALUE));
                }
            }
        }
        return rootOjb;
    }

    private static Object initRequestObj(Object obj, EntityTree treeNode, String oraginValueType)
    {
        String valType = new String();
        Object CurrencyObj = null;
        //If leaf node，init current node
        if(treeNode.isLeaf()) {
            setValueToEntity(obj, treeNode.getNodeKey(), treeNode.getNodeValue());
        }
        else {
            //If list node
            if (treeNode.isListNode()) {
                CurrencyObj = createReqObj(treeNode.getNodeKey().toString(),
                        treeNode.getChild(0).getNodeKey().toString());
                oraginValueType = getReqObj(obj, treeNode.getNodeKey().toString());
            }
            else if(treeNode.isListNodeChild()) {
                CurrencyObj = createReqObj(oraginValueType);
            }
            else
            {
                CurrencyObj = createReqObj(treeNode.getNodeKey().toString());
                if(CurrencyObj == null) {
                    CurrencyObj = createReqObj(
                            ServiceAccess.getPrivateField(
                                    obj, treeNode.getNodeKey().toString()));
                }
            }
            for (EntityTree childTreeNode : treeNode.getChilds()) {
                initRequestObj(CurrencyObj, childTreeNode, oraginValueType);
            }
            //赋值的时候一定传递的是引用的实体
            setValueToEntity(obj, treeNode.getNodeKey(),CurrencyObj);
        }
        return CurrencyObj;
    }

    private static void setValueToEntity(Object bean, Object key, Object value)
    {
        //判断如果为List类型，直接新建List
        if(isObjectList(key.toString())) {
            List<Object> objects = (ArrayList) bean;
            objects.add(value);
            return;
        }
        Class<?> cls = bean.getClass();
        // 取出bean里的所有方法和属性
        Method[] methods = ServiceAccess.getDeclaredMethod(bean);
        Field[] fields = ServiceAccess.getDeclaredFields(bean);

        for (Field field : fields) {
            try {
                String fieldSetName = parSetName(field.getName());
                if (!checkSetMet(methods, fieldSetName) || ! key.toString().equals(field.getName())) {
                    continue;
                }
                Method fieldSetMet = cls.getMethod(fieldSetName, field.getType());
                if (null != value && !"".equals(value)) {
                    String fieldType = field.getType().getSimpleName();
                    //当对应属性为枚举类型时，初始化实例
                    if(fieldType.toLowerCase().contains("enum"))
                    {
                        Class clazz = ServiceAccess.reflectClazz(field.getType().getName());
                        Object temp = Enum.valueOf(clazz, value.toString());
                        fieldSetMet.invoke(bean, temp);
                    }
                    value = ParseTypeUtility.parseBaseFieldValue(value, fieldType);
                    fieldSetMet.invoke(bean, value);
                }
            } catch (Exception e) {
                System.out.println(String.format("Init object failed, set data value \"%s\" failed: ", value.toString()) + e);
                continue;
            }
            break;
        }
    }

    private static Object createReqObj(String objectName, String chlidObjectName)  {
        List<Object> objects = new ArrayList<Object>();
        return objects;
    }

    private static String getReqObj(Object obj, String fieldName) {
        String listItemType = "";
        fieldName = parGetName(fieldName);
        Method method = ServiceAccess.getObjMethod(obj, fieldName);
        if (method != null && !"".equals(method.getGenericReturnType())) {
            listItemType = method.getGenericReturnType().getTypeName();
        }
        return getListItemType(listItemType);
    }

    private static Object createReqObj(String objectName) {
        Object obj = new Object();
        if (isObjectList(objectName)) {
            obj = ServiceAccess.createObject(objectName.substring(0, objectName.indexOf("[")));
        }
        else {
            obj = ServiceAccess.createObject(objectName);
        }
        return obj;
    }

    private static boolean isObjectList(String objectName) {
        String pattern = ".*\\[(\\d+)\\]";
        Pattern r = Pattern.compile(pattern);
        return Pattern.matches(pattern, objectName);
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

    /**
     * 拼接在某属性的 set方法
     *
     * @param fieldName
     * @return String
     */
    private static String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_') {
            startIndex = 1;
        }
        return "set"
                + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }

    /**
     * 判断是否存在某属性的 set方法
     *
     * @param methods
     * @param fieldSetMet
     * @return boolean
     */
    private static boolean checkSetMet(Method[] methods, String fieldSetMet) {
        for (Method met : methods) {
            if (fieldSetMet.equals(met.getName())) {
                return true;
            }
        }
        return false;
    }

    private static String getListItemType(String value)
    {
        String targetStr = "";
        String pattern = "java\\.util\\.List<(.*)>";
        Pattern  r = Pattern.compile(pattern);
        Matcher ma = r.matcher(value);
        boolean rs = ma.find();
        Pattern.matches(pattern, value);
        targetStr = ma.group(1);
        return targetStr;
    }
}
