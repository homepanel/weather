package com.homepanel.weather.weather.annotation;

import com.homepanel.weather.weather.converter.Converter;
import com.homepanel.weather.weather.provider.Provider;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.homepanel.weather.weather.converter.Converter.TYPE.AUTO;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD})
@Retention(RUNTIME)
public @interface ProviderEntry {
    public Provider.TYPE providerType();

    public String property();

    public Converter.TYPE converterType() default AUTO;
}