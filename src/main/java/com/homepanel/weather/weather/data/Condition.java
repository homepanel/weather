package com.homepanel.weather.weather.data;

import com.homepanel.weather.weather.annotation.ProviderEntry;
import com.homepanel.weather.weather.annotation.ProviderMap;
import com.homepanel.weather.weather.converter.Converter;
import com.homepanel.weather.weather.provider.Provider;

import java.time.LocalDateTime;

public class Condition implements InterfaceData {

    private String text;
    private LocalDateTime observationTime;
    private String icon;
    private Integer icondId;
    private LocalDateTime lastUpdate;

    public String getText() {
        return text;
    }

    @ProviderMap({
        @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "weather.0.description")
    })
    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getObservationTime() {
        return observationTime;
    }

    @ProviderMap({
        @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "dt")
    })
    public void setObservationTime(LocalDateTime observationTime) {
        this.observationTime = observationTime;
    }

    public String getIcon() {
        return icon;
    }

    @ProviderMap({
        @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "weather.0.id", converterType = Converter.TYPE.ICON)
    })
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getIcondId() {
        return icondId;
    }

    @ProviderMap({
            @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "weather.0.id")
    })
    public void setIcondId(Integer icondId) {
        this.icondId = icondId;
    }

    private LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}