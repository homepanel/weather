package com.homepanel.weather.weather.data;

import com.homepanel.weather.weather.annotation.ProviderEntry;
import com.homepanel.weather.weather.annotation.ProviderMap;
import com.homepanel.weather.weather.provider.Provider;

public class Atmosphere implements InterfaceData {

    private Integer humidity;
    private Double visibility;
    private Double pressure;
    private Integer ozone;
    private Integer uvIndex;

    public Integer getHumidity() {
        return humidity;
    }

    @ProviderMap({
        @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "main.humidity")
    })
    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getVisibility() {
        return visibility;
    }

    @ProviderMap({
    })
    public void setVisibility(Double visibility) {
        this.visibility = visibility;
    }

    public Double getPressure() {
        return pressure;
    }

    @ProviderMap({
        @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "main.pressure")
    })
    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Integer getOzone() {
        return ozone;
    }

    @ProviderMap({
    })
    public void setOzone(Integer ozone) {
        this.ozone = ozone;
    }

    public Integer getUvIndex() {
        return uvIndex;
    }

    @ProviderMap({
    })
    public void setUvIndex(Integer uvIndex) {
        this.uvIndex = uvIndex;
    }
}