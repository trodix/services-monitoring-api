package com.trodix.monitoring.servicesmonitoringapi.api.health.docker;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "adapters.docker")
public record DockerAdapterProperties(
        String dockerHost
) {}
