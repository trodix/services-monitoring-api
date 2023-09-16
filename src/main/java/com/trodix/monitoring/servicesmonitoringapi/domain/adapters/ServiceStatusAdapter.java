package com.trodix.monitoring.servicesmonitoringapi.domain.adapters;

import com.trodix.monitoring.servicesmonitoringapi.domain.models.HealthStatus;

import java.util.List;

public interface ServiceStatusAdapter {

    public List<HealthStatus> getServicesStatus();
}
