package io.lishman.green.group;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    List<Group> getAllGroups() {
        // TODO Exception handling
        return groupRepository.findAll()
                .stream()
                .map(Group::fromGroupEntity)
                .collect(Collectors.toList());
    }

    Group getById(final Long id) {
        // TODO Exception handling
        return groupRepository.findById(id)
                .map(Group::fromGroupEntity)
                .orElseThrow();
    }

    Group createGroup(final Group group) {
        final var groupEntity = GroupEntity.fromGroup(group);
        final var savedGroupEntity = this.groupRepository.save(groupEntity);
        return Group.fromGroupEntity(savedGroupEntity);
    }

    Group updateGroup(final Long id, final Group group) {
        final var groupWithId = group.cloneWithNewId(id);
        return this.createGroup(groupWithId);
    }
}
