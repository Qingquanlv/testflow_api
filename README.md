
<img src="https://github.com/Qingquanlv/testflow_api/blob/master/testflow.png" width = 40% height = 40% />


### introduction：
目前随着测试场景的复杂性增加，仅仅通过发送Request对比固定Responce的方式已经很难覆盖现有接口测试场景。所以这个时候你需要End-To-End testing模式的testflow_api.

### architecture：
<img src="https://github.com/Qingquanlv/testflow_api/blob/master/liuchengtu.png" width = 100% height = 100% />


#### testflow_API的优势：

#### 1. 易用性
Testflow_api目前支持两种模式，java代码模式和XML模式。一行代码搞定接口测试。

##### java代码模式：
```java
TestFlowManager.runner().sendRequest("",
                "https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=245b7545b69b4b4a9bc2a7e497a88b01",
                "weather");
```
##### xml模式：
```java
@TestFlowTest(path = "src\\main\\resources\\weather.xml")
    public void test(Map<String, String> map) {
        TestFlowFileManager.runner().executeFile(map, "src\\main\\resources\\weather.xml");
        TestFlowManager.runner().deposed();
    }
```
```xml
<request id="weather" method="post" contenttype="json">
    <url>https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=245b7545b69b4b4a9bc2a7e497a88b01</url>
    <body>
    {
    }
    </body>
</request>
```

#### 2. 行为模式封装
Testflow_api根据具体的操作步骤封装sendRquest，Parse，QueryDB，Veryfy四类方法。

##### java代码模式：
```java
TestFlowManager.runner().sendRequest("",
                "https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=245b7545b69b4b4a9bc2a7e497a88b01",
                "weather").verify("weather",
                        "/HeWeather6/*[0]/basic/location",
                        "Beijing");
```

##### xml模式：
```xml
<request id="weather" method="post" contenttype="json">
    <url>https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=245b7545b69b4b4a9bc2a7e497a88b01</url>
    <body>
    {
    }
    </body>
</request>
```

#### 3. End-To-End testing
Testflow_api提供sendRquest，Parse，QueryDB，Veryfy四类方法和缓存机制模拟完整的调用链。

#### 4. 数据驱动

Testflow_api支持数据驱动模式。

##### java代码模式：
Java代码可以采用Junit数据驱动的方法，这里就不过多介绍。

##### xml模式：
xml可以使用dataloader标签
```xml
<given>
   <dataloader>
       <parame parame1 = 'parame1' parame2 = 'parame2' />
   </dataloader>
</given>
```


#### 5. 支持Http(s)请求

Testflow_api支持多样的Http(s)请求。

#### 6. 支持DataBase操作

Testflow_api支持基于Mybatis的数据库增删改查操作。

```java
TestFlowManager.runner().queryDataBase(parmeType, sqlStr);
```

```xml
   <database id="table">
            <sql>
                SELECT * FROM table
            </sql>
        </database>
```

#### 7. 多样的断言方法

Testflow_api支持多种的断言方法。

##### 验证实体中任一字段值：

```java
verify("weather2","/HeWeather6/*[0]/basic/location","Beijing");
```

##### 直接对比两个JSON String：

```java
verify("weather1", "weather2");
```

##### 对比两个实体（包括实体中不对比key和List实体的主键）

```java
.verify("com.testflow.apitest.testentity.JsonsRootBean", //对比实体类型
                "weather1",
                "weather2",
                "Heweather6:{status}, Daily_forecast:{cond_code_d, cond_code_n}", //对比实体中的List主键
                "Daily_forecast:{wind_dir}") //对比实体中不对比字段
```





##  Documentation：

#### Getting Statted

#### 引入POM
```java
<dependency>
    <groupId>com.github.qingquanlv</groupId>
    <artifactId>testflow_api</artifactId>
    <version>2.1.1</version>
</dependency>
```

#### Junit模式/xml模式

Junit模式链接：
XML模式链接：


#### 开放方法

##### Requset：

```java
/**
* 发送Json请求
* @param requestJsonStr : Json格式请求
* @param url : 请求url
* @param responce : 保存response的key
*
*/
public TestFlowManager sendRequest(String requestJsonStr, String url, String responce) 
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
public TestFlowManager sendRequest(String requestJsonStr, String url, String responce) 
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
public TestFlowManager reflectParse(String convertFileName, String convertMethodName, String sourceParemKey, String sourceParamType, String targetParemKey)
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
public TestFlowManager overrideParse(String sourceParemType, String sourceParemKey, String targeParemtKey, DataParser dataParser)
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
public TestFlowManager sourceParse(String convertMethodSource, String convertMethodName, String sourceParemKey, String sourceParamType, String targetParemKey, String targetParamType)
```

##### Verify：

```java
/**
 * 对比缓存中的Json
 * @param expObj : 预期值缓存Key
 * @param atlObj : 实际值缓存Key
 *
 */
public TestFlowManager verify(String expObj, String atlObj)
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
public TestFlowManager verify(String paramType, String expObj, String atlObj, String pkMapStr, String noCompareItemMapStr)
```

```java
/**
 * 对比缓存中的Json
 * @param expObj : parse预期值缓存Key
 * @param atlObj : parse实际值缓存Key
 *
 */
public TestFlowManager verify(String paramType, String expObj, String atlObj, String pkMapStr, String noCompareItemMapStr)
```

##### Other：

##### Xpath读取缓存实体字段的写法
```java
${缓存Key:JSON定位串}
${weather1:/HeWeather6/*[0]/basic/location}
```

##### Cucumber request模式，请求feature文件写法
```java
And I send request "Root" to url "Url" get "targetParamKey" with
      | Key                                           | Value   |
      | Child1                                        | parame1 |
      | Child2:GrandChild                             | parame2 |
      | ListChild3:ListChild3Item[0]:GrandGrandChild1 | parame3 |
      | ListChild3:ListChild3Item[1]:GrandGrandChild1 | parame4 |
      | ListChild3:ListChild3Item[1]:GrandGrandChild2 | parame5 |
```

##### 相同类型实体，不对比字段与对比list主键的字符串写法
```java
类名1:{字段名1,字段名2},类名2:{字段名1,字段名2}
Heweather6:{status}, Daily_forecast:{cond_code_d, cond_code_n}
```


##  PS：
1. 目前发送请求只支持Json格式。
2. 目前缓存只采用Json String的格式存储。
3. 目前针对大型系统的接口测试，一般采用自动化测试代码动态计算预期值的方式。针对这种模式testflow_api可以迅速支持落地，完成高质量的测试。















