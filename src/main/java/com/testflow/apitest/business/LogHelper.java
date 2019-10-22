package com.testflow.apitest.business;

import java.util.Date;
import com.testflow.apitest.TestFlowManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogHelper {
    public static Integer index;
    private static Logger logger = LoggerFactory.getLogger(TestFlowManager.class);

    public static void initLog()
    {
        if(index == null) {
            index = 1;
        }
    }

    public static void deposeLog() {
        index = 1;
    }

    public static void stepExecLog(String stepName, String...stepParam) {
        String param = "";
        for (int i=0;i<stepParam.length;i++) {
            param +=stepParam[i] + ",";
        }
        logger.info(String.format("*********************************************************************************************************************************************************************************************************"));
        logger.info(String.format("Start execute %s step: step Name \'%s\' step Param \'%s\'.", index, stepName, param));
    }

    public static void stepAfterLog(String stepName, String buffer) {
        logger.info(String.format("Step %s Passed: buffer key\'%s\' buffer \'%s\'.", index, stepName, buffer));
        index ++;
    }
}
