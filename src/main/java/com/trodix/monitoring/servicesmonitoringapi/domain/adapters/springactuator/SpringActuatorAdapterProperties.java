package com.trodix.monitoring.servicesmonitoringapi.domain.adapters.springactuator;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "adapters.springactuator")
public record SpringActuatorAdapterProperties(
        List<String> actuatorEndpoints
) {}
