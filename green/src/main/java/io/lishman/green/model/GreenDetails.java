package io.lishman.green.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class GreenDetails {
    private final Long id;
    private final String correlationId;
    private final String colorName;
    private final String colorInstance;
    private final String details;

    private GreenDetails(final Long id,
                         final String correlationId,
                         final String colorName,
                         final String colorInstance,
                         final String details) {
        this.id = id;
        this.correlationId = correlationId;
        this.colorName = colorName;
        this.colorInstance = colorInstance;
        this.details = details;
    }

    public static GreenDetails newInstance(final Long id,
                                           final String correlationId,
                                           final String colorName,
                                           final String colorInstance,
                                           final String details) {
        return new GreenDetails(id, correlationId, colorName, colorInstance, details);
    }

    // Note that if the 'id' argument is omitted here then it is
    // set on the object if it is included in the json.
    @JsonCreator
    public static GreenDetails fromJson(@JsonProperty("id") final Long id,
                                        @JsonProperty("correlationId") final String correlationId,
                                        @JsonProperty("colorName") final String colorName,
                                        @JsonProperty("colorInstance") final String colorInstance,
                                        @JsonProperty("details") final String details) {
        return newInstance(null, correlationId, colorName, colorInstance, details);
    }

    public final Long getId() {
        return id;
    }

    public final String getCorrelationId() {
        return correlationId;
    }

    public final String getColorName() {
        return colorName;
    }

    public final String getColorInstance() {
        return colorInstance;
    }

    public final String getDetails() {
        return details;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GreenDetails that = (GreenDetails) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(correlationId, that.correlationId) &&
                Objects.equals(colorName, that.colorName) &&
                Objects.equals(colorInstance, that.colorInstance) &&
                Objects.equals(details, that.details);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, correlationId, colorName, colorInstance, details);
    }

    @Override
    public final String toString() {
        return "GreenDetails{" +
                "id='" + id + '\'' +
                ", correlationId='" + correlationId + '\'' +
                ", colorName='" + colorName + '\'' +
                ", colorInstance='" + colorInstance + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
