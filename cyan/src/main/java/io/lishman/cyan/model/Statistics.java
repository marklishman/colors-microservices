package io.lishman.cyan.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class Statistics {

    private final long personCount;
    private final long countryCount;
    private final long employeeCount;

    private Statistics(final long personCount,
                       final long countryCount,
                       final long employeeCount) {
        this.personCount = personCount;
        this.countryCount = countryCount;
        this.employeeCount = employeeCount;
    }

    @JsonCreator
    public static Statistics newInstance(@JsonProperty("personCount") final long personCount,
                                         @JsonProperty("countryCount") final long countryCount,
                                         @JsonProperty("employeeCount") final long employeeCount) {
        return new Statistics(personCount, countryCount, employeeCount);
    }

    public long getPersonCount() {
        return personCount;
    }

    public long getCountryCount() {
        return countryCount;
    }

    public long getEmployeeCount() {
        return employeeCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return personCount == that.personCount &&
                countryCount == that.countryCount &&
                employeeCount == that.employeeCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(personCount, countryCount, employeeCount);
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "personCount=" + personCount +
                ", countryCount=" + countryCount +
                ", employeeCount=" + employeeCount +
                '}';
    }
}
