package com.trodix.monitoring.servicesmonitoringapi.domain.models;

public record HealthStatus(String serviceName, ServiceStatus serviceStatus, String comment) {}
