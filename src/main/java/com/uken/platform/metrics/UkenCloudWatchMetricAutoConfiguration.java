package com.uken.platform.metrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.aws.actuate.metrics.BufferingCloudWatchMetricSender;
import org.springframework.cloud.aws.actuate.metrics.CloudWatchMetricSender;
import org.springframework.cloud.aws.actuate.metrics.CloudWatchMetricWriter;
import org.springframework.cloud.aws.autoconfigure.actuate.CloudWatchMetricProperties;
import org.springframework.cloud.aws.autoconfigure.context.ContextCredentialsAutoConfiguration;
import org.springframework.cloud.aws.context.annotation.ConditionalOnMissingAmazonClient;
import org.springframework.cloud.aws.core.region.RegionProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsync;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsyncClient;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchAsyncClientBuilder;

/**
 * Autoconfiguration which creates and exports a {@link CloudWatchMetricWriter}
 * alongside with an {@link AmazonCloudWatchAsync} if its not already present.
 * 
 * This Version supports prefix
 *  
 * @author Simon Buettner
 * @author Agim Emruli
 * @author gihad
 * 
 */
@Configuration
@Import(ContextCredentialsAutoConfiguration.class)
@EnableConfigurationProperties(UkenCloudWatchMetricProperties.class)
@ConditionalOnProperty(prefix = "uken.cloud.aws.cloudwatch", name = "namespace")
@ConditionalOnClass(AmazonCloudWatchAsync.class)
public class UkenCloudWatchMetricAutoConfiguration {

	@Autowired
	private UkenCloudWatchMetricProperties ukenCloudWatchMetricProperties;

	@Autowired 
	private String hostname;

	@Bean
	@ConditionalOnMissingAmazonClient(AmazonCloudWatchAsync.class)
	public AmazonCloudWatchAsync amazonCloudWatchAsync(AWSCredentialsProvider credentialsProvider) {
		
		AmazonCloudWatchAsyncClientBuilder builder = AmazonCloudWatchAsyncClientBuilder.standard();
		builder.setCredentials(credentialsProvider);
		builder.setRegion(this.ukenCloudWatchMetricProperties.getRegion());
		AmazonCloudWatchAsync serviceClient = builder.build();
		return serviceClient;
	}

	@Bean
	@ExportMetricWriter
	CloudWatchMetricWriter cloudWatchMetricWriter(CloudWatchMetricSender cloudWatchMetricSender) {
		return new CloudWatchMetricWriter(cloudWatchMetricSender);
	}

	@Bean
	@ConditionalOnMissingBean(CloudWatchMetricSender.class)
	CloudWatchMetricSender cloudWatchMetricWriterSender(AmazonCloudWatchAsync amazonCloudWatchAsync) {
		
		// If no prefix is defined use hostname
		String prefix = this.ukenCloudWatchMetricProperties.getPrefix().isEmpty() ? hostname : this.ukenCloudWatchMetricProperties.getPrefix(); 
		
		return new UkenBufferingCloudWatchMetricSender(this.ukenCloudWatchMetricProperties.getNamespace(),
				this.ukenCloudWatchMetricProperties.getMaxBuffer(),
				this.ukenCloudWatchMetricProperties.getFixedDelayBetweenRuns(), amazonCloudWatchAsync, prefix);
	}
}
