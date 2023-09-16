package com.trodix.monitoring.servicesmonitoringapi.domain.adapters.http;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "adapters.http")
public record HttpAdapterProperties(
        List<String> httpEndpoints
) {}
