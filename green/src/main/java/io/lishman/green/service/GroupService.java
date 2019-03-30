package io.lishman.green.service;

import io.lishman.green.model.Group;
import io.lishman.green.repo.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class GroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupService.class);

    @Value("${spring.application.name}")
    private String name;

    @Value("${app.instance:one}")
    private String instance;

    @Value("${server.port}")
    private int port;

    @Value("${app.config:default green config}")
    private String config;

    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(final GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    // CRUD

    public Group getById(final Long id) {
        return groupRepository.findById(id)
                .orElseThrow();
    }

    public Group createDetails(final Group group) {
        return this.groupRepository.saveDetails(group);
    }

    public Group updateDetails(final Long id, final Group group) {
        final Group groupWithId = group.cloneWithNewId(id);
        return this.groupRepository.saveDetails(groupWithId);
    }

    // TODO commented out code

    // Projection

//    public InstanceDetails getInstanceDetails(final Long id) {
//
//        // TODO exception handling
//        final GreenDetails greenDetails = groupRepository.findById(id)
//                .orElseThrow();
//
//        InstanceDetails instanceDetails = new InstanceDetails(
//                name,
//                instance,
//                port,
//                config,
//                greenDetails,
//                Collections.emptyList()
//        );
//        LOGGER.info(instanceDetails.toString());
//        return instanceDetails;
//    }

    // Search

//    public GreenDetails getDetailsForCorrelationId(final String correlationId) {
//
//        // TODO exception handling
//        return groupRepository.findByCorrelationId(correlationId)
//                .orElseThrow();
//    }

}
