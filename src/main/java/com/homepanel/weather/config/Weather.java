package com.homepanel.weather.config;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "onewire")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Weather {

    private List<Location> locations;

    @XmlElementWrapper(name = "locations")
    @XmlElements({
            @XmlElement(name = "openWeatherMap", type = OpenWeatherMapLocation.class)
    })
    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}