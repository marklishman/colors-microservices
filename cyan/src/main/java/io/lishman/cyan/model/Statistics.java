package io.lishman.cyan.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class Statistics {

    private final long personCount;
    private final long countryCount;
    private final long userCount;

    private Statistics(final long personCount,
                       final long countryCount,
                       final long userCount) {
        this.personCount = personCount;
        this.countryCount = countryCount;
        this.userCount = userCount;
    }

    @JsonCreator
    public static Statistics newInstance(@JsonProperty("personCount") final long personCount,
                                         @JsonProperty("countryCount") final long countryCount,
                                         @JsonProperty("userCount") final long userCount) {
        return new Statistics(personCount, countryCount, userCount);
    }

    public long getPersonCount() {
        return personCount;
    }

    public long getCountryCount() {
        return countryCount;
    }

    public long getUserCount() {
        return userCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return personCount == that.personCount &&
                countryCount == that.countryCount &&
                userCount == that.userCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(personCount, countryCount, userCount);
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "personCount=" + personCount +
                ", countryCount=" + countryCount +
                ", userCount=" + userCount +
                '}';
    }
}
