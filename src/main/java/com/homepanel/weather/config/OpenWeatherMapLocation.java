package com.homepanel.weather.config;

import com.homepanel.weather.weather.provider.Provider;

public class OpenWeatherMapLocation extends Location {

    private UNIT unit;
    private String apiKey;

    public UNIT getUnit() {
        return unit;
    }

    private void setUnit(UNIT unit) {
        this.unit = unit;
    }

    public String getApiKey() {
        return apiKey;
    }

    private void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Provider.TYPE getProviderType() {
        return Provider.TYPE.OPEN_WEATHER_MAP;
    }
}
