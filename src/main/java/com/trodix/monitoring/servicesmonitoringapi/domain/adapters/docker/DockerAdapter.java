package com.trodix.monitoring.servicesmonitoringapi.domain.adapters.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.trodix.monitoring.servicesmonitoringapi.domain.models.HealthStatus;
import com.trodix.monitoring.servicesmonitoringapi.domain.adapters.ServiceStatusAdapter;
import com.trodix.monitoring.servicesmonitoringapi.domain.models.ServiceStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class DockerAdapter implements ServiceStatusAdapter {

    private final DockerClient dockerClient;

    public DockerAdapter(DockerAdapterProperties properties) {

        DockerClientConfig config= DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(properties.dockerHost())
                .build();

        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();

        this.dockerClient = DockerClientImpl.getInstance(config, httpClient);
    }

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
