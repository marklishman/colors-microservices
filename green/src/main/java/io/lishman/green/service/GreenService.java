package io.lishman.green.service;

import io.lishman.green.entity.ColorEntity;
import io.lishman.green.model.InstanceDetails;
import io.lishman.green.repository.ColorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RefreshScope
public class GreenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreenService.class);

    @Value("${spring.application.name}")
    private String name;

    @Value("${app.instance:one}")
    private String instance;

    @Value("${server.port}")
    private int port;

    @Value("${app.config:default green config}")
    private String config;

    private final ColorRepository colorRepository;

    @Autowired
    public GreenService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public InstanceDetails getInstanceDetails() {

        final String colorName = colorRepository.findByColorName("green")
                .map(ColorEntity::getDetails)
                .orElse("");

        final InstanceDetails instanceDetails = new InstanceDetails(
                name,
                instance,
                port,
                config,
                colorName,
                Collections.emptyList()
        );
        LOGGER.info(instanceDetails.toString());
        return instanceDetails;
    }
}
