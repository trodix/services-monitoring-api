package com.trodix.monitoring.servicesmonitoringapi.domain.models;

public record DiskPartitionUsage(String partitionPath, long totalSizeByte, long usedSizeByte) {}
