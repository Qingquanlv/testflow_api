
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
```xml
<request id="weather" method="post" contenttype="json">
    <url>https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=245b7545b69b4b4a9bc2a7e497a88b01</url>
    <body>
    {
    }
    </body>
</request>
```
```java
@TestFlowTest(path = "src\\main\\resources\\weather.xml")
    public void test(Map<String, String> map) {
        TestFlowFileManager.runner().executeFile(map, "src\\main\\resources\\weather.xml");
        TestFlowManager.runner().deposed();
    }
```


#### 2. End-To-End testing
Testflow_api提供sendRquest，Parse，QueryDB，Veryfy四类方法和缓存机制模拟完整的调用链。

##### java代码模式：
```java
    //发送请求，使用子类重写模式parse返回报文，再验证对比返回实体的所有字段值
    @Test
    public void example4() {
        TestFlowManager.runner().sendRequest("",
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
```

##### xml模式：
```xml
<feature name="weather">
    <given>
        <dataloader>
            <parame parame1 = 'parame1' />
        </dataloader>

        <classloader>
            <!--<class>src\main\resources\clazz</class>-->
        </classloader>
        <packageloader>
            <!--<package>src\main\resources\packages</package>-->
        </packageloader>
    </given>

    <when>
        <request id="weather1" method="post" contenttype="json">
            <url>https://free-api.heweather.net/s6/weather/forecast?location=beijing&amp;key=245b7545b69b4b4a9bc2a7e497a88b01</url>
            <body>
                {

                }
            </body>
        </request>

        <parse id="weather2" type="source">
            <source>
                <parame type = 'com.testflow.apitest.testentity.JsonsRootBean' value = 'weather1'/>
                <return type = 'com.testflow.apitest.testentity.JsonsRootBean'/>
                <method name = 'convertMethod'>
                    public JsonsRootBean convertMethod(JsonsRootBean sourceData) {
                        JsonsRootBean tar = new JsonsRootBean();
                        tar.setHeweather6(sourceData.getHeweather6());
                        return tar;
                    }
                </method>
            </source>
        </parse>
    </when>

    <then>
        <verify id="verify" type="entity">
            <entitys type="com.testflow.apitest.testentity.JsonsRootBean">
                <entity>weather1</entity>
                <entity>weather2</entity>
            </entitys>
            <pkkeys>
                <pkkey>Heweather6:{status}</pkkey>
                <pkkey>Daily_forecast:{cond_code_d, cond_code_n}</pkkey>
            </pkkeys>
            <nocomparekeys>
                <nocomparekey>Daily_forecast:{wind_dir}</nocomparekey>
            </nocomparekeys>
        </verify>
    </then>

    <config>
    </config>
</feature>
```

#### 3. 数据驱动

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

#### 4. 支持DataBase操作

Testflow_api支持基于Mybatis的数据库增删改查操作。

##### java代码模式：
```java
TestFlowManager.runner().queryDataBase(parmeType, sqlStr);
```

##### xml模式：
```xml
   <database id="table">
        <sql>
           SELECT * FROM table
        </sql>
   </database>
```

#### 5. 多样的断言方法

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


```java
<verify id="verify" type="entity">
    <entitys type="com.testflow.apitest.testentity.JsonsRootBean">
        <entity>weather1</entity>
        <entity>weather2</entity>
    </entitys>
    <pkkeys>
        <pkkey>Heweather6:{status}</pkkey>
        <pkkey>Daily_forecast:{cond_code_d, cond_code_n}</pkkey>
    </pkkeys>
    <nocomparekeys>
        <nocomparekey>Daily_forecast:{wind_dir}</nocomparekey>
    </nocomparekeys>
</verify>
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

##### 相同类型实体，不对比字段与对比list主键的字符串写法
```java
类名1:{字段名1,字段名2},类名2:{字段名1,字段名2}
Heweather6:{status}, Daily_forecast:{cond_code_d, cond_code_n}
```


##  Contuct me
  qingquanlv@gmail.com















