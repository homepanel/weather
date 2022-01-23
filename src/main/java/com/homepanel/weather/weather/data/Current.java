package com.homepanel.weather.weather.data;

import com.homepanel.weather.weather.annotation.ProviderEntry;
import com.homepanel.weather.weather.annotation.ProviderMap;
import com.homepanel.weather.weather.provider.Provider;

public class Current extends Weather {

    private String error;
    private Integer responseCode;
    private Location location;

    public String getError() {
        return error;
    }

    @ProviderMap({
    })
    public void setError(String error) {
        this.error = error;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    @ProviderMap({
            @ProviderEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "cod")
    })
    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Current() {
        super();
        setLocation(new Location());
    }
}