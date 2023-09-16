package com.trodix.monitoring.servicesmonitoringapi.domain.adapters.docker;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "adapters.docker")
public record DockerAdapterProperties(
        String dockerHost
) {}
