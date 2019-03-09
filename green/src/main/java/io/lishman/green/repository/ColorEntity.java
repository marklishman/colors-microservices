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
@Table(name="COLOR")
class ColorEntity {

    // TODO Use UUID for Id

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;
    private String correlationId;
    private String colorName;
    private String colorInstance;
    private String details;

    public ColorEntity() {}

    public ColorEntity(final Long id,
                       final String correlationId,
                       final String colorName,
                       final String colorInstance,
                       final String details
    ) {
        this.id = id;
        this.correlationId = correlationId;
        this.colorName = colorName;
        this.colorInstance = colorInstance;
        this.details = details;
    }

    static ColorEntity newInstance(final GreenDetails greenDetails) {
        return new ColorEntity(
                greenDetails.getId(),
                greenDetails.getCorrelationId(),
                greenDetails.getColorName(),
                greenDetails.getColorInstance(),
                greenDetails.getDetails()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorInstance() {
        return colorInstance;
    }

    public void setColorInstance(String colorInstance) {
        this.colorInstance = colorInstance;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColorEntity that = (ColorEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ColorEntity{" +
                "id='" + id + '\'' +
                ", correlationId='" + correlationId + '\'' +
                ", colorName='" + colorName + '\'' +
                ", colorInstance='" + colorInstance + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
