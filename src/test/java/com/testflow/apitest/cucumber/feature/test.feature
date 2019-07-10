@CreatePrivateTestWithAPI
Feature: Create Private Test With API
Feature Info: Prepare test data - Private Test

  Scenario: Query weather
  #查询北京地区天气，通过${weather:/HeWeather6/*[0]/basic/location}获取上面step中的参数
    When I send JSON request "" to url "https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=245b7545b69b4b4a9bc2a7e497a88b01" get "weather1"
    And I send JSON request "" to url "https://free-api.heweather.net/s6/weather/forecast?location=${weather1:/HeWeather6/*[0]/basic/location}&key=245b7545b69b4b4a9bc2a7e497a88b01" get "weather2"
    And I parse data from via file "com.testflow.apitest.normaltest.TestMethod" method "convertMethod" from "weather1" type "com.testflow.apitest.testentity.JsonsRootBean" to "weather3"
    Then I verify expected "weather1" actual "weather2"
    #And I verify expected "weather1" actual "weather3"
    And I verify object type "com.testflow.apitest.testentity.JsonsRootBean" expect key "weather1" actual value "weather2" pkMap "Heweather6:{status}, Daily_forecast:{cond_code_d, cond_code_n}" noComppareItemMap "Daily_forecast:{wind_dir}"
    And I verify object "weather1" key "/HeWeather6/*[0]/basic/location" value "南京"
