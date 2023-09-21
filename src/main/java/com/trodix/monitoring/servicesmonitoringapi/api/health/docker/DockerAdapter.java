package com.trodix.monitoring.servicesmonitoringapi.api.health.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.trodix.monitoring.servicesmonitoringapi.api.health.HealthStatus;
import com.trodix.monitoring.servicesmonitoringapi.api.health.ServiceStatus;
import com.trodix.monitoring.servicesmonitoringapi.api.health.ServiceStatusAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DockerAdapter implements ServiceStatusAdapter {

    private final DockerClient dockerClient;

    @Override
    public List<HealthStatus> getServicesStatus() {
        List<Container> containers = dockerClient.listContainersCmd()
                .withShowAll(true)
                .exec();

        List<HealthStatus> names = containers.stream()
                .map(c ->
                        new HealthStatus(
                                c.getNames()[0],
                                c.getState().equalsIgnoreCase("running") ? ServiceStatus.UP : ServiceStatus.DOWN,
                                c.getStatus()))
                .toList();

        return names;
    }

}
