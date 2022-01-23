package com.homepanel.weather.config;

import com.homepanel.core.config.InterfaceTopic;
import com.homepanel.core.config.InterfaceTopicPolling;
import com.homepanel.core.config.InterfaceTopicValue;
import com.homepanel.core.state.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlValue;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@XmlRootElement(name = "topic")
public class Topic implements InterfaceTopic, InterfaceTopicValue, InterfaceTopicPolling {

    private final static Logger LOGGER = LoggerFactory.getLogger(Topic.class);

    public enum GROUP {
        CURRENT,
        FORECAST
    }

    private String location;
    private GROUP group;
    private String path;
    private Type type;
    private Object lastValue;
    private LocalDateTime lastDateTime;
    private Integer refreshIntervalValue;
    private TimeUnit refreshIntervalUnit;

    @XmlAttribute
    public String getLocation() {
        return location;
    }

    private void setLocation(String location) {
        this.location = location;
    }

    @XmlAttribute
    public GROUP getGroup() {
        return group;
    }

    private void setGroup(GROUP group) {
        this.group = group;
    }

    @XmlValue
    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @XmlTransient
    @Override
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @XmlTransient
    public Object getLastValue() {
        return lastValue;
    }

    public void setLastValue(Object lastValue) {
        this.lastValue = lastValue;
    }

    @XmlTransient
    @Override
    public LocalDateTime getLastDateTime() {
        return lastDateTime;
    }

    @Override
    public void setLastDateTime(LocalDateTime lastDateTime) {
        this.lastDateTime = lastDateTime;
    }

    @XmlAttribute
    @Override
    public Integer getRefreshIntervalValue() {
        return refreshIntervalValue;
    }

    @Override
    public void setRefreshIntervalValue(Integer refreshIntervalValue) {
        this.refreshIntervalValue = refreshIntervalValue;
    }

    @XmlAttribute
    @Override
    public TimeUnit getRefreshIntervalUnit() {
        return refreshIntervalUnit;
    }

    @Override
    public void setRefreshIntervalUnit(TimeUnit refreshIntervalUnit) {
        this.refreshIntervalUnit = refreshIntervalUnit;
    }
}