package com.trodix.monitoring.servicesmonitoringapi.domain.adapters;

import com.trodix.monitoring.servicesmonitoringapi.api.responses.HealthStatus;

import java.util.List;

public interface ServiceStatusAdapter {

    public List<HealthStatus> getServicesStatus();
}
