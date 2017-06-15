package com.uken.platform.metrics;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by kefu on 2017-06-14.
 */
@ConfigurationProperties(prefix = "statsd")
public class StatsdProperties {

    @Value("${statsd.host:localhost}")
    private String host;

    @Value("${statsd.port:8125}")
    private int port;

    private String prefix;

    public String getHost()
    {
        return host;
    }

    public int getPort()
    {
        return port;
    }
}
