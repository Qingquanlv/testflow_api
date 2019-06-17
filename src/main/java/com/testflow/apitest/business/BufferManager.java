package com.testflow.apitest.business;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author qq.lv
 * @date 2019/6/2
 */
public class BufferManager {
    private static Map<String, Object> requestObjectBufferMap;

    public static void initBufferMap()
    {
        if(requestObjectBufferMap == null) {
            requestObjectBufferMap = new HashMap<String, Object>();
        }
    }

    public static void deposeBufferMap() {
        requestObjectBufferMap.clear();
    }

    public static Object getBufferByKey(String bufferKey) {
        return requestObjectBufferMap.get(bufferKey);
    }

    public static void addBufferByKey(String bufferKey, Object bufferObject) {
        if(!bufferExist(bufferKey)) {
            requestObjectBufferMap.put(bufferKey, bufferObject);
        }
        else
        {
            int i = 1;
            while (true){
                if(bufferKey.contains("_")) {
                    bufferKey = bufferKey.substring(0, bufferKey.indexOf("_"));
                }
                bufferKey = bufferKey + "_" + i;
                if (!bufferExist(bufferKey)) {
                    break;
                }
                i++;
            }
            requestObjectBufferMap.put(bufferKey, bufferObject);
        }
    }

    public static boolean bufferExist(String bufferObjectMapKey) {
        return requestObjectBufferMap.containsKey(bufferObjectMapKey);
    }
}