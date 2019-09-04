package com.testflow.apitest.stepdefinations;

import com.testflow.apitest.business.BufferManager;
import com.testflow.apitest.servicesaccess.HttpClientImpl;
import com.testflow.apitest.servicesaccess.HttpClientUtil;
import com.testflow.apitest.utilities.FastJsonUtil;
import com.testflow.apitest.utilities.ParamUtil;
import com.testflow.apitest.utilities.TableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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
    //HTTP内容类型。相当于form表单的形式，提交数据
    public static final String CONTENT_TYPE_JSON = "application/json;charset=utf-8";
    public static final String CONTENT_TYPE_XML="text/xml";

    /**
     * 发送Json请求
     *
     */
    public String sendRequest(String requestJsonStr, String url) throws Exception {
        requestJsonStr = ParamUtil.parseParam(requestJsonStr);
        logger.info(String.format("%s Send request: %s", new Date(), requestJsonStr));
        url = ParamUtil.parseParam(url);
        logger.info(String.format("%s Url: %s", new Date(), url));
        //保存Response到Buffer
        String requestObject = HttpClientUtil.sendHttpPost(url, requestJsonStr, CONTENT_TYPE_JSON);
        logger.info(String.format("%s Get responce: %s", new Date(), requestObject));
        return requestObject;
    }

    /**
     * 发送Json Post请求
     *
     */
    public String sendPostRequest(String requestJsonStr, String url) throws Exception {
        requestJsonStr = ParamUtil.parseParam(requestJsonStr);
        logger.info(String.format("%s Send request: %s", new Date(), requestJsonStr));
        url = ParamUtil.parseParam(url);
        logger.info(String.format("%s Url: %s", new Date(), url));
        //保存Response到Buffer
        String requestObject = HttpClientUtil.sendHttpPost(url, requestJsonStr, CONTENT_TYPE_JSON);
        logger.info(String.format("%s Get responce: %s", new Date(), requestObject));
        return requestObject;
    }

    /**
     * 发送XML Post请求
     *
     */
    public String sendPostRequestXML(String requestXmlStr, String url) throws Exception {
        requestXmlStr = ParamUtil.parseParam(requestXmlStr);
        logger.info(String.format("%s Send request: %s", new Date(), requestXmlStr));
        url = ParamUtil.parseParam(url);
        logger.info(String.format("%s Url: %s", new Date(), url));
        //保存Response到Buffer
        String requestObject = HttpClientUtil.sendHttpPost(url, requestXmlStr, CONTENT_TYPE_XML);
        logger.info(String.format("%s Get responce: %s", new Date(), requestObject));
        return  requestObject;
    }

    /**
     * 批量发送Json Post请求
     *
     */
    public String sendBatchPostRequest(String requestJsonStr, String url) throws Exception {
        List<String> requestJsonStrList = ParamUtil.parseParamList(requestJsonStr);
        String responceJsonStrList = "[";
        //批量post json str
        List<String> list = new ArrayList<>();
        for (String str : requestJsonStrList) {
            list.add(ParamUtil.parseParam(str));
        }
        logger.info(String.format("%s Send request: %s", new Date(), list));
        url = ParamUtil.parseParam(url);
        logger.info(String.format("%s Url: %s", new Date(), url));
        //保存Response到Buffer
        List<String> rsplist = HttpClientUtil.sendHttpPostList(url, requestJsonStrList, CONTENT_TYPE_JSON);
        for (int i = 0; i < rsplist.size(); i++) {
            responceJsonStrList += rsplist.get(i);
            if (i != rsplist.size() - 1) {
                responceJsonStrList += ",";
            }
        }
        responceJsonStrList += "]";
        logger.info(String.format("%s Get responce: %s", new Date(), responceJsonStrList));
        return responceJsonStrList;
    }

    /**
     * 批量发送XML Post请求
     *
     */
    public String sendBatchPostRequestXML(String requestXmlStr, String url) throws Exception {
        List<String> requestJsonStrList = ParamUtil.parseParamList(requestXmlStr);
        String responceJsonStrList = "<ListRoot>";
        //批量post json str
        List<String> list = new ArrayList<>();
        for (String str : requestJsonStrList) {
            list.add(ParamUtil.parseParam(str));
        }
        logger.info(String.format("%s Send request: %s", new Date(), list));
        url = ParamUtil.parseParam(url);
        logger.info(String.format("%s Url: %s", new Date(), url));
        //保存Response到Buffer
        List<String> rsplist = HttpClientUtil.sendHttpPostList(url, requestJsonStrList, CONTENT_TYPE_XML);
        for (int i = 0; i < rsplist.size(); i++) {
            responceJsonStrList += rsplist.get(i);
        }
        responceJsonStrList += "</ListRoot>";
        logger.info(String.format("%s Get responce: %s", new Date(), responceJsonStrList));
        return responceJsonStrList;
    }

    /**
     * 发送Json get请求
     *
     */
    public String sendGetRequest(String url) throws Exception {
        logger.info(String.format("%s Send get request", new Date()));
        logger.info(String.format("%s Url: %s", new Date(), url));
        //保存Response到Buffer
        String responceObject = HttpClientUtil.sendHttpGet(url);
        logger.info(String.format("%s Get responce: %s", new Date(), responceObject));
        return responceObject;
    }

    /**
     * 发送Json Head请求
     *
     */
    public String sendHeadRequest(String url) throws Exception {
        logger.info(String.format("%s Send head request", new Date()));
        logger.info(String.format("%s Url: %s", new Date(), url));
        //保存Response到Buffer
        String responceObject = HttpClientUtil.sendHttpHead(url);
        logger.info(String.format("%s Get responce: %s", new Date(), responceObject));
        return responceObject;
    }

    /**
     * 发送Json Put请求
     *
     */
    public String sendPutRequest(String requestJsonStr, String url) throws Exception {
        requestJsonStr = ParamUtil.parseParam(requestJsonStr);
        logger.info(String.format("%s Send request: %s", new Date(), requestJsonStr));
        url = ParamUtil.parseParam(url);
        logger.info(String.format("%s Url: %s", new Date(), url));
        //保存Response到Buffer
        String responceObject = HttpClientUtil.sendHttpPutJson(url, requestJsonStr, CONTENT_TYPE_JSON);
        logger.info(String.format("%s Get responce: %s", new Date(), responceObject));
        return responceObject;
    }

    /**
     * 发送Json Put请求
     *
     */
    public String sendPutRequestXML(String requestJsonStr, String url) throws Exception {
        requestJsonStr = ParamUtil.parseParam(requestJsonStr);
        logger.info(String.format("%s Send request: %s", new Date(), requestJsonStr));
        url = ParamUtil.parseParam(url);
        logger.info(String.format("%s Url: %s", new Date(), url));
        //保存Response到Buffer
        String responceObject = HttpClientUtil.sendHttpPutJson(url, requestJsonStr, CONTENT_TYPE_XML);
        logger.info(String.format("%s Get responce: %s", new Date(), responceObject));
        return responceObject;
    }

    /**
     * 批量发送Json Put请求
     *
     */
    public String sendBatchPutRequest(String requestJsonStr, String url) throws Exception {
        List<String> requestJsonStrList = ParamUtil.parseParamList(requestJsonStr);
        String responceJsonStrList = "[";
        //批量post json str
        List<String> list = new ArrayList<>();
        for (String str : requestJsonStrList) {
            list.add(ParamUtil.parseParam(str));
        }
        logger.info(String.format("%s Send request: %s", new Date(), list));
        url = ParamUtil.parseParam(url);
        logger.info(String.format("%s Url: %s", new Date(), url));
        //保存Response到Buffer
        List<String> rsplist = HttpClientUtil.sendHttpPutJsonList(url, requestJsonStrList, CONTENT_TYPE_JSON);
        for (int i = 0; i < rsplist.size(); i++) {
            responceJsonStrList += rsplist.get(i);
            if (i != rsplist.size() - 1) {
                responceJsonStrList += ",";
            }
        }
        responceJsonStrList += "]";
        logger.info(String.format("%s Get responce: %s", new Date(), responceJsonStrList));
        return responceJsonStrList;
    }

    /**
     * 发送Json Delete请求
     *
     */
    public String sendDeleteRequest(String url) throws Exception {
        logger.info(String.format("%s Send head request", new Date()));
        logger.info(String.format("%s Url: %s", new Date(), url));
        //保存Response到Buffer
        String responceObject = HttpClientUtil.sendHttpDelete(url);
        logger.info(String.format("%s Get responce: %s", new Date(), responceObject));
        return responceObject;
    }

    /**
     * 发送Json Options请求
     *
     */
    public String sendOptionsRequest(String url) throws Exception {
        logger.info(String.format("%s Send head request", new Date()));
        logger.info(String.format("%s Url: %s", new Date(), url));
        //保存Response到Buffer
        String responceObject = HttpClientUtil.sendHttpOptions(url);
        logger.info(String.format("%s Get responce: %s", new Date(), responceObject));
        return responceObject;
    }

    /**
     * 发送Json Trace请求
     *
     */
    public String sendTraceRequest(String url, String responce) throws Exception {
        logger.info(String.format("%s Send head request", new Date()));
        logger.info(String.format("%s Url: %s", new Date(), url));
        //保存Response到Buffer
        String responceObject = HttpClientUtil.sendHttpTrace(url);
        logger.info(String.format("%s Get responce: %s", new Date(), responceObject));
        return responceObject;
    }

    /**
     * 发送ListMap格式请求
     *
     */
    public String sendRequest(String requsetName, List<Map<String, String>> requestListMap, String url) throws Exception {
        String requestJsonStr = TableUtil.convertListMapToJSON(requsetName,  requestListMap);
        //保存Response到Buffer
        logger.info(String.format("%s Send request: %s", new Date(), requestJsonStr));
        url = ParamUtil.parseParam(url);
        logger.info(String.format("%s Url: %s", new Date(), url));
        String responceObject = HttpClientUtil.sendHttpPost(url, requestJsonStr, CONTENT_TYPE_JSON);
        logger.info(String.format("%s Get responce: %s", new Date(), responceObject));
        return responceObject;
    }

    /**
     * 设置请求中的Header
     *
     */
    public void setHeaders(String headerKey, String headerVal) {
        HttpClientImpl.setHttpHeaderParam(headerKey, headerVal);
    }

    /**
     * 设置请求Config
     *
     */
    public void setRequestConfig(String configKey, String configVal) {
        HttpClientImpl.setRequestConfigParam(configKey, configVal);
    }

    /**
     * 设置请求代理
     *
     */
    public void setProxy(String ipAddress, int port, String scheme) throws Exception {
        HttpClientUtil.setProxy(ipAddress, port, scheme);
    }
}
