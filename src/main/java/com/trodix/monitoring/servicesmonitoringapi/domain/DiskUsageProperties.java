package com.trodix.monitoring.servicesmonitoringapi.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "diskusage")
public record DiskUsageProperties(
        List<String> volumes
) {}
