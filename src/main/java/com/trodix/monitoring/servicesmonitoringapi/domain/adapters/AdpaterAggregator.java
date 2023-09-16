package com.trodix.monitoring.servicesmonitoringapi.domain.adapters;

import com.trodix.monitoring.servicesmonitoringapi.api.responses.HealthStatus;
import com.trodix.monitoring.servicesmonitoringapi.domain.adapters.docker.DockerAdapter;
import com.trodix.monitoring.servicesmonitoringapi.domain.adapters.springactuator.SpringActuatorAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AdpaterAggregator {

    private final DockerAdapter dockerAdapter;

    private final SpringActuatorAdapter actuatorAdapter;

    public List<HealthStatus> getHealthStatus() {
        return Stream.concat(
                        dockerAdapter.getServicesStatus().stream(),
                        actuatorAdapter.getServicesStatus().stream())
                .toList();
    }

}
