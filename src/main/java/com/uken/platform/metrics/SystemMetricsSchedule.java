package com.uken.platform.metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.SystemPublicMetrics;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kefu on 2017-06-14.
 */
@Component
public class SystemMetricsSchedule {
    private static final Logger log = LoggerFactory.getLogger(SystemMetricsSchedule.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private GaugeService gaugeService;

    @Autowired
    private SystemPublicMetrics systemPublicMetrics;

    @Scheduled(fixedRate = 5000)
    public void reportMetrics() {
        log.info("The time is now {}", dateFormat.format(new Date()));
        for (final org.springframework.boot.actuate.metrics.Metric<?> springMetric : systemPublicMetrics.metrics()) {
            gaugeService.submit(springMetric.getName(), springMetric.getValue().doubleValue());
        }
    }
}
