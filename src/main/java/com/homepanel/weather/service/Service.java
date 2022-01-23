package com.homepanel.weather.service;

import com.homepanel.core.executor.PriorityThreadPoolExecutor;
import com.homepanel.core.service.PollingService;
import com.homepanel.core.state.Type;
import com.homepanel.weather.config.Config;
import com.homepanel.weather.config.Location;
import com.homepanel.weather.config.Topic;
import com.homepanel.weather.weather.converter.WindDirectionConverter;
import com.homepanel.weather.weather.provider.Provider;
import com.homepanel.weather.weather.provider.ProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Service extends PollingService<Config, Topic> {

    private final static Logger LOGGER = LoggerFactory.getLogger(Service.class);

    @Override
    public Config getConfig() {
        return (Config) super.getConfig();
    }

    @Override
    protected List<String> getMqttTopics() {
        return new ArrayList<>();
    }

    @Override
    public void start(String[] arguments, Class configClass) {

        super.start(arguments, configClass);

        try {
            startService();
        } catch (Exception e) {
            LOGGER.error("global exception when starting service", e);
        }
    }

    @Override
    public String getTopicNameByTopic(Topic topic) {
        return null;
    }

    @Override
    protected Integer getPollingExecutorServicePoolSize() {
        return 3;
    }

    @Override
    protected void startService() throws Exception {

        new WindDirectionConverter.Builder(getConfig()).build();

        if (getConfig().getTopics() != null) {

            // set type
            for (Topic topic : getConfig().getTopics()) {
                topic.setType(Config.getType(Type.NAME.JSON));
            }
        }
    }

    @Override
    protected void shutdownService() throws Exception {
    }

    @Override
    protected void onInit() {

        // clean old value and timestamp for init
        for (Topic topic : getConfig().getTopics()) {
            if (topic.getLastValue() != null) {
                publishData(topic, topic.getLastValue());
            }
        }
    }

    @Override
    protected void onData(String topic, String value) {
        // unused
    }

    @Override
    protected void onData(Topic topic, Object value, PriorityThreadPoolExecutor.PRIORITY priority) {
        // unused
    }

    @Override
    protected void updateData(Topic topic) {
        // unused
    }

    @Override
    public void pollData(Topic topic, Long jobRunningTimeInMilliseconds, Long refreshIntervalInMilliseconds) {

        Object value = null;
        if (topic.getLocation() != null) {

            if (getConfig().getWeather().getLocations() != null) {
                for (Location location : getConfig().getWeather().getLocations()) {
                    if (location.getName() != null && location.getName().equals(topic.getLocation())) {
                        Provider provider = ProviderFactory.getConverter(location.getProviderType());

                        if (provider != null) {
                            if (topic.getGroup() == Topic.GROUP.CURRENT) {
                                try {
                                    value = provider.executeCurrent(location);
                                } catch (Exception e) {
                                    LOGGER.error("can not parse current", e);
                                }
                            } else if (topic.getGroup() == Topic.GROUP.FORECAST) {
                                try {
                                    value = provider.executeForecast(location);
                                } catch (Exception e) {
                                    LOGGER.error("can not parse forecast", e);
                                }
                            }
                        }
                    }
                }
            }
        }

        if (value != null) {
            publishData(topic, value);

            topic.setLastValue(value);
            topic.setLastDateTime(LocalDateTime.now());
        }
    }

    public static void main(String[] arguments) throws Exception {
        new Service().start(arguments, Config.class);
    }
}