package com.testflow.apitest.stepdefinations;

import com.testflow.apitest.business.BufferManager;
import com.testflow.apitest.servicesaccess.ServiceAccess;
import com.testflow.apitest.utilities.FastJsonUtil;
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
     * 对比Json某一节点
     *
     */
    public String verify(String atlObj, String JsonFilter, String expValue) throws Exception {
        String errMsg = "";
        String atlStr = BufferManager.getBufferByKey(atlObj);

        List<Object> objList = ParamUtil.getMapValFromJson(atlStr, JsonFilter);
        if (null == objList || objList.isEmpty()) {
            throw new Exception(String.format("No matiched value for key \"%s\" Json string \"%s\" .", atlStr, JsonFilter));
        }
        if (!objList.get(0).toString().equals(expValue)) {
            errMsg =  String.format("\n" + "expected: \"%s\" is not equal with actual: \"%s\".", expValue, objList.get(0).toString());
        }
        return errMsg;
    }

    /**
     * 对比实体
     *
     */
    public String verify(String paramType, String expStr, String atlStr, String pkMapStr, String noCompareItemMapStr) throws Exception {
        Object expObj = ParamUtil.getParameType(expStr, paramType);
        Object atlObj = ParamUtil.getParameType(atlStr, paramType);
        //primary key map
        Map<String, List<String>> pkMap = ParamUtil.parseVerifyParam(pkMapStr);
        //no compare map
        Map<String, List<String>> noCompareItemMap = ParamUtil.parseVerifyParam(noCompareItemMapStr);
        VerifyUtil compareUtil = new VerifyUtil();
        compareUtil.compareEntity(expObj, atlObj, pkMap, noCompareItemMap);
        return compareUtil.getErrorMsg();
    }
}
