package com.homepanel.weather.weather.data;

import com.homepanel.weather.weather.annotation.ProviderEntry;
import com.homepanel.weather.weather.annotation.ProviderMap;
import com.homepanel.weather.weather.provider.Provider;

public class Clouds implements InterfaceData {

    private Integer percent;

    public Integer getPercent() {
        return percent;
    }

    @ProviderMap({
        @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "clouds.all")
    })
    public void setPercent(Integer percent) {
        this.percent = percent;
    }
}