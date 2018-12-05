package io.lishman.green.service;

import io.lishman.green.model.InstanceDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.task.launcher.TaskLaunchRequest;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RefreshScope
@EnableBinding(Source.class)
public class GreenService {

    @Value("${spring.application.name}")
    private String name;

    @Value("${token.instance:one}")
    private String instance;

    @Value("${server.port}")
    private int port;

    @Value("${token.config:default green config}")
    private String config;

    @Value("${token.task.launch.request:false}")
    private boolean taskLaunchRequest;

    private final Source source;

    public GreenService(Source source) {
        this.source = source;
    }

    public InstanceDetails getInstanceDetails() {

        InstanceDetails instanceDetails = new InstanceDetails(
                name,
                instance,
                port,
                config,
                Collections.emptyList()
        );

        if (taskLaunchRequest) {
            launchRequest(instanceDetails);
        }

        return instanceDetails;
    }

    private void launchRequest(InstanceDetails instanceDetails) {
        String url = "maven://io.lishman:task-one:jar:0.0.1-SNAPSHOT";
        List<String> params = Arrays.asList(
                instanceDetails.getName(),
                instanceDetails.getInstance(),
                String.valueOf(instanceDetails.getPort()),
                instanceDetails.getConfig()
        );

        TaskLaunchRequest request = new TaskLaunchRequest(url, params, null, null, "green");
        GenericMessage<TaskLaunchRequest> message = new GenericMessage<>(request);
        source.output().send(message);
    }
}
