package com.homepanel.weather.weather.data;

import com.homepanel.weather.weather.annotation.ProviderEntry;
import com.homepanel.weather.weather.annotation.ProviderMap;
import com.homepanel.weather.weather.provider.Provider;

public class Location implements InterfaceData {

    private String title;
    private Double latitude;
    private Double longitude;
    private Double altitude;

    public String getTitle() {
        return title;
    }

    @ProviderMap({
            @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "name"),
            @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "city.name")
    })
    public void setTitle(String title) {
        this.title = title;
    }

    public Double getLatitude() {
        return latitude;
    }

    @ProviderMap({
            @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "coord.lat"),
            @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "city.coord.lat")
    })
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @ProviderMap({
            @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "coord.lon"),
            @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "city.coord.lon")
    })
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getAltitude() {
        return altitude;
    }


    @ProviderMap({
    })
    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }
}