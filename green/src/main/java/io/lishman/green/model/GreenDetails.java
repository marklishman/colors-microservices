package io.lishman.green.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class GreenDetails {
    private final Long id;
    private final String uuid;
    private final String correlationId;
    private final String details;

    // TODO combine with Entity

    // Factory methods

    private GreenDetails(final Long id,
                         final String uuid,
                         final String correlationId,
                         final String details) {
        this.id = id;
        this.uuid = uuid;
        this.correlationId = correlationId;
        this.details = details;
    }

    public static GreenDetails newInstance(final Long id,
                                           final String uuid,
                                           final String correlationId,
                                           final String details) {
        return new GreenDetails(id, uuid, correlationId, details);
    }

    // Note that if the 'id' argument is omitted here then it is
    // set on the object if it is included in the json.
    @JsonCreator
    public static GreenDetails fromJson(@JsonProperty("id") final Long id,
                                        @JsonProperty("uuid") final String uuid,
                                        @JsonProperty("correlationId") final String correlationId,
                                        @JsonProperty("details") final String details) {
        return newInstance(null, uuid, correlationId, details);
    }

    public GreenDetails cloneWithNewId(final Long id) {
        return GreenDetails.newInstance(id, uuid, correlationId, details);
    }

    // getters

    public final Long getId() {
        return id;
    }

    public final String getUuid() {
        return uuid;
    }

    public final String getCorrelationId() {
        return correlationId;
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
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(correlationId, that.correlationId) &&
                Objects.equals(details, that.details);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id, uuid, correlationId, details);
    }

    @Override
    public final String toString() {
        return "GreenDetails{" +
                "id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", correlationId='" + correlationId + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
