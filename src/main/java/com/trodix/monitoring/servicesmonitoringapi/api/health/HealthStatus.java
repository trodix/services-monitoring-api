package com.trodix.monitoring.servicesmonitoringapi.api.health;

public record HealthStatus(String serviceName, ServiceStatus serviceStatus, String comment) {}
