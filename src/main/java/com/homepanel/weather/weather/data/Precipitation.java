package com.homepanel.weather.weather.data;

import com.homepanel.weather.weather.annotation.ProviderEntry;
import com.homepanel.weather.weather.annotation.ProviderMap;
import com.homepanel.weather.weather.converter.Converter;
import com.homepanel.weather.weather.provider.Provider;

public class Precipitation implements InterfaceData {

    private Double rain;
    private Double snow;
    private Integer probability;

    public Double getRain() {
        return rain;
    }

    @ProviderMap({
            @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "rain.3h", converterType = Converter.TYPE.DOUBLE_3H),
            @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "rain.1h")
    })
    public void setRain(Double rain) {
        this.rain = rain;
    }

    public Double getSnow() {
        return snow;
    }

    @ProviderMap({
            @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "snow.3h", converterType = Converter.TYPE.DOUBLE_3H),
            @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "snow.1h")
    })
    public void setSnow(Double snow) {
        this.snow = snow;
    }

    public Integer getProbability() {
        return probability;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }
}