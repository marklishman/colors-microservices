package io.lishman.green.repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="COLOR")
class ColorEntity {

    @Id
    private Long id;
    private String correlationId;
    private String colorName;
    private String colorInstance;
    private String details;

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
