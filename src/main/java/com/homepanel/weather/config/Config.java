package com.homepanel.weather.config;

import com.homepanel.core.type.DefaultDateTime;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "config")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Config extends com.homepanel.core.config.ConfigTopic<Topic> {

    static {
        addTypes(new DefaultDateTime());
    }

    private Weather weather;
    private List<Topic> topics;

    public Weather getWeather() {
        return weather;
    }

    private void setWeather(Weather weather) {
        this.weather = weather;
    }

    @XmlElementWrapper(name = "topics")
    @XmlElement(name = "topic")
    @Override
    public List<Topic> getTopics() {
        return topics;
    }

    @Override
    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }
}