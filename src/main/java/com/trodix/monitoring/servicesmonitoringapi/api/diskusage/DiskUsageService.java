package com.trodix.monitoring.servicesmonitoringapi.api.diskusage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiskUsageService {

    private final DiskUsageProperties properties;

    public List<DiskPartitionUsage> getDiskUsage() {

        List<DiskPartitionUsage> result = new ArrayList<>();

        for (String volumePath : properties.volumes()) {
            File volume = Paths.get(volumePath).toFile();

            long totalSizeByte = volume.getTotalSpace();
            long usedSizeByte = volume.getTotalSpace() - volume.getUsableSpace();

            result.add(new DiskPartitionUsage(volumePath, totalSizeByte, usedSizeByte));
        }

        return result;
    }






}
