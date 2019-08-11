package com.testflow.apitest.servicesaccess;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionOperator;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HttpClientUtil {

    //HTTP内容类型。相当于form表单的形式，提交数据
    public static final String CONTENT_TYPE_JSON = "application/json;charset=utf-8";
    public static final String CONTENT_TYPE_XML_URL="application/xml";
    //Socket超时时间
    public static final String SOCKETTIMEOUT = "socketTimeout";
    //连接超时时间
    public static final String CONNECTTIMEOUT = "connectTimeout";
    //请求超时时间
    public static final String CONNECTIONREQUESTTIMEOUT = "connectionRequestTimeout";
    //本机
    public static final String LOCAL_HOST = "localhost";

    //HTTP头部类型
    private static HashMap<String, String> httpHeaderParam;
    // 请求配置
    private static HashMap<String, String> requestConfigParam;

    public static void setHttpHeaderParam(String headerKey, String headerVal) {
        if (null == httpHeaderParam) {
            httpHeaderParam = new HashMap<>();
        }
        httpHeaderParam.put(headerKey, headerVal);
    }

    public static HashMap<String, String> getHttpHeaderParam() {
        return httpHeaderParam;
    }

    public static void setRequestConfigParam(String headerKey, String headerVal) {
        if (null == requestConfigParam) {
            requestConfigParam = new HashMap<>();
        }
        requestConfigParam.put(headerKey, headerVal);
    }

    public static HashMap<String, String> getRequestConfigParam() {
        return requestConfigParam;
    }

    /**
     * 发送 Head请求
     * @param httpUrl 地址
     * @return
     */
    public static String sendHttpHead(String httpUrl) {
        // 创建HttpHead
        HttpHead httpHead = new HttpHead(httpUrl);
        addHeaders(httpHead);
        buildRequestConfig();
        RequestConfig.custom().build();
        return HttpClientImpl.sendHttp(httpHead);
    }

    /**
     * 发送 Trace请求
     * @param httpUrl
     * @return
     */
    public static String sendHttpTrace(String httpUrl) {
        // 创建HttpTrace
        HttpTrace httpTrace = new HttpTrace(httpUrl);
        addHeaders(httpTrace);
        buildRequestConfig();
        RequestConfig.custom().build();
        return HttpClientImpl.sendHttp(httpTrace);
    }

    /**
     * 发送 Options请求
     * @param httpUrl
     * @return
     */
    public static String sendHttpOptions(String httpUrl) {
        // 创建HttpOptions
        HttpOptions httpOptions = new HttpOptions(httpUrl);
        addHeaders(httpOptions);
        buildRequestConfig();
        RequestConfig.custom().build();
        return HttpClientImpl.sendHttp(httpOptions);
    }

    /**
     * 发送 Delete请求
     *
     * @param httpUrl    地址
     */
    public static String sendHttpDelete(String httpUrl) {
        // 创建httpPost
        HttpDelete httpDelete = new HttpDelete(httpUrl);
        addHeaders(httpDelete);
        buildRequestConfig();
        RequestConfig.custom().build();
        return HttpClientImpl.sendHttp(httpDelete);
    }

    /**
     * 发送 get请求
     *
     * @param httpUrl
     */
    public static String sendHttpGet(String httpUrl) {
        // 创建get请求
        HttpGet httpGet = new HttpGet(httpUrl);
        addHeaders(httpGet);
        buildRequestConfig();
        RequestConfig.custom().build();
        return HttpClientImpl.sendHttp(httpGet);
    }

    /**
     * 发送 put请求 发送json数据
     *
     * @param httpUrl    地址
     * @param paramsJson 参数(格式 json)
     */
    public static String sendHttpPutJson(String httpUrl, String paramsJson) {
        // 创建httpPost
        HttpPut httpPut = new HttpPut(httpUrl);
        try {
            // 设置参数
            if (paramsJson != null && paramsJson.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(paramsJson, "UTF-8");
                stringEntity.setContentType(CONTENT_TYPE_JSON);
                addHeaders(httpPut);
                httpPut.setEntity(stringEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        buildRequestConfig();
        RequestConfig.custom().build();
        return HttpClientImpl.sendHttp(httpPut);
    }

    /**
     * 发送 post请求 发送json数据
     *
     * @param httpUrl    地址
     * @param paramsJson 参数(格式 json)
     */
    public static String sendHttpPostJson(String httpUrl, String paramsJson) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        try {
            // 设置参数
            if (paramsJson != null && paramsJson.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(paramsJson, "UTF-8");
                stringEntity.setContentType(CONTENT_TYPE_JSON);
                httpPost.setEntity(stringEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        buildRequestConfig();
        RequestConfig.custom().build();
        return HttpClientImpl.sendHttp(httpPost);
    }


    /**
     * 设置代理
     *
     * @param ipAddress
     * @param port
     * @param scheme
     */
    public static void setProxy(String ipAddress, int port, String scheme) {
        try {
            HttpHost proxy = null;
            if (LOCAL_HOST.equals(ipAddress.trim().toLowerCase())) {
                InetAddress addr = InetAddress.getLocalHost();
                proxy = new HttpHost(addr, port, scheme);
            }
            else {
                proxy = new HttpHost(ipAddress, port, scheme);
            }
            RequestConfig.custom().setProxy(proxy);
        }catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送 post请求 发送xml数据
     *
     * @param httpUrl   地址
     * @param paramsXml 参数(格式 Xml)
     */
    public static String sendHttpPostXml(String httpUrl, String paramsXml) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        try {
            // 设置参数
            if (paramsXml != null && paramsXml.trim().length() > 0) {
                StringEntity stringEntity = new StringEntity(paramsXml, "UTF-8");
                stringEntity.setContentType(CONTENT_TYPE_XML_URL);
                httpPost.setEntity(stringEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpClientImpl.sendHttp(httpPost);
    }

    /**
     * 将map集合的键值对转化成：key1=value1&key2=value2 的形式
     *
     * @param parameterMap 需要转化的键值对集合
     * @return 字符串
     */
    public static String convertStringParamter(Map parameterMap) {
        StringBuffer parameterBuffer = new StringBuffer();
        if (parameterMap != null) {
            Iterator iterator = parameterMap.keySet().iterator();
            String key = null;
            String value = null;
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                if (parameterMap.get(key) != null) {
                    value = (String) parameterMap.get(key);
                } else {
                    value = "";
                }
                parameterBuffer.append(key).append("=").append(value);
                if (iterator.hasNext()) {
                    parameterBuffer.append("&");
                }
            }
        }
        return parameterBuffer.toString();
    }

    /**
     * 添加请求Header
     *
     * @param param
     * @param <T>
     */
    private static <T extends HttpRequestBase> void addHeaders(T param) {
        HashMap<String, String> map = getHttpHeaderParam();
        if (map != null) {
            for (String key : map.keySet()) {
                param.addHeader(key, map.get(key));
            }
        }
    }

    /**
     * 设置请求参数
     *
     */
    private static void buildRequestConfig() {
        HashMap<String, String> map = getRequestConfigParam();
        // 根据默认超时限制初始化requestConfig
        int socketTimeout = 20000;
        int connectTimeout = 20000;
        int connectionRequestTimeout = 20000;
        if (null != map.get(SOCKETTIMEOUT)) {
            RequestConfig.custom().setSocketTimeout(Integer.parseInt(map.get(SOCKETTIMEOUT)));
        }
        else {
            RequestConfig.custom().setSocketTimeout(socketTimeout);
        }
        if (null != map.get(CONNECTTIMEOUT)) {
            RequestConfig.custom().setConnectTimeout(Integer.parseInt(map.get(CONNECTTIMEOUT)));
        }
        else {
            RequestConfig.custom().setConnectTimeout(connectTimeout);
        }
        if (null != map.get(CONNECTIONREQUESTTIMEOUT)) {
            RequestConfig.custom().setConnectionRequestTimeout(Integer.parseInt(map.get(CONNECTIONREQUESTTIMEOUT)));
        }
        else {
            RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout);
        }
        if (null != map.get(CONNECTIONREQUESTTIMEOUT)) {
            RequestConfig.custom().setConnectionRequestTimeout(Integer.parseInt(map.get(CONNECTIONREQUESTTIMEOUT)));
        }
        else {
            RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout);
        }
        if (null != map.get(CONNECTIONREQUESTTIMEOUT)) {
            RequestConfig.custom().setConnectionRequestTimeout(Integer.parseInt(map.get(CONNECTIONREQUESTTIMEOUT)));
        }
        else {
            RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout);
        }
    }
}
