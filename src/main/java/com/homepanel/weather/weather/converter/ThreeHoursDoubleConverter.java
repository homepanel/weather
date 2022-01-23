package com.homepanel.weather.weather.converter;

import com.homepanel.weather.weather.provider.Provider;

public class ThreeHoursDoubleConverter extends Converter<Double> {

    @Override
    public Double convert(Provider.TYPE providerType, Object value, Class setterType) throws Exception {

        if (value instanceof Number) {
            return ((Number) value).doubleValue() / 3;
        }

        return null;
    }
}