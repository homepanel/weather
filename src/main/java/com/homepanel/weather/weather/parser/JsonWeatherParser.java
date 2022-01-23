package com.homepanel.weather.weather.parser;

import com.homepanel.weather.weather.data.Weather;
import com.homepanel.weather.weather.provider.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.homepanel.core.service.Service.OBJECT_MAPPER;

public class JsonWeatherParser extends WeatherParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(JsonWeatherParser.class);

    public JsonWeatherParser(Provider.TYPE TYPE) {
        super(TYPE);
    }

    @Override
    protected Map getSource(String content) throws Exception {
        return OBJECT_MAPPER.readValue(content, Map.class);
    }

    @Override
    protected Object getProperty(String property, Object source) {

        if (source != null) {
            Object value = source;
            for (String key : property.split(("\\."))) {
                if (value instanceof Map) {
                    Map map = (Map) value;
                    if (map.containsKey(key)) {
                        value = map.get(key);
                    } else {
                        return null;
                    }
                } else if (value instanceof List) {
                    List list = (List) value;
                    Integer index = Integer.parseInt(key);
                    if (index < list.size()) {
                        value = list.get(index);
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }

            return value;
        }

        return null;
    }

    protected List<Weather> parseForecast(Object object) {

        List<Weather> weathers = new ArrayList<>();

        if (object instanceof List) {
            List list = (List) object;

            for (int i = 0; i < list.size(); i++) {
                Map source = (Map) list.get(i);

                Weather weather = new Weather();
                handleObject(weather, source);
                weathers.add(weather);
            }
        }

        return weathers;
    }
}