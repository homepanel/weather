package com.homepanel.weather.weather.cleaner;

import com.homepanel.weather.weather.data.Weather;
import com.homepanel.weather.weather.provider.Provider;

import java.util.List;

public interface InterfaceCleaner {

    public List<Weather> execute(Provider.TYPE type, List<Weather> weathers);
}