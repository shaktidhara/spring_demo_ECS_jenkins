package com.platform;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.SystemPublicMetrics;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsync;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsyncClient;
import com.blacklocus.metrics.CloudWatchReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.MetricSet;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

@Configuration
@EnableMetrics
public class MetricsConfig extends MetricsConfigurerAdapter {

	final static Logger logger = LoggerFactory.getLogger(MetricsConfig.class);

	@Value("${spring.application.name:demo2}")
	private String applicationName;

	@Value("${spring.profiles.active:dev}")
	private String environment;

	@Autowired
	private SystemPublicMetrics systemPublicMetrics;

	@Override
	public void configureReporters(MetricRegistry metricRegistry) {
		AmazonCloudWatchAsync amazonCloudWatchAsync = new AmazonCloudWatchAsyncClient();
		amazonCloudWatchAsync.setRegion(Region.getRegion(Regions.US_EAST_1));

		metricRegistry.register("spring.boot", (MetricSet) () -> {
			final Map<String, Metric> gauges = new HashMap<String, Metric>();

			for (final org.springframework.boot.actuate.metrics.Metric<?> springMetric : systemPublicMetrics
					.metrics()) {

				gauges.put(springMetric.getName(), (Gauge<Object>) () -> {

					return systemPublicMetrics.metrics().stream()
							.filter(m -> StringUtils.equals(m.getName(), springMetric.getName())).map(m -> m.getValue())
							.findFirst().orElse(null);

				});
			}
			return Collections.unmodifiableMap(gauges);
		});

		String nameSpace = environment + "." + applicationName;
		CloudWatchReporter cloudWatchReporter = new CloudWatchReporter(metricRegistry, nameSpace, amazonCloudWatchAsync);

		logger.info("Namespace is" + nameSpace);

		registerReporter(cloudWatchReporter).start(1, TimeUnit.MINUTES);
	}
}
