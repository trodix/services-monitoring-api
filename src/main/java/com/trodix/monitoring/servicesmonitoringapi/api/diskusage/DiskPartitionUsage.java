package com.trodix.monitoring.servicesmonitoringapi.api.diskusage;

public record DiskPartitionUsage(String partitionPath, long totalSizeByte, long usedSizeByte) {}
