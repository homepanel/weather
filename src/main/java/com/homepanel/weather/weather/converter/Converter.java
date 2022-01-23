package com.homepanel.weather.weather.converter;

import com.homepanel.weather.weather.provider.Provider;

public abstract class Converter<T> {

    public enum TYPE {
        AUTO,
        ICON,
        DOUBLE_3H,
        WIND_DIRECTION
    }

    public abstract T convert(Provider.TYPE providerType, Object value, Class setterType) throws Exception;
}