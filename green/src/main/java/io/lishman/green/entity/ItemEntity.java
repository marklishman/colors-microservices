package io.lishman.green.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.lishman.green.group.service.GroupEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="item")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itm_id", columnDefinition = "serial")
    private Long id;

    @Column(name = "itm_uuid")
    private UUID uuid;

    @Column(name = "itm_name")
    private String name;

    @Column(name = "itm_desc")
    private String description;

    @Column(name = "itm_correlation_id")
    private UUID correlationId;

    @Column(name = "itm_status")
    private Integer status;

    @Column(name = "itm_created_at", updatable = false)
    private LocalDateTime createdAt;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "grp_id")
    private GroupEntity group;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "itm_id")
    private List<DataEntity> data;

    public ItemEntity() {
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public UUID getCorrelationId() {
        return correlationId;
    }

    public Integer getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public BigDecimal getTotal() {
        return this.data.stream()
                .map(DataEntity::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public GroupEntity getGroup() {
        return group;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        data.forEach(d -> d.setItem(this));
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemEntity item = (ItemEntity) o;
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
