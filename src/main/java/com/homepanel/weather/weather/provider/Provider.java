package com.homepanel.weather.weather.provider;

import com.homepanel.weather.weather.data.Current;
import com.homepanel.weather.weather.data.Forecast;
import com.homepanel.weather.weather.parser.WeatherParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class Provider<T> {

    private final static Logger LOGGER = LoggerFactory.getLogger(Provider.class);

    public enum TYPE {
        OPEN_WEATHER_MAP
    }

    private OkHttpClient httpClient;
    private WeatherParser parser;

    private OkHttpClient getHttpClient() {
        return httpClient;
    }

    private void setHttpClient(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    protected WeatherParser getParser() {
        return parser;
    }

    private void setParser(WeatherParser parser) {
        this.parser = parser;
    }

    public Provider(WeatherParser parser) {
        setHttpClient(new OkHttpClient());
        setParser(parser);
    }

    public abstract Current executeCurrent(T location) throws Exception ;

    public abstract Forecast executeForecast(T location) throws Exception ;

    protected String getUrl(String url) {
        Request request = new Request.Builder()
            .url(url)
            .build();

        try {
            Response response = getHttpClient().newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            LOGGER.error("can not get url \"{}\"", url, e);
        }

        return null;
    }
}