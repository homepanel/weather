package com.homepanel.weather.weather.converter;

import java.util.HashMap;
import java.util.Map;

public class ConverterFactory {

    private static Map<Converter.TYPE, Converter<?>> CONVERTERS = new HashMap<>();

    public static Converter<?> getConverter(Converter.TYPE type) {
        if (!CONVERTERS.containsKey(type)) {
            switch (type) {
                case AUTO:
                    CONVERTERS.put(Converter.TYPE.AUTO, new AutoConverter());
                    break;
                case ICON:
                    CONVERTERS.put(Converter.TYPE.ICON, new IconConverter());
                    break;
                case DOUBLE_3H:
                    CONVERTERS.put(Converter.TYPE.DOUBLE_3H, new ThreeHoursDoubleConverter());
                    break;
                case WIND_DIRECTION:
                    CONVERTERS.put(Converter.TYPE.WIND_DIRECTION, new WindDirectionConverter());
                    break;
            }
        }

        return CONVERTERS.get(type);
    }
}