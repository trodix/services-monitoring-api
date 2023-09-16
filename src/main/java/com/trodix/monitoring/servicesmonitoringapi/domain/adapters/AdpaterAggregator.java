package com.trodix.monitoring.servicesmonitoringapi.domain.adapters;

import com.trodix.monitoring.servicesmonitoringapi.api.responses.HealthStatus;
import com.trodix.monitoring.servicesmonitoringapi.domain.adapters.docker.DockerAdapter;
import com.trodix.monitoring.servicesmonitoringapi.domain.adapters.http.HttpAdapter;
import com.trodix.monitoring.servicesmonitoringapi.domain.adapters.springactuator.SpringActuatorAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdpaterAggregator {

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
