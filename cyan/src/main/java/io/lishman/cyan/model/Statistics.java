package io.lishman.cyan.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class Statistics {

    private final long countryCount;
    private final long personCount;
    private final long userCount;

    private Statistics(final long countryCount,
                       final long personCount, long userCount) {
        this.countryCount = countryCount;
        this.personCount = personCount;
        this.userCount = userCount;
    }

    @JsonCreator
    public static Statistics newInstance(@JsonProperty("countryCount") final long countryCount,
                                         @JsonProperty("personCount") final long personCount,
                                         @JsonProperty("userCount") final long userCount) {
        return new Statistics(countryCount, personCount, userCount);
    }

    public long getCountryCount() {
        return countryCount;
    }

    public long getPersonCount() {
        return personCount;
    }

    public long getUserCount() {
        return userCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return countryCount == that.countryCount &&
                personCount == that.personCount &&
                userCount == that.userCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCount, personCount, userCount);
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "countryCount=" + countryCount +
                ", personCount=" + personCount +
                ", userCount=" + userCount +
                '}';
    }
}
