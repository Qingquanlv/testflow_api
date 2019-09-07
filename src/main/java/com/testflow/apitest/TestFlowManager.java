package com.testflow.apitest;

import com.testflow.apitest.business.BufferManager;
import com.testflow.apitest.business.LogHelper;
import com.testflow.apitest.parser.DataParser;
import com.testflow.apitest.servicesaccess.ServiceAccess;
import com.testflow.apitest.stepdefinations.*;
import com.testflow.apitest.utilities.FastJsonUtil;
import com.testflow.apitest.utilities.ParamUtil;

import java.util.List;
import java.util.Map;

/**
 *
 * @author qq.lv
 * @date 2019/6/2
 */
public class TestFlowManager {

    private static class SingleHolder{
        private static TestFlowManager instance = new TestFlowManager();
    }

    /**
     * 私有化构造方法
     */
    private TestFlowManager(){

        BufferManager.initBufferMap();
        LogHelper.initLog();
    }

    /**
     * 初始化实例，初始化缓存
     *
     */
    public static TestFlowManager runner(){
        return SingleHolder.instance;
    }

    /**
     * 销毁缓存
     *
     */
    public TestFlowManager deposed(){
        BufferManager.deposeBufferMap();
        LogHelper.deposeLog();
        return this;
    }

    /**
     * 发送Json请求
     * @param requestJsonStr : Json格式请求
     * @param url : 请求url
     * @param responce : 保存response的key
     *
     */
    public TestFlowManager sendRequest(String requestJsonStr, String url, String responce) {
        Request request = new Request();
        String responceStr;
        LogHelper.stepExecLog("sendRequest", requestJsonStr, url, responce);
        try {
            responceStr = request.sendRequest(requestJsonStr, url);
            BufferManager.addBufferByKey(responce, responceStr);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("send Request failed: %s", ex));
        }
        LogHelper.stepAfterLog("sendRequest", responceStr);
        return this;
    }

    /**
     * 发送Post请求
     * @param requestJsonStr : Json格式请求
     * @param url : 请求url
     * @param responce : 保存response的key
     *
     */
    public TestFlowManager sendPostRequest(String requestJsonStr, String url, String responce) {
        Request request = new Request();
        String responceStr;
        LogHelper.stepExecLog("sendPostRequest", requestJsonStr, url, responce);
        try {
            responceStr = request.sendPostRequest(requestJsonStr, url);
            BufferManager.addBufferByKey(responce, responceStr);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("send Post Request failed: %s", ex));
        }
        LogHelper.stepAfterLog("sendPostRequest", responceStr);
        return this;
    }

    /**
     * 发送Post请求
     * @param requestXmlStr : Xml格式请求
     * @param url : 请求url
     * @param responce : 保存response的key
     *
     */
    public TestFlowManager sendPostRequestXML(String requestXmlStr, String url, String responce) {
        Request request = new Request();
        String responceStr;
        LogHelper.stepExecLog("sendPostRequestXML", requestXmlStr, url, responce);
        try {
            responceStr = request.sendPostRequestXML(requestXmlStr, url);
            BufferManager.addBufferByKey(responce, responceStr);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("send Post Request failed: %s", ex));
        }
        LogHelper.stepAfterLog("sendPostRequestXML", responceStr);
        return this;
    }

    /**
     * 批量发送Post请求
     * @param requestJsonStr : Json格式请求
     * @param url : 请求url
     * @param responce : 保存response的key
     *
     */
    public TestFlowManager sendBatchPostRequest(String requestJsonStr, String url, String responce) {
        Request request = new Request();
        String responceStr;
        LogHelper.stepExecLog("sendBatchPostRequest", requestJsonStr, url, responce);
        try {
            responceStr = request.sendBatchPostRequest(requestJsonStr, url);
            BufferManager.addBufferByKey(responce, responceStr);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("send Post Request failed: %s", ex));
        }
        LogHelper.stepAfterLog("sendBatchPostRequest", responceStr);
        return this;
    }

    /**
     * 批量发送Post请求
     * @param requestXmlStr : Xml格式请求
     * @param url : 请求url
     * @param responce : 保存response的key
     *
     */
    public TestFlowManager sendBatchPostRequestXML(String requestXmlStr, String url, String responce) {
        Request request = new Request();
        String responceStr;
        LogHelper.stepExecLog("sendBatchPostRequestXML", requestXmlStr, url, responce);
        try {
            responceStr = request.sendBatchPostRequestXML(requestXmlStr, url);
            BufferManager.addBufferByKey(responce, responceStr);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("send Post Request failed: %s", ex));
        }
        LogHelper.stepAfterLog("sendBatchPostRequestXML", responceStr);
        return this;
    }

    /**
     * 发送Get请求
     * @param url : 请求url
     * @param responce : 保存response的key
     *
     */
    public TestFlowManager sendGetRequest(String url, String responce) {
        Request request = new Request();
        String responceStr;
        LogHelper.stepExecLog("sendGetRequest", url, responce);
        try {
            responceStr = request.sendGetRequest(url);
            BufferManager.addBufferByKey(responce, responceStr);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("send Get Request failed: %s", ex));
        }
        LogHelper.stepAfterLog("sendGetRequest", responceStr);
        return this;
    }

    /**
     * 发送Header请求
     * @param url : 请求url
     * @param responce : 保存response的key
     *
     */
    public TestFlowManager sendHeadRequest(String url, String responce) {
        Request request = new Request();
        String responceStr;
        LogHelper.stepExecLog("sendHeadRequest", url, responce);
        try {
            responceStr = request.sendHeadRequest(url);
            BufferManager.addBufferByKey(responce, responceStr);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("send head Request failed: %s", ex));
        }
        LogHelper.stepAfterLog("sendHeadRequest", responceStr);
        return this;
    }

    /**
     * 发送Put请求
     * @param url : 请求url
     * @param responce : 保存response的key
     *
     */
    public TestFlowManager sendPutRequest(String requestJsonStr, String url, String responce) {
        Request request = new Request();
        String responceStr;
        LogHelper.stepExecLog("sendPutRequest", requestJsonStr, url, responce);
        try {
            responceStr = request.sendPutRequest(requestJsonStr, url);
            BufferManager.addBufferByKey(responce, responceStr);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("send put Request failed: %s", ex));
        }
        LogHelper.stepAfterLog("sendPutRequest", responceStr);
        return this;
    }

    /**
     * 发送Put请求
     * @param url : 请求url
     * @param responce : 保存response的key
     *
     */
    public TestFlowManager sendPutRequestXML(String requestXmlStr, String url, String responce) {
        Request request = new Request();
        String responceStr;
        LogHelper.stepExecLog("sendPutRequestXML", requestXmlStr, url, responce);
        try {
            responceStr = request.sendPutRequestXML(requestXmlStr, url);
            BufferManager.addBufferByKey(responce, responceStr);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("send put Request failed: %s", ex));
        }
        LogHelper.stepAfterLog("sendPutRequestXML", responceStr);
        return this;
    }

    /**
     * 批量发送Put请求
     * @param requestJsonStr : Json格式请求
     * @param url : 请求url
     * @param responce : 保存response的key
     *
     */
    public TestFlowManager sendBatchPutRequest(String requestJsonStr, String url, String responce) {
        Request request = new Request();
        String responceStr;
        LogHelper.stepExecLog("sendBatchPutRequest", requestJsonStr, url, responce);
        try {
            responceStr = request.sendBatchPutRequest(requestJsonStr, url);
            BufferManager.addBufferByKey(responce, responceStr);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("send Post Request failed: %s", ex));
        }
        LogHelper.stepAfterLog("sendBatchPutRequest", responceStr);
        return this;
    }

    /**
     * 发送Delete请求
     * @param url : 请求url
     * @param responce : 保存response的key
     *
     */
    public TestFlowManager sendDeleteRequest(String requestJsonStr, String url, String responce) {
        Request request = new Request();
        String responceStr;
        LogHelper.stepExecLog("sendDeleteRequest", requestJsonStr, url, responce);
        try {
            responceStr = request.sendDeleteRequest(url);
            BufferManager.addBufferByKey(responce, responceStr);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("send Delete Request failed: %s", ex));
        }
        LogHelper.stepAfterLog("sendDeleteRequest", responceStr);
        return this;
    }

    /**
     * 发送Options请求
     * @param url : 请求url
     * @param responce : 保存response的key
     *
     */
    public TestFlowManager sendOptionsRequest(String url, String responce) {
        Request request = new Request();
        String responceStr;
        LogHelper.stepExecLog("sendOptionsRequest", url, responce);
        try {
            responceStr = request.sendOptionsRequest(url);
            BufferManager.addBufferByKey(responce, responceStr);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("send Options Request failed: %s", ex));
        }
        LogHelper.stepAfterLog("sendOptionsRequest", responceStr);
        return this;
    }

    /**
     * 发送Trace请求
     * @param url : 请求url
     * @param responce : 保存response的key
     *
     */
    public TestFlowManager sendTraceRequest(String url, String responce) {
        Request request = new Request();
        String responceStr;
        LogHelper.stepExecLog("sendTraceRequest", url, responce);
        try {
            responceStr = request.sendTraceRequest(url, responce);
            BufferManager.addBufferByKey(responce, responceStr);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("send Trace Request failed: %s", ex));
        }
        LogHelper.stepAfterLog("sendTraceRequest", responceStr);
        return this;
    }

    /**
     * 设置请求中的Header
     *
     * @param headerKey : 请求HeaderKey
     * @param headerVal : 请求HeaderVal
     * @return
     */
    public TestFlowManager setHeaders(String headerKey, String headerVal) {
        Request request = new Request();
        LogHelper.stepExecLog("setHeaders", headerKey, headerVal);
        request.setHeaders(headerKey, headerVal);
        LogHelper.stepAfterLog("setHeaders", "");
        return this;
    }

    /**
     * 设置请求Config
     *
     * @param configKey : 请求配置Key
     * @param configVal : 请求配置Val
     * @return
     */
    public TestFlowManager setRequestConfig(String configKey, String configVal) {
        Request request = new Request();
        LogHelper.stepExecLog("setRequestConfig", configKey, configVal);
        request.setRequestConfig(configKey, configVal);
        LogHelper.stepAfterLog("setRequestConfig", "");
        return this;
    }

    /**
     * 设置请求代理
     *
     * @param ipAddress : 代理IP
     * @param port : 代理端口号
     * @param scheme : Http/Https
     * @return
     */
    public TestFlowManager setProxy(String ipAddress, int port, String scheme) {
        Request request = new Request();
        LogHelper.stepExecLog("setProxy", ipAddress, String.valueOf(port), scheme);
        try {
            request.setProxy(ipAddress, port, scheme);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("set Proxy failed: %s", ex));
        }
        LogHelper.stepAfterLog("setProxy", "");
        return this;
    }

    /**
     * 发送Cucumber格式请求
     * @param requsetName : cucumber格式RequestName
     * @param requestListMap : cucumber table.asMaps 格式request参数
     * @param url : 请求url
     * @param responce : 保存response的key
     *
     */
    public TestFlowManager sendRequest(String requsetName, List<Map<String, String>> requestListMap, String url, String responce) {
        Request request = new Request();
        String responceStr;
        LogHelper.stepExecLog("sendRequest", url, responce);
        try {
            responceStr = request.sendRequest(requsetName, requestListMap, url);
            BufferManager.addBufferByKey(responce, responceStr);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("send Request failed: %s", ex));
        }
        LogHelper.stepAfterLog("sendRequest", responceStr);
        return this;
    }

    /**
     * 使用String格式函数Parse
     * @param convertMethodSource : parse函数字符串
     * @param convertMethodName : parse函数name
     * @param sourceParemKey : parse入参缓存Key
     * @param sourceParamType : parse入参类型
     * @param targetParemKey : parse函数返回值Key
     * @param targetParamType : parse返回值类型
     *
     */
    public TestFlowManager sourceParse(String convertMethodSource, String convertMethodName, String sourceParemKey, String sourceParamType, String targetParemKey, String targetParamType) {
        Parser parser = new Parser();
        String str;
        LogHelper.stepExecLog("sourceParse", convertMethodSource, convertMethodName, sourceParemKey, sourceParamType, targetParemKey, targetParamType);
        try {
            str = parser.parseValueVidStr(convertMethodSource, convertMethodName, sourceParemKey, sourceParamType, targetParamType);
            BufferManager.addBufferByKey(targetParemKey, str);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Init object failed"));
        }
        LogHelper.stepAfterLog("sourceParse", str);
        return this;
    }

    /**
     * 使用反射方法模式Parse（无参数类型）
     *
     * @param convertFileName : 反射类名
     * @param convertMethodName : 反射方法名
     * @param sourceParemKey : parse入参缓存Key
     * @param targetParemKey : parse返回值key
     * @return
     */
    public TestFlowManager reflectParse(String convertFileName, String convertMethodName, String sourceParemKey, String targetParemKey) {
        Parser parser = new Parser();
        String str;
        LogHelper.stepExecLog("reflectParse", convertFileName, convertMethodName, sourceParemKey, targetParemKey);
        try {
            str = parser.parseValueViaFile(convertFileName, convertMethodName, sourceParemKey);
            BufferManager.addBufferByKey(targetParemKey, str);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Reflect method \"%s\" failed: " + ex, convertMethodName));
        }
        LogHelper.stepAfterLog("reflectParse", str);
        return this;
    }

    /**
     * 使用反射方法模式Parse（一个类型参数）
     *
     * @param convertFileName : 反射类名
     * @param convertMethodName : 反射方法名
     * @param sourceParemKey : parse入参缓存Key
     * @param sourceParamType : parse入参类型
     * @param targetParemKey : parse返回值key
     *
     */
    public TestFlowManager reflectParse(String convertFileName, String convertMethodName, String sourceParemKey, String sourceParamType, String targetParemKey) {
        Parser parser = new Parser();
        String str;
        LogHelper.stepExecLog("reflectParse", convertFileName, convertMethodName, sourceParemKey, targetParemKey);
        try {
            str = parser.parseValueViaFile(convertFileName, convertMethodName, sourceParemKey, sourceParamType);
            BufferManager.addBufferByKey(targetParemKey, str);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Reflect method \"%s\" failed: " + ex, convertMethodName));
        }
        LogHelper.stepAfterLog("reflectParse", str);
        return this;
    }

    /**
     * 使用反射方法模式Parse（两个类型参数）
     *
     * @param convertFileName : 反射类名
     * @param convertMethodName : 反射方法名
     * @param sourceParemKey : parse参数一缓存Key
     * @param sourceParamType ：parse参数一类型
     * @param sourceParemKey2 : parse参数二缓存Key
     * @param sourceParamType2 ：parse参数二类型
     * @param targetParemKey : parse返回值key
     * @return
     */
    public TestFlowManager reflectParse(String convertFileName, String convertMethodName, String sourceParemKey, String sourceParamType, String sourceParemKey2, String sourceParamType2, String targetParemKey) {
        Parser parser = new Parser();
        String str;
        LogHelper.stepExecLog("reflectParse", convertFileName, convertMethodName, sourceParemKey, sourceParamType, sourceParemKey2, sourceParamType2, targetParemKey);
        try {
            str = parser.parseValueViaFile(convertFileName, convertMethodName, sourceParemKey, sourceParamType, sourceParemKey2, sourceParamType2);
            BufferManager.addBufferByKey(targetParemKey, str);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Reflect method \"%s\" failed: " + ex, convertMethodName));
        }
        LogHelper.stepAfterLog("reflectParse", str);
        return this;
    }

    /**
     * 使用反射方法模式Parse（三个类型参数）
     *
     * @param convertFileName : 反射类名
     * @param convertMethodName : 反射方法名
     * @param sourceParemKey : parse参数一缓存Key
     * @param sourceParamType ：parse参数一类型
     * @param sourceParemKey2 : parse参数二缓存Key
     * @param sourceParamType2 ：parse参数二类型
     * @param sourceParemKey3 : parse参数三缓存Key
     * @param sourceParamType3 ：parse参数三类型
     * @param targetParemKey : parse返回值key
     * @return
     */
    public TestFlowManager reflectParse(String convertFileName, String convertMethodName, String sourceParemKey, String sourceParamType, String sourceParemKey2, String sourceParamType2, String sourceParemKey3, String sourceParamType3, String targetParemKey) {
        Parser parser = new Parser();
        String str;
        LogHelper.stepExecLog("reflectParse", convertFileName, convertMethodName, sourceParemKey, sourceParamType, sourceParemKey2, sourceParamType2, sourceParemKey3, sourceParamType3, targetParemKey);
        try {
            str = parser.parseValueViaFile(convertFileName, convertMethodName, sourceParemKey, sourceParamType, sourceParemKey2, sourceParamType2, sourceParemKey3, sourceParamType3);
            BufferManager.addBufferByKey(targetParemKey, str);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Reflect method \"%s\" failed: " + ex, convertMethodName));
        }
        LogHelper.stepAfterLog("reflectParse", str);
        return this;
    }

    /**
     * 使用反射方法模式Parse（四个类型参数）
     *
     * @param convertFileName : 反射类名
     * @param convertMethodName : 反射方法名
     * @param sourceParemKey : parse参数一缓存Key
     * @param sourceParamType ：parse参数一类型
     * @param sourceParemKey2 : parse参数二缓存Key
     * @param sourceParamType2 ：parse参数二类型
     * @param sourceParemKey3 : parse参数三缓存Key
     * @param sourceParamType3 ：parse参数三类型
     * @param sourceParemKey4 : parse参数四缓存Key
     * @param sourceParamType4 ：parse参数四类型
     * @param targetParemKey : parse返回值key
     * @return
     */
    public TestFlowManager reflectParse(String convertFileName, String convertMethodName, String sourceParemKey, String sourceParamType, String sourceParemKey2, String sourceParamType2, String sourceParemKey3, String sourceParamType3, String sourceParemKey4, String sourceParamType4, String targetParemKey) {
        Parser parser = new Parser();
        String str;
        LogHelper.stepExecLog("reflectParse", convertFileName, convertMethodName, sourceParemKey, sourceParamType, sourceParemKey2, sourceParamType2, sourceParemKey3, sourceParamType3, sourceParemKey4, sourceParamType4, targetParemKey);
        try {
            str = parser.parseValueViaFile(convertFileName, convertMethodName, sourceParemKey, sourceParamType, sourceParemKey2, sourceParamType2, sourceParemKey3, sourceParamType3, sourceParemKey4, sourceParamType4);
            BufferManager.addBufferByKey(targetParemKey, str);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Reflect method \"%s\" failed: " + ex, convertMethodName));
        }
        LogHelper.stepAfterLog("reflectParse", str);
        return this;
    }

    /**
     * 使用子类重写方法模式Parse
     * @param sourceParemKey : parse入参类型
     * @param targeParemtKey : parse返回值key
     * @param dataParser : dataParser重写类
     *
     */
    public TestFlowManager overrideParse(String sourceParemKey, String targeParemtKey, DataParser dataParser) {
        Parser parser = new Parser();
        String str;
        LogHelper.stepExecLog("overrideParse", sourceParemKey, targeParemtKey);
        try {
            str = parser.parseValueVidMethod(sourceParemKey, dataParser);
            BufferManager.addBufferByKey(targeParemtKey, str);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Parse object \"%s\" failed: " + ex, targeParemtKey));
        }
        LogHelper.stepAfterLog("overrideParse", str);
        return this;
    }

    /**
     * 使用子类重写方法模式Parse
     * @param sourceParemType : parse入参缓存Key
     * @param sourceParemKey : parse入参类型
     * @param targeParemtKey : parse返回值key
     * @param dataParser : dataParser重写类
     *
     */
    public TestFlowManager overrideParse(String sourceParemType, String sourceParemKey, String targeParemtKey, DataParser dataParser) {
        Parser parser = new Parser();
        String str;
        LogHelper.stepExecLog("overrideParse", sourceParemType, sourceParemKey, targeParemtKey);
        try {
            str = parser.parseValueVidMethod(sourceParemType, sourceParemKey, dataParser);
            BufferManager.addBufferByKey(targeParemtKey, str);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Parse object \"%s\" failed: " + ex, targeParemtKey));
        }
        LogHelper.stepAfterLog("overrideParse", str);
        return this;
    }

    /**
     * 添加缓存
     *
     */
    public TestFlowManager addBuffer(String bufferKey, String bufferVal) {
        Buffer buffer = new Buffer();
        LogHelper.stepExecLog("addBuffer", bufferKey, bufferVal);
        try {
            buffer.addBufferByKey(bufferKey, bufferVal);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("add Buffer key \"%s\" value \"%s\" failed: %s", bufferKey, bufferVal, ex));
        }
        return this;
    }

    /**
     * 对比缓存中的Json
     * @param expObj : 预期值缓存Key
     * @param atlObj : 实际值缓存Key
     *
     */
    public TestFlowManager verify(String expObj, String atlObj) {
        LogHelper.stepExecLog("verify", expObj, atlObj);
        try {
            String expStr = BufferManager.getBufferByKey(expObj);
            String atlStr = BufferManager.getBufferByKey(atlObj);
            if (!expStr.equals(atlStr)) {
                deposed();
                throw new AssertionError(String.format("\n" + "expected: \"%s\" not equals with actual: \"%s\".\n", expStr, atlStr));
            }
        }
        catch (Exception ex) {
            throw new AssertionError(String.format("Verify object failed: " + ex));
        }
        return this;
    }

    /**
     * 对比两个相同类型的实体
     * @param paramType : 实体类型
     * @param expObj : 预期结果值缓存Key
     * @param expObj : 实际结果值缓存Key
     * @param expObj : 对比实体List主键
     * @param atlObj : 实体中不对比的字段
     *
     */
    public TestFlowManager verify(String paramType, String expObj, String atlObj, String pkMapStr, String noCompareItemMapStr) {
        Verify verify = new Verify();
        LogHelper.stepExecLog("verify", paramType, expObj, atlObj, pkMapStr, noCompareItemMapStr);
        try {
            String errorMsg = verify.verify(paramType, expObj, atlObj, pkMapStr, noCompareItemMapStr);
            if (!"".equals(errorMsg)) {
                deposed();
                throw new AssertionError(String.format("\n" + errorMsg));
            }
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Verify object failed: " + ex));
        }
        return this;
    }

    /**
     * 检验缓存中的Json某个key值
     * @param atlObj : 实际结果值缓存Key
     * @param JsonFilter : 定位Json的xpath
     * @param expValue : 特定key的预期值
     *
     */
    public TestFlowManager verify(String atlObj, String JsonFilter, String expValue) {
        Verify verify = new Verify();
        LogHelper.stepExecLog("verify", atlObj, JsonFilter, expValue);
        try {
            String errorMsg = verify.verify(atlObj, JsonFilter, expValue);
            if (!"".equals(errorMsg)) {
                deposed();
                throw new AssertionError(String.format("\n" + errorMsg));
            }
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Verify object failed: " + ex));
        }
        return this;
    }

    /**
     * 通过myBatis XML查询DB
     *
     * @param queryKey sql语句id
     * @param param sql map 参数
     * @param bufferKey 存入缓存中的key
     * @return
     */
    public TestFlowManager queryDataBase(String queryKey, String param, String bufferKey) {
        Database database= new Database();
        String str;
        LogHelper.stepExecLog("queryDataBase", queryKey, param, bufferKey);
        try {
            str = database.queryDataBase(queryKey, param);
            BufferManager.addBufferByKey(bufferKey, str);
        }
        catch (Exception ex) {
            deposed();
            throw new AssertionError(String.format("Query datebase failed: " + ex));
        }
        LogHelper.stepAfterLog("queryDataBase", str);
        return this;
    }
}
