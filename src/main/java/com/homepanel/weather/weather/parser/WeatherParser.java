package com.homepanel.weather.weather.parser;

import com.homepanel.weather.weather.annotation.ForecastEntry;
import com.homepanel.weather.weather.annotation.ForecastMap;
import com.homepanel.weather.weather.annotation.ProviderEntry;
import com.homepanel.weather.weather.annotation.ProviderMap;
import com.homepanel.weather.weather.cleaner.InterfaceCleaner;
import com.homepanel.weather.weather.converter.ConverterFactory;
import com.homepanel.weather.weather.data.*;
import com.homepanel.weather.weather.provider.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public abstract class WeatherParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(WeatherParser.class);

    private Provider.TYPE type;

    private Provider.TYPE getType() {
        return type;
    }

    private void setType(Provider.TYPE type) {
        this.type = type;
    }

    public WeatherParser(Provider.TYPE type) {
        setType(type);
    }

    public Current parseCurrent(String content) throws Exception {

        Current current = new Current();

        handleObject(current, getSource(content));

        return current;
    }

    public Forecast parseForecast(String content, InterfaceCleaner cleaner) throws Exception {

        Forecast forecast = new Forecast();

        handleObject(forecast, getSource(content));

        forecast.setWeathers(cleaner.execute(getType(), forecast.getWeathers()));

        return forecast;
    }

    protected abstract Map getSource(String content) throws Exception;

    protected void handleObject(Object object, Map source) {

        for (Method method : object.getClass().getMethods()) {

            if (method.getName().startsWith("set")) {
                boolean found = false;
                for (Annotation annotation : method.getAnnotations()) {
                    if (annotation instanceof ProviderMap) {
                        ProviderMap providerMap = (ProviderMap) annotation;

                        for (ProviderEntry providerEntry : providerMap.value()) {
                            if (providerEntry.providerType() == getType()) {
                                Class[] types = method.getParameterTypes();
                                if (types.length == 1) {

                                    Object value = getProperty(providerEntry.property(), source);

                                    if (value != null) {
                                        try {
                                            value = ConverterFactory.getConverter(providerEntry.converterType()).convert(providerEntry.providerType(), value, types[0]);
                                        } catch (Exception e) {
                                            LOGGER.error("can not convert value \"{}\"", value, e);
                                        }

                                        try {
                                            method.invoke(object, value);
                                        } catch (Exception e) {
                                            LOGGER.error("can not invoke method \"{}\" from object \"{}\"", method.getName(), object.getClass().getSimpleName(), e);
                                        }

                                        break;
                                    }
                                }
                            }
                        }

                        found = true;
                    } else if (annotation instanceof ForecastMap) {
                        ForecastMap forecastMap = (ForecastMap) annotation;

                        if (forecastMap != null) {
                            for (ForecastEntry forecastEntry : forecastMap.value()) {
                                if (forecastEntry.providerType() == getType()) {
                                    Class[] types = method.getParameterTypes();
                                    if (types.length == 1) {
                                        Object value = getProperty(forecastEntry.property(), source);

                                        List<Weather> weathers = parseForecast(value);

                                        try {
                                            method.invoke(object, weathers);
                                        } catch (IllegalAccessException | InvocationTargetException e) {
                                            LOGGER.error("can not invoke method \"{}\" from object \"{}\"", method.getName(), object.getClass().getSimpleName(), e);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Class[] types = method.getParameterTypes();
                if (!found && types.length == 1) {
                    if (InterfaceData.class.isAssignableFrom(types[0])) {
                        try {
                            Object child = types[0].newInstance();
                            handleObject(child, source);
                            try {
                                method.invoke(object, child);
                            } catch (InvocationTargetException e) {
                                LOGGER.error("can not invoke method \"{}\" from object \"{}\"", method.getName(), object.getClass().getSimpleName(), e);
                            }
                        } catch (InstantiationException | IllegalAccessException e) {
                            LOGGER.error("can not create new instance from class\"{}\"",  types[0].getClass().getSimpleName(), e);
                        }
                    }
                }
            }
        }
    }

    protected abstract Object getProperty(String property, Object source);

    protected abstract List<Weather> parseForecast(Object object);
}
