package com.trodix.monitoring.servicesmonitoringapi.domain.adapters;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.trodix.monitoring.servicesmonitoringapi.api.responses.HealthStatus;
import com.trodix.monitoring.servicesmonitoringapi.config.DockerAdapterProperties;
import com.trodix.monitoring.servicesmonitoringapi.domain.models.ServiceStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class DockerAdapter {

    private final DockerClient dockerClient;

    public DockerAdapter(DockerAdapterProperties properties) {

        DockerClientConfig config= DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(properties.dockerHost())
                .withRegistryUsername(properties.registryUsername())
                .withRegistryPassword(properties.registryToken())
                .withRegistryEmail(properties.registryEmail())
                .withRegistryUrl(properties.registryUrl())
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

    public List<HealthStatus> getContainersStatus() {
        List<Container> containers = dockerClient.listContainersCmd()
                .withShowAll(true)
                .exec();

        List<HealthStatus> names = containers.stream()
                .map(c ->
                        new HealthStatus(
                                c.getNames()[0],
                                c.getState().equals("Running") ? ServiceStatus.UP : ServiceStatus.DOWN,
                                c.getStatus()))
                .toList();

        return names;
    }

}
