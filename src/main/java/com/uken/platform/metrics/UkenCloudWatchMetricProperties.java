package com.uken.platform.metrics;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.aws.actuate.metrics.CloudWatchMetricWriter;

/**
 * Configuration properties for {@link CloudWatchMetricWriter}.
 *
 * @author Simon Buettner
 * @author Agim Emruli
 */
@ConfigurationProperties(prefix = "uken.cloud.aws.cloudwatch")
public class UkenCloudWatchMetricProperties {

    /**
     * The namespace which will be used when sending
     * metrics to CloudWatch. This property is needed and must not be null.
     */
    private String namespace = "";

    /**
     * The maximum number of elements the {@link CloudWatchMetricWriter}
     * will buffer before sending to CloudWatch.
     */
    private int maxBuffer = Integer.MAX_VALUE;

    /**
	 * The delay of the background task which sends metrics to
	 * CloudWatch. A higher delay leads to more buffering and less but larger requests.
     * A lower delay leads to a smaller buffer but more requests with less payload.
     */
	private long fixedDelayBetweenRuns = 1000 * 60;
	
	/**
	 * Prefix to put in metric name
	 * 
	 */
	private String prefix = "";
	
	/**
	 * AWS region
	 */
	private String region = "us-east-1";

    public String getNamespace() {
		return this.namespace;
	}

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public int getMaxBuffer() {
		return this.maxBuffer;
	}

    public void setMaxBuffer(int maxBuffer) {
        this.maxBuffer = maxBuffer;
    }

	public long getFixedDelayBetweenRuns() {
		return this.fixedDelayBetweenRuns;
	}

	public void setFixedDelayBetweenRuns(long fixedDelayBetweenRuns) {
		this.fixedDelayBetweenRuns = fixedDelayBetweenRuns;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
}
