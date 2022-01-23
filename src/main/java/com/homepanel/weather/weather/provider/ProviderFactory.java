package com.homepanel.weather.weather.provider;

import java.util.HashMap;
import java.util.Map;

public class ProviderFactory {

    private static Map<Provider.TYPE, Provider> PROVIDERS = new HashMap<>();

    public static Provider getConverter(Provider.TYPE type) {
        if (!PROVIDERS.containsKey(type)) {
            switch (type) {
                case OPEN_WEATHER_MAP:
                    PROVIDERS.put(Provider.TYPE.OPEN_WEATHER_MAP, new OpenWeatherMapProvider());
                    break;
            }
        }

        return PROVIDERS.get(type);
    }
}