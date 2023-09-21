package com.trodix.monitoring.servicesmonitoringapi.api.logging.docker;

import com.github.dockerjava.api.DockerClient;
import com.trodix.monitoring.servicesmonitoringapi.api.logging.docker.output.OutputFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DockerLoggingService {

    private final DockerClient dockerClient;

    public String getContainerLogs(String containerId, LocalDateTime since, @Nullable LocalDateTime until) {

        Integer since_ = (int) Timestamp.valueOf(since).toInstant().getEpochSecond();
        Integer until_ = until == null ? null : (int) Timestamp.valueOf(until).toInstant().getEpochSecond();

        return new LogUtils().getOutput(dockerClient, containerId, since_, until_, OutputFrame.OutputType.STDOUT, OutputFrame.OutputType.STDERR);
    }

}
