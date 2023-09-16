package com.trodix.monitoring.servicesmonitoringapi.api.health.http;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "adapters.http")
public record HttpAdapterProperties(
        List<String> httpEndpoints
) {}
