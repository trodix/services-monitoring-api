package com.trodix.monitoring.servicesmonitoringapi.api;

import com.trodix.monitoring.servicesmonitoringapi.api.diskusage.DiskUsageService;
import com.trodix.monitoring.servicesmonitoringapi.api.diskusage.DiskPartitionUsage;
import com.trodix.monitoring.servicesmonitoringapi.api.health.AdapterAggregator;
import com.trodix.monitoring.servicesmonitoringapi.api.health.HealthStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class MonitoringController {

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
