package com.testflow.apitest.stepdefinations;

import com.testflow.apitest.business.BufferManager;
import com.testflow.apitest.servicesaccess.HttpClientUtil;
import com.testflow.apitest.utilities.ParamUtil;
import com.testflow.apitest.utilities.TableUtil;

import java.util.List;
import java.util.Map;

/**
 *
 * @author qq.lv
 * @date 2019/6/15
 */
public class Request {

    /**
     * 发送Json请求
     *
     */
    public void sendRequest(String requestJsonStr, String url, String responce) {
        requestJsonStr = ParamUtil.parseParam(requestJsonStr);
        url = ParamUtil.parseParam(url);
        //保存Response到Buffer
        String requestObject = HttpClientUtil.sendHttpPostJson(url, requestJsonStr);
        BufferManager.addBufferByKey(responce, requestObject);
    }

    /**
     * 发送ListMap格式请求
     *
     */
    public void sendRequest(String requsetName, List<Map<String, String>> requestListMap, String url, String responce) {
        String requestJsonStr = TableUtil.convertListMapToJSON(requsetName,  requestListMap);
        //保存Response到Buffer
        String requestObject = HttpClientUtil.sendHttpPostJson(url, requestJsonStr);
        BufferManager.addBufferByKey(responce, requestObject);
    }

}