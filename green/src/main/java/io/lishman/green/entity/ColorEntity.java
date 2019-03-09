package io.lishman.green.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    public String toString() {
        return "ColorEntity{" +
                "id='" + id + '\'' +
                ", colorId='" + colorId + '\'' +
                ", colorName='" + colorName + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
