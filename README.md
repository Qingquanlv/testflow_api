# testflow_api


# introduction
目前随着测试场景的复杂性和测试数据的多样性，仅仅通过发送请求对比固定返回值的方式已经很难满足现有测试需求。所以这个时候你需要testflow_API。
testflow_API具有一下几个特点：
1.易用性
一行代码搞定接口测试：
new TestFlowManager.Runner().sendRequest("",
                "https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=245b7545b69b4b4a9bc2a7e497a88b01",
                "weather").verify("weather",
                        "/HeWeather6/*[0]/basic/location",
                        "Beijing");

2.可读性
关键字根据具体动作进行封装，无缝连接Cucumber（Cucumber接入方式见test下的例子）

new TestFlowManager.Runner().sendRequest("",
                "https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=245b7545b69b4b4a9bc2a7e497a88b01",
                "weather");

  Scenario: Query weather
  #查询北京地区天气 
    When I send JSON request "" to url "https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=245b7545b69b4b4a9bc2a7e497a88b01" get "weather"

3.缓存上下文功能
每个步骤执行后都会把执行结果转化为JOSN格式存入缓存，后续步骤使用key查询缓存，也可通过xpath匹配缓存JSON中具体字段的值。

4.

# Document

接入步骤
1.maven pom导入

2.Junit模式/Cucumber模式

3.开发方法




# PS
1. 目前发送请求只支持Json格式。
2. 目前缓存只采用Json String的格式存储。
3. 目前针对大型系统的接口测试，一般采用自动化测试代码动态计算与预期值的方式。使用testflow_api可以迅速支持落地，进行测试。


