package com.homepanel.weather.config;

import com.homepanel.weather.weather.provider.Provider;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public abstract class Location {

    public enum UNIT {
        METRIC,
        IMPERIAL
    }

    private String name;
    private String title;
    private Double latitude;
    private Double longitude;
    private String language;

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public Double getLatitude() {
        return latitude;
    }

    private void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    private void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getLanguage() {
        return language;
    }

    private void setLanguage(String language) {
        this.language = language;
    }

    public abstract Provider.TYPE getProviderType();
}