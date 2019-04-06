package io.lishman.green.controller.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.lishman.green.model.Group;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(value = "group", collectionRelation = "groups")
final class GroupResource extends ResourceSupport {

    private final Long id;
    private final String name;
    private final String description;

    private GroupResource(final Long id,
                          final String name,
                          final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    static GroupResource fromGroup(final Group group) {
        return new GroupResource(
                group.getId(),
                group.getName(),
                group.getDescription()
        );
    }

    @JsonProperty("id")
    Long getGroupId() {
        return id;
    }

    String getName() {
        return name;
    }

    String getDescription() {
        return description;
    }
}