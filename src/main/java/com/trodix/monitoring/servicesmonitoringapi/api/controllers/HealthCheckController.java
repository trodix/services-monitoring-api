package com.trodix.monitoring.servicesmonitoringapi.api.controllers;

import com.trodix.monitoring.servicesmonitoringapi.domain.DiskUsageService;
import com.trodix.monitoring.servicesmonitoringapi.domain.models.DiskPartitionUsage;
import com.trodix.monitoring.servicesmonitoringapi.domain.models.HealthStatus;
import com.trodix.monitoring.servicesmonitoringapi.domain.adapters.AdapterAggregator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class HealthCheckController {

    private final AdapterAggregator aggregator;

    private final DiskUsageService diskUsageService;

    @GetMapping("health")
    public List<HealthStatus> getHealthStatus() {
        return aggregator.getHealthStatus();
    }

    @GetMapping("disk-usage")
    public List<DiskPartitionUsage> getDiskUsage() {
        return diskUsageService.getDiskUsage();
    }

}
