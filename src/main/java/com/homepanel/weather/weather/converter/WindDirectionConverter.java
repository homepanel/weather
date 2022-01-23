package com.homepanel.weather.weather.converter;

import com.homepanel.weather.config.Config;
import com.homepanel.weather.weather.provider.Provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class WindDirectionConverter extends Converter<String> {

    private static ResourceBundle RESOURCE_BUNDLE = null;

    @Override
    public String convert(Provider.TYPE providerType, Object value, Class setterType) throws Exception {

        if (value instanceof Number && RESOURCE_BUNDLE != null) {

            double windDirection = ((Number) value).doubleValue();
            String key = null;

            if (windDirection >= 348.75 || windDirection < 11.25) {
                key = "n";
            } else if (windDirection >= 11.25 && windDirection < 37.75) {
                key = "nne";
            } else if (windDirection >= 37.75 && windDirection < 56.25) {
                key = "ne";
            } else if (windDirection >= 56.25 && windDirection < 78.75) {
                key = "ene";
            } else if (windDirection >= 78.75 && windDirection < 101.25) {
                key = "e";
            } else if (windDirection >= 101.25 && windDirection < 123.75) {
                key = "ese";
            } else if (windDirection >= 123.75 && windDirection < 146.25) {
                key = "se";
            } else if (windDirection >= 146.25 && windDirection < 168.75) {
                key = "sse";
            } else if (windDirection >= 168.75 && windDirection < 191.25) {
                key = "s";
            } else if (windDirection >= 191.25 && windDirection < 213.75) {
                key = "ssw";
            } else if (windDirection >= 213.75 && windDirection < 236.25) {
                key = "sw";
            } else if (windDirection >= 236.25 && windDirection < 258.75) {
                key = "wsw";
            } else if (windDirection >= 258.75 && windDirection < 281.25) {
                key = "w";
            } else if (windDirection >= 281.25 && windDirection < 303.75) {
                key = "wnw";
            } else if (windDirection >= 303.75 && windDirection < 326.25) {
                key = "nw";
            } else if (windDirection >= 326.25 && windDirection < 348.75) {
                key = "nnw";
            }

            if (key != null) {
                if (RESOURCE_BUNDLE.containsKey("weather.wind.direction." + key)) {
                    return RESOURCE_BUNDLE.getString("weather.wind.direction." + key);
                }
            }
        }

        return null;
    }

    public static class Builder {

        private Config config = null;

        public Builder(Config config) {
            this.config = config;
        }

        public void build() {

            if (config.getService() != null && config.getService().getI18nPath() != null) {
                WindDirectionConverter.RESOURCE_BUNDLE = ResourceBundle.getBundle("weather", Locale.getDefault(), new ResourceBundle.Control() {
                    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {

                        ResourceBundle bundle = null;

                        String bundleName = toBundleName(baseName, locale);
                        String resourceName = toResourceName(bundleName, "properties");

                        File file = new File(config.getService().getI18nPath(), resourceName);

                        if (file.exists()) {
                            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                                bundle = new PropertyResourceBundle(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
                            }
                        }

                        return bundle;
                    }
                });
            }
        }
    }
}