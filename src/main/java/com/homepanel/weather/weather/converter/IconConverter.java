package com.homepanel.weather.weather.converter;

import com.homepanel.weather.weather.provider.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.homepanel.core.service.Service.OBJECT_MAPPER;

public class IconConverter extends Converter<String> {

    private final static Logger LOGGER = LoggerFactory.getLogger(IconConverter.class);
    private final static Map<Provider.TYPE, Map<Object, String>> ICON_MAPPING = new HashMap<>();

    static {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            try (InputStream inputStream = IconConverter.class.getClassLoader().getResourceAsStream("icons.json")) {
                byte[] buffer = new byte[1024];
                int length;

                while ((length = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, length);
                }

                Map<String, Map> map = OBJECT_MAPPER.readValue(byteArrayOutputStream.toString("UTF-8"), Map.class);

                for (String iconName : map.keySet()) {
                    Map<String, List> entry = map.get(iconName);

                    for (String typeName : entry.keySet()) {
                        Provider.TYPE type = Provider.TYPE.valueOf(typeName);
                        if (!ICON_MAPPING.containsKey(type)) {
                            ICON_MAPPING.put(type, new HashMap<>());
                        }

                        List<Integer> ids = entry.get(typeName);
                        for (int i = 0; i < ids.size(); i++) {
                            ICON_MAPPING.get(type).put(ids.get(i), iconName);
                        }
                    }
                }

            } catch (IOException e) {
                LOGGER.error("can not read file \"icons.json\"", e);
            }
        } catch (IOException e) {
            LOGGER.error("can not read file \"icons.json\"", e);
        }
    }

    @Override
    public String convert(Provider.TYPE providerType, Object value, Class setterType) throws Exception {

        if (ICON_MAPPING.containsKey(providerType)) {
            if (ICON_MAPPING.get(providerType).containsKey(value)) {
                return ICON_MAPPING.get(providerType).get(value);
            }
        }

        return null;
    }
}