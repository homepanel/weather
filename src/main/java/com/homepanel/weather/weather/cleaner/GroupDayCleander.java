package com.homepanel.weather.weather.cleaner;

import com.homepanel.weather.weather.converter.Converter;
import com.homepanel.weather.weather.converter.ConverterFactory;
import com.homepanel.weather.weather.converter.IconConverter;
import com.homepanel.weather.weather.data.Weather;
import com.homepanel.weather.weather.provider.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.homepanel.core.service.Service.OBJECT_MAPPER;

public class GroupDayCleander implements InterfaceCleaner {

    private final static Logger LOGGER = LoggerFactory.getLogger(GroupDayCleander.class);
    private final static Map<Provider.TYPE, List<Integer>> GROUP_MAPPING = new HashMap<>();

    static {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            try (InputStream inputStream = IconConverter.class.getClassLoader().getResourceAsStream("groups.json")) {
                byte[] buffer = new byte[1024];
                int length;

                while ((length = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, length);
                }

                Map<String, Object> map = OBJECT_MAPPER.readValue(byteArrayOutputStream.toString("UTF-8"), Map.class);

                for (String typeName : map.keySet()) {
                    Provider.TYPE type = Provider.TYPE.valueOf(typeName);
                    if (!GROUP_MAPPING.containsKey(type)) {
                        GROUP_MAPPING.put(type, new ArrayList<>());
                    }

                    List<Integer> ids = (List) map.get(typeName);
                    for (int i = 0; i < ids.size(); i++) {
                        GROUP_MAPPING.get(type).add(ids.get(i));
                    }
                }

            } catch (IOException e) {
                LOGGER.error("can not read file \"groups.json\"", e);
            }
        } catch (IOException e) {
            LOGGER.error("can not read file \"groups.json\"", e);
        }
    }

    @Override
    public List<Weather> execute(Provider.TYPE type, List<Weather> weathers) {

        List<Weather> result = new ArrayList<>();

        int oldIndex = -1;
        LocalDateTime oldDateTime = null;
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);

        for (int index = 0; index < weathers.size(); index++) {

            Weather weather = weathers.get(index);
            LocalDateTime dateTime = weather.getCondition().getObservationTime();
            if (dateTime != null) {
                dateTime = dateTime.withHour(12).withMinute(0).withSecond(0);
                if (oldDateTime == null) {
                    oldIndex = index;
                    oldDateTime = dateTime;
                }

                if (dateTime.isAfter(oldDateTime)) {
                    if (oldDateTime.isAfter(today)) {
                        result.add(groupWeathers(type, weathers.subList(oldIndex, index - 1)));
                    }
                    oldDateTime = dateTime;
                    oldIndex = index + 1;
                } else if (index + 1 == weathers.size()) {
                    if (oldDateTime.isAfter(today) && index > oldIndex) {
                        result.add(groupWeathers(type, weathers.subList(oldIndex, index)));
                    }
                }
            }
        }

        return result;
    }

    private Weather groupWeathers(Provider.TYPE type, List<Weather> weathers) {

        Weather result = new Weather();

        if (!weathers.isEmpty()) {
            List<String> texts = new ArrayList<>();
            List<Object> iconIds = new ArrayList<>();

            for (Weather weather : weathers) {

                if (weather.getAtmosphere().getHumidity() != null) {
                    result.getAtmosphere().setHumidity((result.getAtmosphere().getHumidity() != null ? result.getAtmosphere().getHumidity() : 0) + (weather.getAtmosphere().getHumidity() != null ? weather.getAtmosphere().getHumidity() : 0));
                }

                if (weather.getAtmosphere().getVisibility() != null) {
                    result.getAtmosphere().setVisibility((result.getAtmosphere().getVisibility() != null ? result.getAtmosphere().getVisibility() : 0) + (weather.getAtmosphere().getVisibility() != null ? weather.getAtmosphere().getVisibility() : 0));
                }

                if (weather.getAtmosphere().getPressure() != null) {
                    result.getAtmosphere().setPressure((result.getAtmosphere().getPressure() != null ? result.getAtmosphere().getPressure() : 0) + (weather.getAtmosphere().getPressure() != null ? weather.getAtmosphere().getPressure() : 0));
                }

                if (weather.getAtmosphere().getOzone() != null) {
                    result.getAtmosphere().setOzone((result.getAtmosphere().getOzone() != null ? result.getAtmosphere().getOzone() : 0) + (weather.getAtmosphere().getOzone() != null ? weather.getAtmosphere().getOzone() : 0));
                }

                if (weather.getAtmosphere().getUvIndex() != null) {
                    result.getAtmosphere().setUvIndex((result.getAtmosphere().getUvIndex() != null ? result.getAtmosphere().getUvIndex() : 0) + (weather.getAtmosphere().getUvIndex() != null ? weather.getAtmosphere().getUvIndex() : 0));
                }

                if (weather.getClouds().getPercent() != null) {
                    result.getClouds().setPercent((result.getClouds().getPercent() != null ? result.getClouds().getPercent() : 0) + (weather.getClouds().getPercent() != null ? weather.getClouds().getPercent() : 0));
                }

                texts.add(weather.getCondition().getText());
                iconIds.add(weather.getCondition().getIcondId());

                if (weather.getPrecipitation().getRain() != null) {
                    result.getPrecipitation().setRain((result.getPrecipitation().getRain() != null ? result.getPrecipitation().getRain() : 0) + (weather.getPrecipitation().getRain() != null ? weather.getPrecipitation().getRain() : 0));
                }

                if (weather.getPrecipitation().getSnow() != null) {
                    result.getPrecipitation().setSnow((result.getPrecipitation().getSnow() != null ? result.getPrecipitation().getSnow() : 0) + (weather.getPrecipitation().getSnow() != null ? weather.getPrecipitation().getSnow() : 0));
                }

                if (weather.getPrecipitation().getProbability() != null) {
                    result.getPrecipitation().setProbability((result.getPrecipitation().getProbability() != null ? result.getPrecipitation().getProbability() : 0) + (weather.getPrecipitation().getProbability() != null ? weather.getPrecipitation().getProbability() : 0));
                }

                if (weather.getTemperature().getCurrent() != null) {
                    result.getTemperature().setCurrent((result.getTemperature().getCurrent() != null ? result.getTemperature().getCurrent() : 0) + (weather.getTemperature().getCurrent() != null ? weather.getTemperature().getCurrent() : 0));
                }

                if (weather.getTemperature().getMin() != null) {
                    Double min = weather.getTemperature().getMin() != null ? weather.getTemperature().getMin() : 0;
                    if (result.getTemperature().getMin() == null || min < result.getTemperature().getMin()) {
                        result.getTemperature().setMin(min);
                    }
                }

                if (weather.getTemperature().getMax() != null) {
                    Double max = weather.getTemperature().getMax() != null ? weather.getTemperature().getMax() : 0;
                    if (result.getTemperature().getMax() == null || max > result.getTemperature().getMax()) {
                        result.getTemperature().setMax(max);
                    }
                }

                if (weather.getTemperature().getFeeling() != null) {
                    result.getTemperature().setFeeling((result.getTemperature().getFeeling() != null ? result.getTemperature().getFeeling() : 0) + (weather.getTemperature().getFeeling() != null ? weather.getTemperature().getFeeling() : 0));
                }

                if (weather.getTemperature().getDewPoint() != null) {
                    result.getTemperature().setDewPoint((result.getTemperature().getDewPoint() != null ? result.getTemperature().getDewPoint() : 0) + (weather.getTemperature().getDewPoint() != null ? weather.getTemperature().getDewPoint() : 0));
                }

                if (weather.getWind().getSpeed() != null) {
                    result.getWind().setSpeed((result.getWind().getSpeed() != null ? result.getWind().getSpeed() : 0) + (weather.getWind().getSpeed() != null ? weather.getWind().getSpeed() : 0));
                }

                if (weather.getWind().getDegree() != null) {
                    result.getWind().setDegree((result.getWind().getDegree() != null ? result.getWind().getDegree() : 0) + (weather.getWind().getDegree() != null ? weather.getWind().getDegree() : 0));
                }

                if (result.getWind().getGust() != null) {
                    result.getWind().setGust((result.getWind().getGust() != null ? result.getWind().getGust() : 0) + (weather.getWind().getGust() != null ? weather.getWind().getGust() : 0));
                }

                if (weather.getWind().getChill() != null) {
                    result.getWind().setChill((result.getWind().getChill() != null ? result.getWind().getChill() : 0) + (weather.getWind().getChill() != null ? weather.getWind().getChill() : 0));
                }
            }

            double count = weathers.size();

            if (result.getAtmosphere().getHumidity() != null) {
                result.getAtmosphere().setHumidity((int) Math.round(result.getAtmosphere().getHumidity() / count));
            }

            if (result.getAtmosphere().getVisibility() != null) {
                result.getAtmosphere().setVisibility(result.getAtmosphere().getVisibility() / count);
            }

            if (result.getAtmosphere().getPressure() != null) {
                result.getAtmosphere().setPressure(result.getAtmosphere().getPressure() / count);
            }

            if (result.getAtmosphere().getOzone() != null) {
                result.getAtmosphere().setOzone((int) Math.round(result.getAtmosphere().getOzone() / count));
            }

            if (result.getAtmosphere().getUvIndex() != null) {
                result.getAtmosphere().setUvIndex((int) Math.round(result.getAtmosphere().getUvIndex() / count));
            }

            if (result.getClouds().getPercent() != null) {
                result.getClouds().setPercent((int) Math.round(result.getClouds().getPercent() / count));
            }

            // https://github.com/openhab-scripters/openhab-helper-libraries/blob/master/Community/OpenWeatherMap/automation/jsr223/python/community/openweathermap/owm_daily_forecast.py
            if (GROUP_MAPPING.containsKey(type)) {
                for (Integer iconGroupId : GROUP_MAPPING.get(type)) {
                    for (Weather weather : weathers) {
                        if (weather.getCondition().getIcondId() != null) {
                            if (weather.getCondition().getIcondId() >= iconGroupId && weather.getCondition().getIcondId() < iconGroupId + 100) {
                                result.getCondition().setIcon(weather.getCondition().getIcon());
                                result.getCondition().setText(weather.getCondition().getText());
                                break;
                            }
                        }
                    }

                    if (result.getCondition().getIcondId() != null) {
                        break;
                    }
                }
            }

            if (result.getPrecipitation().getRain() != null) {
                result.getPrecipitation().setRain(result.getPrecipitation().getRain() / count);
            }

            if (result.getPrecipitation().getSnow() != null) {
                result.getPrecipitation().setSnow(result.getPrecipitation().getSnow() / count);
            }

            if (result.getPrecipitation().getProbability() != null) {
                result.getPrecipitation().setProbability((int) Math.round(result.getPrecipitation().getProbability() / count));
            }

            if (result.getTemperature().getCurrent() != null) {
                result.getTemperature().setCurrent(result.getTemperature().getCurrent() / count);
            }

            if (result.getTemperature().getFeeling() != null) {
                result.getTemperature().setFeeling(result.getTemperature().getFeeling() / count);
            }

            if (result.getTemperature().getDewPoint() != null) {
                result.getTemperature().setDewPoint(result.getTemperature().getDewPoint() / count);
            }

            if (result.getWind().getSpeed() != null) {
                result.getWind().setSpeed(result.getWind().getSpeed() / count);
            }

            if (result.getWind().getDegree() != null) {
                result.getWind().setDegree((int) Math.round(result.getWind().getDegree() / count));
                try {
                    result.getWind().setDirection((String) ConverterFactory.getConverter(Converter.TYPE.WIND_DIRECTION).convert(type, result.getWind().getDegree(), String.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (result.getWind().getGust() != null) {
                result.getWind().setGust(result.getWind().getGust() / count);
            }

            if (result.getWind().getChill() != null) {
                result.getWind().setChill(result.getWind().getChill() / count);
            }

            result.getCondition().setObservationTime(weathers.get(0).getCondition().getObservationTime().withHour(12).withMinute(0).withSecond(0));
        }

        return result;
    }
}
