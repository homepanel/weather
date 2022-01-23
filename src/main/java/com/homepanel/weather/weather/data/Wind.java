package com.homepanel.weather.weather.data;

import com.homepanel.weather.weather.annotation.ProviderEntry;
import com.homepanel.weather.weather.annotation.ProviderMap;
import com.homepanel.weather.weather.converter.Converter;
import com.homepanel.weather.weather.provider.Provider;

public class Wind implements InterfaceData {

    private Double speed;
    private String direction;
    private Integer degree;
    private Double gust;
    private Double chill;

    public Double getSpeed() {
        return speed;
    }

    @ProviderMap({
            @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "wind.speed")
    })
    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public String getDirection() {
        return direction;
    }

    @ProviderMap({
            @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "wind.deg", converterType = Converter.TYPE.WIND_DIRECTION)
    })
    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Integer getDegree() {
        return degree;
    }

    @ProviderMap({
            @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "wind.deg")
    })
    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public Double getGust() {
        return gust;
    }

    @ProviderMap({
    })
    public void setGust(Double gust) {
        this.gust = gust;
    }

    public Double getChill() {
        return chill;
    }

    @ProviderMap({
            @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "gust")
    })
    public void setChill(Double chill) {
        this.chill = chill;
    }
}