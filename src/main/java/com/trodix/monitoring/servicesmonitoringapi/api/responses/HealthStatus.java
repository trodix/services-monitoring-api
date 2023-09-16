package com.trodix.monitoring.servicesmonitoringapi.api.responses;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.trodix.monitoring.servicesmonitoringapi.domain.models.ServiceStatus;

@JsonPropertyOrder({"serviceName", "serviceStatus", "comment"})
public record HealthStatus(String serviceName, ServiceStatus serviceStatus, String comment) {}
