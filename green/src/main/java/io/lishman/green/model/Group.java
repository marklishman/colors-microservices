package io.lishman.green.model;

import com.fasterxml.jackson.annotation.JsonCreator;
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

    public static Group newInstance(final Long id,
                                    final String name,
                                    final String description) {
        return new Group(id, name, description);
    }

    // Note that if the 'id' argument is omitted here then it is
    // set on the object if it is included in the json.
    @JsonCreator
    public static Group fromJson(@JsonProperty("id") final Long id,
                                 @JsonProperty("name") final String name,
                                 @JsonProperty("description") final String description) {
        return newInstance(null, name, description);
    }

    public Group cloneWithNewId(final Long id) {
        return Group.newInstance(id, name, description);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
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
