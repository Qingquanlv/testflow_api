package com.testflow.apitest.testentity;

import java.util.List;

public class Heweather6 {

    private Basic basic;
    private Update update;
    private String status;
    private List<Daily_forecast> daily_forecast;
    public void setBasic(Basic basic) {
        this.basic = basic;
    }
    public Basic getBasic() {
        return basic;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }
    public Update getUpdate() {
        return update;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setDaily_forecast(List<Daily_forecast> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }
    public List<Daily_forecast> getDaily_forecast() {
        return daily_forecast;
    }

}