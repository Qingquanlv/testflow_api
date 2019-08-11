package com.testflow.apitest.stepdefinations;

import com.testflow.apitest.business.BufferManager;

/**
 *
 * @author qq.lv
 * @date 2019/6/15
 */
public class Buffer {

    public Object getBufferByKey(String bufferKey) {
        return BufferManager.getBufferByKey(bufferKey);
    }

    public void addBufferByKey(String bufferKey, String bufferVal) {
        BufferManager.addBufferByKey(bufferKey, bufferVal);
    }
}
