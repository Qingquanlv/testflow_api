package com.testflow.apitest.stepdefinations;

import com.testflow.apitest.utilities.VerifyUtil;

/**
 *
 * @author qq.lv
 * @date 2019/6/15
 */
public class Verify {

    /**
     * 对比实体
     *
     */
    public String Verify(Object expObj, Object atlObj) {
        VerifyUtil compareUtil = new VerifyUtil();
        compareUtil.compareEntity(expObj, atlObj);
        return compareUtil.getErrorMsg();
    }
}
