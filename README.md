# testflow_api


目前随着测试场景的复杂性和测试数据的多样性，仅仅通过发送请求对比固定返回值的方式已经很难满足现有接口测试需求。所以这个时候你需要testflow_API。

## introduction

testflow_API的优势：

#### 1. 易用性
一行代码搞定接口测试：

```java
new TestFlowManager.Runner().sendRequest("",
                "https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=245b7545b69b4b4a9bc2a7e497a88b01",
                "weather").verify("weather",
                        "/HeWeather6/*[0]/basic/location",
                        "Beijing");
```

#### 2. 可读性
方法根据具体执行动作进行封装，无缝连接Cucumber（Cucumber接入方式见test下的例子）

junit模式：
```java
new TestFlowManager.Runner().sendRequest("",
                "https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=245b7545b69b4b4a9bc2a7e497a88b01",
                "weather");
```
Cucumber模式：
```ruby
Scenario: Query weather
  #查询北京地区天气 
    When I send JSON request "" to url "https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=245b7545b69b4b4a9bc2a7e497a88b01" get "weather"
```

#### 3. 上下文功能
每个步骤执行后都会把执行结果转化为JSON格式存入缓存，后续步骤使用key查询缓存，也可通过xpath匹配缓存JSON中具体字段的值。

缓存中获取的key值${weather1:/HeWeather6/*[0]/basic/location}：
```java
new TestFlowManager.Runner().sendRequest("",
                "https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=245b7545b69b4b4a9bc2a7e497a88b01",
                "weather1").sendRequest("",
                        "https://free-api.heweather.net/s6/weather/forecast?location=${weather1:/HeWeather6/*[0]/basic/location}&key=245b7545b69b4b4a9bc2a7e497a88b01",
                        "weather2").verify("weather2",
                        "/HeWeather6/*[0]/basic/location",
                        "Beijing");
```

#### 4. Parse方法
使用parse方法可以承接缓存中的数据进行处理，然后存入缓存供后续步骤使用。parse方式主要用于计算预期结果值。

子类重写的方式：

反射文件的方式：

插入String格式代码片：

#### 5. 多样的对比方法

对比实体中的任意key值：
```java
verify("weather2","/HeWeather6/*[0]/basic/location","Beijing");
```

直接对比两个JSON String：

```java
verify("weather1", "weather2");
```

对比两个实体（包括实体中不对比key和List实体的主键）

```java
verify("weather1","weather2",
                "weather", //对比实体类型
                "daily_forecast:{cond_code_d, cond_code_n}", //实体中不对比key
                ""); //对比List实体的主键
```

##  Document

接入步骤
1.maven pom导入

2.Junit模式/Cucumber模式

3.开放方法




##  PS
1. 目前发送请求只支持Json格式。
2. 目前缓存只采用Json String的格式存储。
3. 目前针对大型系统的接口测试，一般采用自动化测试代码动态计算与预期值的方式。使用testflow_api可以迅速支持落地，进行测试。















