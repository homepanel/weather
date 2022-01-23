package com.homepanel.weather.weather.data;

import com.homepanel.weather.weather.annotation.ProviderEntry;
import com.homepanel.weather.weather.annotation.ProviderMap;
import com.homepanel.weather.weather.provider.Provider;

public class Temperature implements InterfaceData {

    private Double current;
    private Double min;
    private Double max;
    private Double feeling;
    private Double dewPoint;

    public Double getCurrent() {
        return current;
    }

    @ProviderMap({
            @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "main.temp")
    })
    public void setCurrent(Double current) {
        this.current = current;
    }

    public Double getMin() {
        return min;
    }

    @ProviderMap({
            @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "main.temp_min")
    })
    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    @ProviderMap({
            @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "main.temp_max")
    })
    public void setMax(Double max) {
        this.max = max;
    }

    public Double getFeeling() {
        return feeling;
    }

    @ProviderMap({
            @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "main.feels_like")
    })
    public void setFeeling(Double feeling) {
        this.feeling = feeling;
    }

    public Double getDewPoint() {
        return dewPoint;
    }

    @ProviderMap({
    })
    public void setDewPoint(Double dewPoint) {
        this.dewPoint = dewPoint;
    }
}