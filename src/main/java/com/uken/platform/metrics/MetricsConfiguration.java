package com.uken.platform.metrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.metrics.statsd.StatsdMetricWriter;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * Created by kefu on 2017-06-14.
 */
@Configuration
//@EnableMetrics(proxyTargetClass = true)
@EnableConfigurationProperties(StatsdProperties.class)
public class MetricsConfiguration {
    @Autowired
    private StatsdProperties statsdProperties;

    @Value("${uken.app_name:default}")
    private String appName;

    private String hostName;

    private String prefix;



    @Bean
    @ConditionalOnProperty(prefix = "statsd", name = {"host", "port"})
    @ExportMetricWriter
    MetricWriter metricWriter() {
        return new StatsdMetricWriter(prefix, statsdProperties.getHost(), statsdProperties.getPort());
    }

    MetricsConfiguration(){
        try {

            hostName = InetAddress.getLocalHost().getHostName();
            System.out.println("Get Current HostName:" + hostName);
        }catch (UnknownHostException e) {
            hostName = UUID.randomUUID().toString();
        }
    }

    @PostConstruct
    public void init(){
        prefix = appName + "." + hostName;
    }
}
