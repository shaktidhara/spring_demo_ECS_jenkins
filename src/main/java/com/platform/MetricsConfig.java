package com.platform;

import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.metrics.export.MetricExportProperties;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.cloud.aws.actuate.metrics.BufferingCloudWatchMetricSender;
import org.springframework.cloud.aws.actuate.metrics.CloudWatchMetricWriter;
import org.springframework.context.annotation.Bean;

import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsyncClient;

public class MetricsConfig {

	private String prefix = "test";
	private int maxBuffer = 1000;
	private long fixedDelayBetweenRuns = 5000;
	

	@Bean
	@ExportMetricWriter
	MetricWriter metricWriter(MetricExportProperties export) {
	    return new CloudWatchMetricWriter(new BufferingCloudWatchMetricSender(prefix, maxBuffer, fixedDelayBetweenRuns, new AmazonCloudWatchAsyncClient()));
	}
	
}
