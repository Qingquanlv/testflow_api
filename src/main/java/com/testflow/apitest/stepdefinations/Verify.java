package com.testflow.apitest.stepdefinations;

import com.testflow.apitest.utilities.ParamUtil;
import com.testflow.apitest.utilities.VerifyUtil;

import java.util.List;
import java.util.Map;

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

    /**
     * 对比实体
     *
     */
    public String Verify(Object expObj, Object atlObj, String pkMapStr, String noCompareItemMapStr) {
        Map<String, List<String>> pkMap = ParamUtil.parseVerifyParam(pkMapStr);
        Map<String, List<String>> noCompareItemMap = ParamUtil.parseVerifyParam(noCompareItemMapStr);
        VerifyUtil compareUtil = new VerifyUtil();
        compareUtil.compareEntity(expObj, atlObj, pkMap, noCompareItemMap);
        return compareUtil.getErrorMsg();
    }
}
