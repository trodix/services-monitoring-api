package com.trodix.monitoring.servicesmonitoringapi.api.health;

import java.util.List;

public interface ServiceStatusAdapter {

    public List<HealthStatus> getServicesStatus();
}
