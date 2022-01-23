package com.homepanel.weather.weather.data;

public class Weather implements InterfaceData {

    private Atmosphere atmosphere;
    private Clouds clouds;
    private Condition condition;
    private Precipitation precipitation;
    private Temperature temperature;
    private Wind wind;

    public Atmosphere getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(Atmosphere atmosphere) {
        this.atmosphere = atmosphere;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Precipitation getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(Precipitation precipitation) {
        this.precipitation = precipitation;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Weather() {
        setAtmosphere(new Atmosphere());
        setClouds(new Clouds());
        setCondition(new Condition());
        setPrecipitation(new Precipitation());
        setTemperature(new Temperature());
        setWind(new Wind());
    }
}