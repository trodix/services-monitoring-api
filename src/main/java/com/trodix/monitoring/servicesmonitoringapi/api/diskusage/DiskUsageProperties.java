package com.trodix.monitoring.servicesmonitoringapi.api.diskusage;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "diskusage")
public record DiskUsageProperties(
        List<String> volumes
) {}
