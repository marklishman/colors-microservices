package io.lishman.green.repository;

import io.lishman.green.model.GreenDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="DETAILS")
class GreenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    private String uuid;
    private String correlationId;
    private String details;

    public GreenEntity() {
    }

    private GreenEntity(final Long id,
                        final String uuid,
                        final String correlationId,
                        final String details
    ) {
        this.id = id;
        this.uuid = uuid;
        this.correlationId = correlationId;
        this.details = details;
    }

    static GreenEntity fromDetails(final GreenDetails greenDetails) {
        return new GreenEntity(
                greenDetails.getId(),
                greenDetails.getUuid(),
                greenDetails.getCorrelationId(),
                greenDetails.getDetails()
        );
    }

    Long getId() {
        return id;
    }

    String getUuid() {
        return uuid;
    }

    String getCorrelationId() {
        return correlationId;
    }

    String getDetails() {
        return details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GreenEntity that = (GreenEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "GreenEntity{" +
                "id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", correlationId='" + correlationId + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
