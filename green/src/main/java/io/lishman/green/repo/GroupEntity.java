package io.lishman.green.repo;

import io.lishman.green.model.Group;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="group")
class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grp_id", columnDefinition = "serial")
    private Long id;

    @Column(name = "grp_name")
    private String name;

    @Column(name = "grp_desc")
    private String description;

    public GroupEntity() {
    }

    private GroupEntity(final Long id,
                        final String name,
                        final String description
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    static GroupEntity fromGroup(final Group group) {
        return new GroupEntity(
                group.getId(),
                group.getName(),
                group.getDescription()
        );
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
        GroupEntity that = (GroupEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "GroupEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
