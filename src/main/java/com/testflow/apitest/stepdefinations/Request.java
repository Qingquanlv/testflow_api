package com.testflow.apitest.stepdefinations;

import com.testflow.apitest.business.BufferManager;
import com.testflow.apitest.servicesaccess.HttpClientUtil;
import com.testflow.apitest.utilities.ParamUtil;
import com.testflow.apitest.utilities.TableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author qq.lv
 * @date 2019/6/15
 */
public class Request {

    private static Logger logger = LoggerFactory.getLogger(Request.class);
    /**
     * 发送Json请求
     *
     */
    public void sendRequest(String requestJsonStr, String url, String responce) {
        requestJsonStr = ParamUtil.parseParam(requestJsonStr);
        logger.info(String.format("%s Send request: %s", new Date(), requestJsonStr));
        url = ParamUtil.parseParam(url);
        logger.info(String.format("%s Url: %s", new Date(), url));
        //保存Response到Buffer
        String requestObject = HttpClientUtil.sendHttpPostJson(url, requestJsonStr);
        logger.info(String.format("%s Get responce: %s", new Date(), requestObject));
        BufferManager.addBufferByKey(responce, requestObject);
    }

    /**
     * 发送ListMap格式请求
     *
     */
    public void sendRequest(String requsetName, List<Map<String, String>> requestListMap, String url, String responce) {
        String requestJsonStr = TableUtil.convertListMapToJSON(requsetName,  requestListMap);
        //保存Response到Buffer
        logger.info(String.format("%s Send request: %s", new Date(), requestJsonStr));
        url = ParamUtil.parseParam(url);
        logger.info(String.format("%s Url: %s", new Date(), url));
        String requestObject = HttpClientUtil.sendHttpPostJson(url, requestJsonStr);
        logger.info(String.format("%s Get responce: %s", new Date(), requestObject));
        BufferManager.addBufferByKey(responce, requestObject);
    }
}
