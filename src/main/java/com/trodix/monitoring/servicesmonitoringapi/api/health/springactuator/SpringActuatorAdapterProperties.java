package com.trodix.monitoring.servicesmonitoringapi.api.health.springactuator;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "adapters.springactuator")
public record SpringActuatorAdapterProperties(
        List<String> actuatorEndpoints
) {}
