package com.homepanel.weather.weather.converter;

import com.homepanel.weather.weather.provider.Provider;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class AutoConverter extends Converter<Object> {

    private static final String[] EMPTY_VALUES = new String[] {"NA", "N/A", "--", ""};

    @Override
    public Object convert(Provider.TYPE providerType, Object value, Class setterType) throws Exception {

        if (value instanceof Number) {
            if (Integer.class.isAssignableFrom(setterType)) {
                value = ((Number) value).intValue();
            } else if (Double.class.isAssignableFrom(setterType)) {
                value = ((Number) value).doubleValue();
            } else if (String.class.isAssignableFrom(setterType)) {
                value = ((Number) value).toString();
            } else if (LocalDateTime.class.isAssignableFrom(setterType)) {
                value = LocalDateTime.ofInstant(Instant.ofEpochSecond(((Number) value).longValue()), ZoneId.systemDefault());
            }
        } else if (value instanceof String) {
            if (setterType == Integer.class) {
                value = Integer.valueOf((String) value);
            }
        }

        return value;
    }
}
