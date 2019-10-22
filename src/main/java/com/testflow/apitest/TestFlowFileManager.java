package com.testflow.apitest;

import com.testflow.apitest.stepdefinations.FileExecutor;

import java.util.Map;

public class TestFlowFileManager {

    private static class FileSingleHolder{
        private static TestFlowFileManager instance = new TestFlowFileManager();
    }

    /**
     * 私有化构造方法
     */
    private TestFlowFileManager(){

    }

    /**
     * 初始化实例，初始化缓存
     *
     */
    public static TestFlowFileManager runner(){
        return FileSingleHolder.instance;
    }

    /**
     * 通过xml文件执行（一行参数）
     *
     * @param path
     * @return
     */
    public TestFlowFileManager executeFile(String path) {
        try {
            FileExecutor fileExecutor= new FileExecutor();
            fileExecutor.executeFile(path);
        }
        catch (Exception ex) {
            throw new AssertionError(String.format("send Request failed: %s", ex));
        }
        return this;
    }

    /**
     * 通过xml文件执行数据驱动
     *
     * @param path
     * @return
     */
    public TestFlowFileManager executeFile(Map<String, String> map, String path) {
        try {
            FileExecutor fileExecutor= new FileExecutor();
            fileExecutor.executeFile(map, path);
        }
        catch (Exception ex) {
            throw new AssertionError(String.format("send Request failed: %s", ex));
        }
        return this;
    }

}
