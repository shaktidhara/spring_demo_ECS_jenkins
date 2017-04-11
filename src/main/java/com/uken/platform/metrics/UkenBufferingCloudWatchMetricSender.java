package com.uken.platform.metrics;

import org.springframework.cloud.aws.actuate.metrics.BufferingCloudWatchMetricSender;

import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsync;
import com.amazonaws.services.cloudwatch.model.MetricDatum;

/**
 * Like BufferingCloudWatchMetricSender but with prefix support
 */
public class UkenBufferingCloudWatchMetricSender extends BufferingCloudWatchMetricSender {

	private final String prefix;

	public UkenBufferingCloudWatchMetricSender(String namespace, int maxBuffer, long fixedDelayBetweenRuns, AmazonCloudWatchAsync amazonCloudWatchAsync, String prefix) {
		super(namespace, maxBuffer, fixedDelayBetweenRuns, amazonCloudWatchAsync);
		this.prefix = prefix;
	}

	@Override
	public void send(MetricDatum metricDatum) {
		if(!prefix.isEmpty()){
			metricDatum.setMetricName(prefix + "." + metricDatum.getMetricName());
		}
		super.send(metricDatum);
	}
}
