package io.lishman.green.service;

import io.lishman.green.model.GreenDetails;
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

    public static final String GREEN = "green";
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
    public GreenService(final ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    // CRUD

    public GreenDetails getById(final Long id) {
        return colorRepository.findById(id)
                .orElseThrow();
    }

    public GreenDetails createDetails(final GreenDetails greenDetails) {
        return this.colorRepository.saveDetails(greenDetails);
    }

    public GreenDetails updateDetails(final Long id, final GreenDetails greenDetails) {
        final GreenDetails greenDetailsWithId = greenDetails.cloneWithNewId(id);
        return this.colorRepository.saveDetails(greenDetailsWithId);
    }

    // Projection

    public InstanceDetails getInstanceDetails(final Long id) {

        // TODO exception handling
        final GreenDetails greenDetails = colorRepository.findById(id)
                .orElseThrow();

        return getInstanceDetails(greenDetails);
    }

    // Search

    public GreenDetails getDetailsForCorrelationId(final String correlationId) {

        // TODO exception handling
        return colorRepository.findByCorrelationId(correlationId)
                .orElseThrow();
    }

    // private

    private InstanceDetails getInstanceDetails(GreenDetails greenDetails) {
        InstanceDetails instanceDetails = new InstanceDetails(
                name,
                instance,
                port,
                config,
                greenDetails,
                Collections.emptyList()
        );
        LOGGER.info(instanceDetails.toString());
        return instanceDetails;
    }

}
