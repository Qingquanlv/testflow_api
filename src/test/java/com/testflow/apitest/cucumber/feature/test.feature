@CreatePrivateTestWithAPI
Feature: Create Private Test With API
Feature Info: Prepare test data - Private Test

  Scenario: Query weather
  #查询北京地区天气
    When I send JSON request "" to url "https://free-api.heweather.net/s6/weather/forecast?location=beijing&key=245b7545b69b4b4a9bc2a7e497a88b01" get "weather"
    When I send JSON request "" to url "https://free-api.heweather.net/s6/weather/forecast?location=${weather:/HeWeather6/*[0]/basic/location}&key=245b7545b69b4b4a9bc2a7e497a88b01" get "weather"

