package com.trodix.monitoring.servicesmonitoringapi.api.controllers;

import com.trodix.monitoring.servicesmonitoringapi.api.responses.HealthStatus;
import com.trodix.monitoring.servicesmonitoringapi.domain.adapters.AdpaterAggregator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class HealthCheckController {

    private final AdpaterAggregator aggregator;

    @GetMapping("health")
    public List<HealthStatus> getHealthStatus() {
        return aggregator.getHealthStatus();
    }

}
