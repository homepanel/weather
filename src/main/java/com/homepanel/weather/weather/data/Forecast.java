package com.homepanel.weather.weather.data;

import com.homepanel.weather.weather.annotation.ForecastEntry;
import com.homepanel.weather.weather.annotation.ForecastMap;
import com.homepanel.weather.weather.annotation.ProviderEntry;
import com.homepanel.weather.weather.annotation.ProviderMap;
import com.homepanel.weather.weather.provider.Provider;

import java.util.ArrayList;
import java.util.List;

public class Forecast implements InterfaceData {

    private String error;
    private Integer responseCode;
    private Location location;
    private List<Weather> weathers;

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

    public List<Weather> getWeathers() {
        return weathers;
    }

    @ForecastMap({
            @ForecastEntry(providerType = Provider.TYPE.OPEN_WEATHER_MAP, property = "list")
    })
    public void setWeathers(List<Weather> weathers) {
        this.weathers = weathers;
    }

    public Forecast() {
        setWeathers(new ArrayList<>());
    }
}