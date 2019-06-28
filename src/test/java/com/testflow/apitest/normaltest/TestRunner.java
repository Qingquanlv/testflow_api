package com.testflow.apitest.normaltest;

import com.testflow.apitest.TestFlowManager;
import com.testflow.apitest.parser.DataParser;

public class TestRunner {

    public static void main(String[] args) {
        new TestFlowManager.Runner().sendRequest("",
                "https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=245b7545b69b4b4a9bc2a7e497a88b01",
                "weather").parse("abc", "abc" ,new DataParser<Object>() {
            @Override
            public void parse(String sourceDataType, String sourceDataParamType, String sourceData) {


            }
        });

    }
}
