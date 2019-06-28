package com.testflow.apitest;

import com.testflow.apitest.business.BufferManager;
import com.testflow.apitest.parser.DataParser;
import com.testflow.apitest.stepdefinations.Buffer;
import com.testflow.apitest.stepdefinations.Parser;
import com.testflow.apitest.stepdefinations.Request;
import com.testflow.apitest.stepdefinations.Verify;
import com.testflow.apitest.utilities.FastJsonUtil;

import java.util.List;
import java.util.Map;

 /**
  *
  * @author qq.lv
  * @date 2019/6/2
  */
 public class TestFlowManager {

     public static class Runner {
         private TestFlowManager crawler = new TestFlowManager();

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
          * 转化方法
          *
          */
         public Runner parse(String convertFileSource, String convertMethodName, String sourceData, String sourceDataParamType, String targetDataParamType) {
             Parser parser = new Parser();
             try {
                 Class<?> sourceDataParamTypeClazz = Class.forName(sourceDataParamType);
                 Class<?> targetDataParamTypeClazz = Class.forName(targetDataParamType);
                 parser.parseValue(convertFileSource,
                         convertMethodName,
                         BufferManager.getBufferByKey(sourceData),
                         sourceDataParamTypeClazz,
                         targetDataParamTypeClazz
                 );
             }
             catch (Exception ex) {
                 System.out.println(String.format("Init object failed"));
             }
             return this;
         }

         /**
          * 转化方法
          *
          */
         public Runner parse(String convertFileName, String convertFileSource, String convertMethodName, String sourceData, String sourceDataParamType, String targetDataParamType) {
             Parser parser = new Parser();
             try {
                 Class<?> sourceDataParamTypeClazz = Class.forName(sourceDataParamType);
                 Class<?> targetDataParamTypeClazz = Class.forName(targetDataParamType);
                 parser.convertSourceToTarget(convertFileName,
                         convertFileSource,
                         convertMethodName,
                         BufferManager.getBufferByKey(sourceData),
                         sourceDataParamTypeClazz,
                         targetDataParamTypeClazz
                 );
             }
             catch (Exception ex) {
                 System.out.println(String.format("Init object failed"));
             }
             return this;
         }

         /**
          * 转化方法
          *
          */
         public Runner parse(String sourceKey, String targetKey, DataParser dataParser) {
             try {
                 BufferManager.addBufferByKey(targetKey,
                         dataParser.parse(FastJsonUtil.jsonToBean(BufferManager.getBufferByKey(sourceKey).toString(),
                                 Class.forName(sourceKey))));
             }
             catch (Exception ex) {
                 System.out.println(String.format("Parse object failed"));
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
         public String verify(String expObj, String atlObj) {
             Verify verify = new Verify();
             return verify.Verify(BufferManager.getBufferByKey(expObj),
                     BufferManager.getBufferByKey(atlObj)
             );
         }

     }
 }
