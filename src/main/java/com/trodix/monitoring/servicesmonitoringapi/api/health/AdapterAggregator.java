package com.trodix.monitoring.servicesmonitoringapi.api.health;

import com.trodix.monitoring.servicesmonitoringapi.api.health.docker.DockerAdapter;
import com.trodix.monitoring.servicesmonitoringapi.api.health.http.HttpAdapter;
import com.trodix.monitoring.servicesmonitoringapi.api.health.springactuator.SpringActuatorAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdapterAggregator {

    private final DockerAdapter dockerAdapter;

    private final SpringActuatorAdapter actuatorAdapter;
    private final HttpAdapter httpAdapter;

    public List<HealthStatus> getHealthStatus() {
        List<HealthStatus> result = new ArrayList<>();

        result.addAll(dockerAdapter.getServicesStatus());
        result.addAll(actuatorAdapter.getServicesStatus());
        result.addAll(httpAdapter.getServicesStatus());

        return result;
    }

}
