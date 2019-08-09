package com.testflow.apitest.servicesaccess;

import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
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

/**
 *
 * @author qq.lv
 * @date 2019/8/9
 */
public class HttpClientImpl {
    //utf-8字符编码
    public static final String CHARSET_UTF_8 = "utf-8";
    //本机
    public static final String LOCAL_HOST = "localhost";
    //本机
    public static final String SOCKETTIMEOUT = "socketTimeout";
    //本机
    public static final String CONNECTTIMEOUT = "connectTimeout";
    //本机
    public static final String CONNECTIONREQUESTTIMEOUT = "connectionRequestTimeout";
    //HTTP内容类型
    public static final String CONTENT_TYPE_TEXT_HTML = "text/xml";
    //HTTP内容类型。相当于form表单的形式，提交数据
    public static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded";
    //连接管理器
    private static PoolingHttpClientConnectionManager pool;
    private static RequestConfig requestConfig;// 请求配置

    static {
        try {
            //System.out.println("初始化HttpClientTest~~~开始");
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    builder.build());
            // 配置同时支持 HTTP 和 HTPPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register(
                    "http", PlainConnectionSocketFactory.getSocketFactory()).register(
                    "https", sslsf).build();
            // 初始化连接管理器
            pool = new PoolingHttpClientConnectionManager(
                    socketFactoryRegistry);
            // 将最大连接数增加到200，实际项目最好从配置文件中读取这个值
            pool.setMaxTotal(200);
            // 设置最大路由
            pool.setDefaultMaxPerRoute(2);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置请求配置
     *
     * @param configMap
     */
    public static void setRequestConfig(HashMap<String, String> configMap) {
        // 根据默认超时限制初始化requestConfig
        int socketTimeout = 20000;
        int connectTimeout = 20000;
        int connectionRequestTimeout = 20000;
        if (null != configMap.get(SOCKETTIMEOUT)) {
            RequestConfig.custom().setSocketTimeout(Integer.parseInt(configMap.get(SOCKETTIMEOUT)));
        }
        else {
            RequestConfig.custom().setSocketTimeout(socketTimeout);
        }
        if (null != configMap.get(CONNECTTIMEOUT)) {
            RequestConfig.custom().setConnectTimeout(Integer.parseInt(configMap.get(CONNECTTIMEOUT)));
        }
        else {
            RequestConfig.custom().setConnectTimeout(connectTimeout);
        }
        if (null != configMap.get(CONNECTIONREQUESTTIMEOUT)) {
            RequestConfig.custom().setConnectionRequestTimeout(Integer.parseInt(configMap.get(CONNECTIONREQUESTTIMEOUT)));
        }
        else {
            RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout);
        }
        if (null != configMap.get(CONNECTIONREQUESTTIMEOUT)) {
            RequestConfig.custom().setConnectionRequestTimeout(Integer.parseInt(configMap.get(CONNECTIONREQUESTTIMEOUT)));
        }
        else {
            RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout);
        }
        if (null != configMap.get(CONNECTIONREQUESTTIMEOUT)) {
            RequestConfig.custom().setConnectionRequestTimeout(Integer.parseInt(configMap.get(CONNECTIONREQUESTTIMEOUT)));
        }
        else {
            RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout);
        }
        RequestConfig.custom().build();
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
     * 发送Http请求
     *
     * @param http
     * @param <T>
     * @return
     */
    public static <T extends HttpRequestBase> String sendHttp(T http) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        // 响应内容
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = getHttpClient();
            // 配置请求信息
            http.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(http);
            // 得到响应实例
            HttpEntity entity = response.getEntity();
            // 可以获得响应头
            Header[] headers = response.getHeaders(HttpHeaders.CONTENT_TYPE);
            for (Header header : headers) {
                System.out.println(header.getName());
            }
            // 得到响应类型
            System.out.println(ContentType.getOrDefault(response.getEntity()).getMimeType());
            // 判断响应状态
            if (response.getStatusLine().getStatusCode() >= 300) {
                throw new Exception(
                        "HTTP Request is not success, Response code is " + response.getStatusLine().getStatusCode());
            }
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
                EntityUtils.consume(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }

    public static CloseableHttpClient getHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom()
                // 设置连接池管理
                .setConnectionManager(pool)
                // 设置请求配置
                .setDefaultRequestConfig(requestConfig)
                // 设置重试次数
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                .build();
        return httpClient;
    }
}
