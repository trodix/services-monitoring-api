package com.trodix.monitoring.servicesmonitoringapi.api.controllers;

import com.trodix.monitoring.servicesmonitoringapi.api.responses.HealthStatus;
import com.trodix.monitoring.servicesmonitoringapi.domain.adapters.DockerAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    private final DockerAdapter dockerAdapter;

    @GetMapping("docker-services/status")
    public List<HealthStatus> getHealthStatus() {
        return dockerAdapter.getContainersStatus();
    }

}
