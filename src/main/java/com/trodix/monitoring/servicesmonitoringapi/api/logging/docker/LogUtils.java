package com.trodix.monitoring.servicesmonitoringapi.api.logging.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.LogContainerCmd;
import com.trodix.monitoring.servicesmonitoringapi.api.logging.docker.output.FrameConsumerResultCallback;
import com.trodix.monitoring.servicesmonitoringapi.api.logging.docker.output.OutputFrame;
import com.trodix.monitoring.servicesmonitoringapi.api.logging.docker.output.ToStringConsumer;
import com.trodix.monitoring.servicesmonitoringapi.api.logging.docker.output.WaitingConsumer;
import lombok.SneakyThrows;

import java.io.Closeable;
import java.io.IOException;
import java.util.function.Consumer;

public class LogUtils {

    /**
     * Attach a log consumer to a container's log outputs in follow mode. The consumer will receive all previous
     * and all future log frames of the specified type(s).
     *
     * @param dockerClient a Docker client
     * @param containerId  container ID to attach to
     * @param consumer     a consumer of {@link OutputFrame}s
     * @param types        types of {@link OutputFrame} to receive
     */
    public void followOutput(
            DockerClient dockerClient,
            String containerId,
            Consumer<OutputFrame> consumer,
            Integer since,
            Integer until,
            OutputFrame.OutputType... types
    ) {
        attachConsumer(dockerClient, containerId, consumer, true, since, until, types);
    }

    /**
     * Attach a log consumer to a container's log outputs in follow mode. The consumer will receive all previous
     * and all future log frames (both stdout and stderr).
     *
     * @param dockerClient a Docker client
     * @param containerId  container ID to attach to
     * @param consumer     a consumer of {@link OutputFrame}s
     */
    public void followOutput(DockerClient dockerClient, String containerId, Consumer<OutputFrame> consumer, Integer since, Integer until) {
        followOutput(dockerClient, containerId, consumer, since, until, OutputFrame.OutputType.STDOUT, OutputFrame.OutputType.STDERR);
    }

    /**
     * Retrieve all previous log outputs for a container of the specified type(s).
     *
     * @param dockerClient a Docker client
     * @param containerId  container ID to attach to
     * @param types        types of {@link OutputFrame} to receive
     * @return all previous output frames (stdout/stderr being separated by newline characters)
     */
    @SneakyThrows(IOException.class)
    public String getOutput(DockerClient dockerClient, String containerId, Integer since, Integer until, OutputFrame.OutputType... types) {
        if (containerId == null) {
            return "";
        }

        if (types.length == 0) {
            types = new OutputFrame.OutputType[] { OutputFrame.OutputType.STDOUT, OutputFrame.OutputType.STDERR };
        }

        final ToStringConsumer consumer = new ToStringConsumer();
        final WaitingConsumer wait = new WaitingConsumer();
        try (Closeable closeable = attachConsumer(dockerClient, containerId, consumer.andThen(wait), false, since, until, types)) {
            wait.waitUntilEnd();
            return consumer.toUtf8String();
        }
    }

    private static Closeable attachConsumer(
            DockerClient dockerClient,
            String containerId,
            Consumer<OutputFrame> consumer,
            boolean followStream,
            Integer since,
            Integer until,
            OutputFrame.OutputType... types
    ) {
        final LogContainerCmd cmd = dockerClient
                .logContainerCmd(containerId)
                .withFollowStream(followStream);

        if (since != null) {
            cmd.withSince(since);
        } else {
            cmd.withSince(0);
        }

        if (until != null) {
            cmd.withUntil(until);
        }

        final FrameConsumerResultCallback callback = new FrameConsumerResultCallback();
        for (OutputFrame.OutputType type : types) {
            callback.addConsumer(type, consumer);
            if (type == OutputFrame.OutputType.STDOUT) {
                cmd.withStdOut(true);
            }
            if (type == OutputFrame.OutputType.STDERR) {
                cmd.withStdErr(true);
            }
        }

        return cmd.exec(callback);
    }
}