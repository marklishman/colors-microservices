package io.lishman.green.group;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class Group {

    private final Long id;
    private final String name;
    private final String description;

    private Group(final Long id,
                  final String name,
                  final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    static Group newInstance(final Long id,
                             final String name,
                             final String description) {
        return new Group(id, name, description);
    }

    static Group fromGroupEntity(GroupEntity groupEntity) {
        return Group.newInstance(
                groupEntity.getId(),
                groupEntity.getName(),
                groupEntity.getDescription()
        );
    }

    Group cloneWithNewId(final Long id) {
        return Group.newInstance(id, name, description);
    }

    @JsonProperty("id")
    public Long getGroupId() {
        return id;
    }

    public String getName() {
        return name;
    }

    String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(id, group.id) &&
                Objects.equals(name, group.name) &&
                Objects.equals(description, group.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
