package io.lishman.purple.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itm_id", columnDefinition = "serial")
    private Long id;

    @Column(name = "itm_uuid")
    private String uuid;

    @Column(name = "itm_name")
    private String name;

    @Column(name = "itm_desc")
    private String description;

    @Column(name = "itm_correlation_id")
    private String correlationId;

    @Column(name = "itm_status")
    private Integer status;

    // TODO Automatically populate created date
    @Column(name = "itm_created_at")
    private Date createdAt;

    public Item() {
    }

    private Item(final String uuid,
                 final String name,
                 final String description,
                 final String correlationId,
                 final Integer status,
                 final Date createdAt) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.correlationId = correlationId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public Integer getStatus() {
        return status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", correlationId='" + correlationId + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
