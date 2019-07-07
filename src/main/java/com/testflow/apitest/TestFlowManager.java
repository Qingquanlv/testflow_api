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
         *
         */
        public Runner sendRequest(String requestJsonStr, String url, String responce) {
            Request request = new Request();
            request.sendRequest(requestJsonStr, url, responce);
            return this;
        }

        public Runner sendRequest(String requsetName, List<Map<String, String>> requestListMap, String url, String responce) {
            Request request = new Request();
            request.sendRequest(requsetName, requestListMap, url, responce);
            return this;
        }

        /**
         * 转化方法(String参数)
         *
         */
        public Runner parseS(String convertFileSource, String convertMethodName, String sourceData, String sourceDataParamType, String targetDataParamType) {
            Parser parser = new Parser();
            try {
                parser.parseValueVidStr(convertFileSource, convertMethodName, sourceData, sourceDataParamType, targetDataParamType);
            }
            catch (Exception ex) {
                System.out.println(String.format("Init object failed"));
            }
            return this;
        }

        /**
         * 转化方法(java文件)
         *
         */
        public Runner parseR(String convertFileName, String convertMethodName, String sourceData, String sourceDataParamType, String targetDataParamType) {
            Parser parser = new Parser();
            try {
                parser.parseValueViaFile(convertFileName, convertMethodName, sourceData, sourceDataParamType, targetDataParamType);
            }
            catch (Exception ex) {
                System.out.println(String.format("Init object failed" + ex));
            }
            return this;
        }

        /**
         * 转化方法（重写方式）
         *
         */
        public Runner parseO(String sourceKey, String targetKey, DataParser dataParser) {
            try {
                BufferManager.addBufferByKey(targetKey,
                        FastJsonUtil.toJson(dataParser.parse(FastJsonUtil.toBean(BufferManager.getBufferByKey(sourceKey),
                                Class.forName(sourceKey)))));
            }
            catch (Exception ex) {
                System.out.println(String.format("Parse object \"%s\" failed: " + ex, targetKey));
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
         * 对比实体
         *
         */
        public Runner verify(String expObj, String atlObj) {
            if (!BufferManager.getBufferByKey(expObj).equals(BufferManager.getBufferByKey(atlObj))) {
                Assert.fail("\n" + String.format("expected: \"%s\" not equals with actual: \"%s\".\n", expObj, atlObj));
            }
            return this;
        }

        /**
         * 对比实体(包含对比List主键和实体中不对比属性)
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
         * 对比下某属性实体
         *
         */
        public Runner verify(String expObj, String JsonFilter, String expValue) {
            Assert.assertEquals(ParamUtil.getMapValFromJson(BufferManager.getBufferByKey(expObj), JsonFilter), expValue);
            return this;
        }
    }
}
