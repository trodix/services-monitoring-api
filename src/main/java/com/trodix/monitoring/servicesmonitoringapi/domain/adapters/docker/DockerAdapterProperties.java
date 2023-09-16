package com.trodix.monitoring.servicesmonitoringapi.domain.adapters.docker;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "adapters.docker")
public record DockerAdapterProperties(
        String dockerHost,
        String registryUsername,
        String registryToken,
        String registryEmail,
        String registryUrl
) {}