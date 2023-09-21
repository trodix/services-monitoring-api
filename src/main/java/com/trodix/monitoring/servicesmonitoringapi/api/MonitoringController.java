package com.trodix.monitoring.servicesmonitoringapi.api;

import com.trodix.monitoring.servicesmonitoringapi.api.diskusage.DiskPartitionUsage;
import com.trodix.monitoring.servicesmonitoringapi.api.diskusage.DiskUsageService;
import com.trodix.monitoring.servicesmonitoringapi.api.health.AdapterAggregator;
import com.trodix.monitoring.servicesmonitoringapi.api.health.HealthStatus;
import com.trodix.monitoring.servicesmonitoringapi.api.logging.docker.DockerLoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class MonitoringController {

    private final AdapterAggregator aggregator;

    private final DiskUsageService diskUsageService;

    private final DockerLoggingService dockerLoggingService;

    @GetMapping("health")
    public List<HealthStatus> getHealthStatus() {
        return aggregator.getHealthStatus();
    }

    @GetMapping("disk-usage")
    public List<DiskPartitionUsage> getDiskUsage() {
        return diskUsageService.getDiskUsage();
    }

    @GetMapping("logs/{containerId}")
    public String getContainerLogs(@PathVariable String containerId, @RequestParam String since, @RequestParam(required = false) String until) {

        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ISO_LOCAL_DATE_TIME; // '2023-09-20T21:15:30'
        LocalDateTime since_ = dateTimeFormat.parse(since, LocalDateTime::from);
        LocalDateTime until_ = until == null ? null : dateTimeFormat.parse(until, LocalDateTime::from);

        return dockerLoggingService.getContainerLogs(containerId, since_, until_);
    }

}
