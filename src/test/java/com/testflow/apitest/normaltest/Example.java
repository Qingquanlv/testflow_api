package com.testflow.apitest.normaltest;

import com.testflow.apitest.TestFlowManager;
import com.testflow.apitest.parser.DataParser;
import com.testflow.apitest.testentity.JsonsRootBean;
import org.junit.Test;

public class Example {

    //发送请求后，验证返回报文某一节点的值
    @Test
    public void example1() {
        new TestFlowManager.Runner().sendRequest("",
                "https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=245b7545b69b4b4a9bc2a7e497a88b01",
                "weather"
        ).verify("weather",
                "/HeWeather6/*[0]/basic/location", //Xpath配置Json，验证节点的值
                "南京");
    }

    //发送请求后，验证对比返回Json String
    @Test
    public void example2() {
        new TestFlowManager.Runner().sendRequest("",
                "https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=245b7545b69b4b4a9bc2a7e497a88b01",
                "weather1"
        ).sendRequest("",
                "https://free-api.heweather.net/s6/weather/forecast?location=${weather1:/HeWeather6/*[0]/basic/location}&key=245b7545b69b4b4a9bc2a7e497a88b01",
                "weather2"
        ).verify("weather1", "weather2");
    }

    //发送请求后，验证对比返回实体的所有字段值
    @Test
    public void example3() {
        new TestFlowManager.Runner().sendRequest("",
                "https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=245b7545b69b4b4a9bc2a7e497a88b01",
                "weather1"
        ).sendRequest("",
                "https://free-api.heweather.net/s6/weather/forecast?location=${weather1:/HeWeather6/*[0]/basic/location}&key=245b7545b69b4b4a9bc2a7e497a88b01",
                "weather2"
        ).verify("com.testflow.apitest.testentity.JsonsRootBean",
                "weather1",
                "weather2",
                "Heweather6:{status}, Daily_forecast:{cond_code_d, cond_code_n}",
                "Daily_forecast:{wind_dir}");
    }

    //发送请求，使用子类重写模式parse返回报文，再验证对比返回实体的所有字段值
    @Test
    public void example4() {
        new TestFlowManager.Runner().sendRequest("",
                "https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=245b7545b69b4b4a9bc2a7e497a88b01",
                "weather1"
        ).overrideParse("com.testflow.apitest.testentity.JsonsRootBean",
                "weather1",
                "weather2", new DataParser<JsonsRootBean, JsonsRootBean>() {
                    @Override
                    public JsonsRootBean parse(JsonsRootBean sourceData) {
                        JsonsRootBean tar = new JsonsRootBean();
                        tar.setHeweather6(sourceData.getHeweather6());
                        return tar;
                    }
                }).verify("com.testflow.apitest.testentity.JsonsRootBean",
                "weather1",
                "weather2",
                "Heweather6:{status}, Daily_forecast:{cond_code_d, cond_code_n}",
                "Daily_forecast:{wind_dir}");
    }

    //发送请求，使用反射模式parse返回报文，再验证对比返回实体的所有字段值
    @Test
    public void example5() {
        new TestFlowManager.Runner().sendRequest("",
                "https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=245b7545b69b4b4a9bc2a7e497a88b01",
                "weather1"
        ).reflectParse("com.testflow.apitest.normaltest.TestMethod",
                "convertMethod",
                "weather1",
                "com.testflow.apitest.testentity.JsonsRootBean",
                "weather2"
        ).verify("com.testflow.apitest.testentity.JsonsRootBean",
                "weather1",
                "weather2",
                "Heweather6:{status}, Daily_forecast:{cond_code_d, cond_code_n}",
                "Daily_forecast:{wind_dir}");
    }

    //发送请求，使用String模式的method parse返回报文，再验证对比返回实体的所有字段值

    String javaFileSource= "public JsonsRootBean convertMethod(JsonsRootBean sourceData) {\n" +
            "        JsonsRootBean tar = new JsonsRootBean();\n" +
            "        tar.setHeweather6(sourceData.getHeweather6());\n" +
            "        return tar;\n" +
            "    }";

    @Test
    public void example6() {
        new TestFlowManager.Runner().sendRequest("",
                "https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=245b7545b69b4b4a9bc2a7e497a88b01",
                "weather1"
        ).sourceParse(javaFileSource,
                "convertMethod",
                "weather1",
                "com.testflow.apitest.testentity.JsonsRootBean",
                "weather2",
                "com.testflow.apitest.testentity.JsonsRootBean"
        ).verify("com.testflow.apitest.testentity.JsonsRootBean",
                "weather1",
                "weather2",
                "Heweather6:{status}, Daily_forecast:{cond_code_d, cond_code_n}",
                "Daily_forecast:{wind_dir}");
    }

}
