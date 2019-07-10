# testflow_api


目前随着测试场景的复杂性和测试数据的多样性，仅仅通过发送Request对比固定Responce的方式已经很难满足现有接口测试需求。所以这个时候你需要testflow_API。

# introduction：

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
每个步骤执行后都会把执行结果转化为JSON格式存入缓存，后续步骤使用key查询缓存进行后续处理（支持通过Xpath获取缓存中JSON特定字段值）。

```java
//缓存中获取的key值${weather1:/HeWeather6/*[0]/basic/location}
new TestFlowManager.Runner().sendRequest("",
                "https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=245b7545b69b4b4a9bc2a7e497a88b01",
                "weather1").sendRequest("",
                        "https://free-api.heweather.net/s6/weather/forecast?location=${weather1:/HeWeather6/*[0]/basic/location}&key=245b7545b69b4b4a9bc2a7e497a88b01",
                        "weather2").verify("weather2",
                        "/HeWeather6/*[0]/basic/location",
                        "Beijing");
```

#### 4. Parse方法
使用parse方法可以对缓存中的数据进行更复杂的处理，支持处理后存入缓存

子类重写的方式：
```java
.overrideParse("com.testflow.apitest.testentity.JsonsRootBean",
                "weather1",
                "weather2", new DataParser<JsonsRootBean, JsonsRootBean>() {
            @Override
            public JsonsRootBean parse(JsonsRootBean sourceData) {
                JsonsRootBean tar = new JsonsRootBean();
                tar.setHeweather6(sourceData.getHeweather6());
                return tar;
            }
        })
```

反射文件的方式：
```java
.reflectParse("com.testflow.apitest.normaltest.TestMethod",
                "convertMethod",
                "weather1",
                "com.testflow.apitest.testentity.JsonsRootBean",
                "weather2"
        )
```

插入String格式代码片方式：
```java
.sourceParse(javaFileSource,
                "convertMethod",
                "weather1",
                "com.testflow.apitest.testentity.JsonsRootBean",
                "weather2",
                "com.testflow.apitest.testentity.JsonsRootBean"
        )
```

#### 5. 多样的对比方法

验证实体中任一字段值：

```java
verify("weather2","/HeWeather6/*[0]/basic/location","Beijing");
```

直接对比两个JSON String：

```java
verify("weather1", "weather2");
```

对比两个实体（包括实体中不对比key和List实体的主键）

```java
.verify("com.testflow.apitest.testentity.JsonsRootBean", //对比实体类型
                "weather1",
                "weather2",
                "Heweather6:{status}, Daily_forecast:{cond_code_d, cond_code_n}", //对比实体中的List主键
                "Daily_forecast:{wind_dir}") //对比实体中不对比字段
```

#  Document：

### 接入步骤




### Junit模式/Cucumber模式

Junit：

 com\testflow\apitest\normaltest\Example.java

Cucumber：

 com\testflow\apitest\cucumber\feature\test.feature

### 开放方法

##### Requset：

```java
/**
* 发送Json请求
* @param requestJsonStr : Json格式请求
* @param url : 请求url
* @param responce : 保存response的key
*
*/
public Runner sendRequest(String requestJsonStr, String url, String responce) 
```

```java
/**
 * 发送Cucumber格式请求
 * @param requsetName : cucumber格式RequestName
 * @param requestListMap : cucumber table.asMaps 格式request参数
 * @param url : 请求url
 * @param responce : 保存response的key
 *
 */
public Runner sendRequest(String requestJsonStr, String url, String responce) 
```

##### Paser：

```java
/**
 * 使用反射方法模式Parse
 * @param convertFileName : 反射类名
 * @param convertMethodName : 反射方法名
 * @param sourceParemKey : parse入参缓存Key
 * @param sourceParamType : parse入参类型
 * @param targetParemKey : parse返回值key
 *
 */
public Runner reflectParse(String convertFileName, String convertMethodName, String sourceParemKey, String sourceParamType, String targetParemKey)
```

```java
/**
 * 使用子类重写方法模式Parse
 * @param sourceParemType : parse入参缓存Key
 * @param sourceParemKey : parse入参类型
 * @param targeParemtKey : parse返回值key
 * @param dataParser : dataParser重新类
 *
 */
public Runner overrideParse(String sourceParemType, String sourceParemKey, String targeParemtKey, DataParser dataParser)
```

```java
/**
 * 使用String格式函数Parse
 * @param convertMethodSource : parse函数字符串
 * @param convertMethodName : parse函数name
 * @param sourceParemKey : parse入参缓存Key
 * @param sourceParamType : parse入参类型
 * @param targetParemKey : parse函数返回值Key
 * @param targetParamType : parse返回值类型
 *
 */
public Runner sourceParse(String convertMethodSource, String convertMethodName, String sourceParemKey, String sourceParamType, String targetParemKey, String targetParamType)
```

##### Verify：

```java
/**
 * 对比缓存中的Json
 * @param expObj : 预期值缓存Key
 * @param atlObj : 实际值缓存Key
 *
 */
public Runner verify(String expObj, String atlObj)
```

```java
/**
 * 对比两个相同类型的实体
 * @param paramType : 实体类型
 * @param expObj : 预期结果值缓存Key
 * @param expObj : 实际结果值缓存Key
 * @param expObj : 对比实体List主键
 * @param atlObj : 实体中不对比的字段
 *
 */
public Runner verify(String paramType, String expObj, String atlObj, String pkMapStr, String noCompareItemMapStr)
```

```java
/**
 * 对比缓存中的Json
 * @param expObj : parse预期值缓存Key
 * @param atlObj : parse实际值缓存Key
 *
 */
public Runner verify(String paramType, String expObj, String atlObj, String pkMapStr, String noCompareItemMapStr)
```

##### Other：

Xpath读取缓存实体字段的写法

```java
${缓存Key:JSON定位串}
${weather1:/HeWeather6/*[0]/basic/location}
```

Cucumber request模式，请求feature文件写法

```java
And I send request "Root" to url "Url" get "targetParamKey" with
      | Key                                           | Value   |
      | Child1                                        | parame1 |
      | Child2:GrandChild                             | parame2 |
      | ListChild3:ListChild3Item[0]:GrandGrandChild1 | parame3 |
      | ListChild3:ListChild3Item[1]:GrandGrandChild1 | parame4 |
      | ListChild3:ListChild3Item[1]:GrandGrandChild2 | parame5 |
```

相同类型实体，不对比字段和对比list主键的写法

```java
类名1:{字段名1,字段名2},类名2:{字段名1,字段名2}
Heweather6:{status}, Daily_forecast:{cond_code_d, cond_code_n}
```


#  PS：
1. 目前发送请求只支持Json格式。
2. 目前缓存只采用Json String的格式存储。
3. 目前针对大型系统的接口测试，一般采用自动化测试代码动态计算预期值的方式。针对这种模式testflow_api可以迅速支持落地，完成高质量的测试。















