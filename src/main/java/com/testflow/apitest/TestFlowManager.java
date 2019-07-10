package com.testflow.apitest;

import com.testflow.apitest.business.BufferManager;
import com.testflow.apitest.parser.DataParser;
import com.testflow.apitest.servicesaccess.ServiceAccess;
import com.testflow.apitest.stepdefinations.Buffer;
import com.testflow.apitest.stepdefinations.Parser;
import com.testflow.apitest.stepdefinations.Request;
import com.testflow.apitest.stepdefinations.Verify;
import com.testflow.apitest.utilities.FastJsonUtil;
import com.testflow.apitest.utilities.ParamUtil;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

/**
 *
 * @author qq.lv
 * @date 2019/6/2
 */
public class TestFlowManager {

    public static class Runner {

        /**
         * 初始化Buffer
         *
         */
        public Runner() {
            BufferManager.initBufferMap();
        }

        /**
         * 发送Json请求
         * @param requestJsonStr : Json格式请求
         * @param url : 请求url
         * @param responce : 保存response的key
         *
         */
        public Runner sendRequest(String requestJsonStr, String url, String responce) {
            Request request = new Request();
            request.sendRequest(requestJsonStr, url, responce);
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
        public Runner sendRequest(String requsetName, List<Map<String, String>> requestListMap, String url, String responce) {
            Request request = new Request();
            request.sendRequest(requsetName, requestListMap, url, responce);
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
        public Runner sourceParse(String convertMethodSource, String convertMethodName, String sourceParemKey, String sourceParamType, String targetParemKey, String targetParamType) {
            Parser parser = new Parser();
            try {
                parser.parseValueVidStr(convertMethodSource, convertMethodName, sourceParemKey, sourceParamType, targetParemKey, targetParamType);
            }
            catch (Exception ex) {
                System.out.println(String.format("Init object failed"));
            }
            return this;
        }

        /**
         * 使用反射方法模式Parse
         * @param convertFileName : 反射类名
         * @param convertMethodName : 反射方法名
         * @param sourceParemKey : parse入参缓存Key
         * @param sourceParamType : parse入参类型
         * @param targetParemKey : parse返回值key
         *
         */
        public Runner reflectParse(String convertFileName, String convertMethodName, String sourceParemKey, String sourceParamType, String targetParemKey) {
            Parser parser = new Parser();
            try {
                parser.parseValueViaFile(convertFileName, convertMethodName, sourceParemKey, sourceParamType, targetParemKey);
            }
            catch (Exception ex) {
                System.out.println(String.format("Init object failed" + ex));
            }
            return this;
        }

        /**
         * 使用子类重写方法模式Parse
         * @param sourceParemType : parse入参缓存Key
         * @param sourceParemKey : parse入参类型
         * @param targeParemtKey : parse返回值key
         * @param dataParser : dataParser重新类
         *
         */
        public Runner overrideParse(String sourceParemType, String sourceParemKey, String targeParemtKey, DataParser dataParser) {
            try {
                BufferManager.addBufferByKey(targeParemtKey,
                        FastJsonUtil.toJson(dataParser.parse(FastJsonUtil.toBean(BufferManager.getBufferByKey(sourceParemKey),
                                ServiceAccess.reflectClazz(sourceParemType)))));
            }
            catch (Exception ex) {
                System.out.println(String.format("Parse object \"%s\" failed: " + ex, targeParemtKey));
            }
            return this;
        }


        /**
         * 获取缓存
         *
         */
        public Object getBuffer(String bufferKey) {
            Buffer buffer = new Buffer();
            return buffer.getBufferByKey(bufferKey);
        }

        /**
         * 对比缓存中的Json
         * @param expObj : 预期值缓存Key
         * @param atlObj : 实际值缓存Key
         *
         */
        public Runner verify(String expObj, String atlObj) {
            if (!BufferManager.getBufferByKey(expObj).equals(BufferManager.getBufferByKey(atlObj))) {
                Assert.fail("\n" + String.format("expected: \"%s\" not equals with actual: \"%s\".\n", BufferManager.getBufferByKey(expObj), BufferManager.getBufferByKey(atlObj)));
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
        public Runner verify(String paramType, String expObj, String atlObj, String pkMapStr, String noCompareItemMapStr) {
            Verify verify = new Verify();
            String errorMsg = verify.Verify(FastJsonUtil.toBean(BufferManager.getBufferByKey(expObj), ServiceAccess.reflectClazz(paramType)),
                    FastJsonUtil.toBean(BufferManager.getBufferByKey(atlObj), ServiceAccess.reflectClazz(paramType)),
                    pkMapStr,
                    noCompareItemMapStr);
            if (!"".equals(errorMsg)) {
                Assert.fail("\n" + errorMsg);
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
        public Runner verify(String atlObj, String JsonFilter, String expValue) {
            Assert.assertEquals(ParamUtil.getMapValFromJson(BufferManager.getBufferByKey(atlObj), JsonFilter), expValue);
            return this;
        }
    }
}
