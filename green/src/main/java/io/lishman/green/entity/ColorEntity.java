package io.lishman.green.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="COLOR")
public class ColorEntity {

    @Id
    private String id;
    private String colorId;
    private String colorName;
    private String details;

    public ColorEntity() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
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
        return Objects.equals(id, that.id) &&
                Objects.equals(colorId, that.colorId) &&
                Objects.equals(colorName, that.colorName) &&
                Objects.equals(details, that.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, colorId, colorName, details);
    }

    @Override
    public String toString() {
        return "ColorEntity{" +
                "id='" + id + '\'' +
                ", colorId='" + colorId + '\'' +
                ", colorName='" + colorName + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
