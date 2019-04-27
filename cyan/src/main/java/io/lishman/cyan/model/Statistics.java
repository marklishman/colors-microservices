package io.lishman.cyan.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class Statistics {

    private final long userCount;
    private final long countryCount;
    private final long employeeCount;

    private Statistics(final long userCount,
                       final long countryCount,
                       final long employeeCount) {
        this.userCount = userCount;
        this.countryCount = countryCount;
        this.employeeCount = employeeCount;
    }

    @JsonCreator
    public static Statistics newInstance(@JsonProperty("userCount") final long userCount,
                                         @JsonProperty("countryCount") final long countryCount,
                                         @JsonProperty("employeeCount") final long employeeCount) {
        return new Statistics(userCount, countryCount, employeeCount);
    }

    public long getUserCount() {
        return userCount;
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
        return userCount == that.userCount &&
                countryCount == that.countryCount &&
                employeeCount == that.employeeCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userCount, countryCount, employeeCount);
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "userCount=" + userCount +
                ", countryCount=" + countryCount +
                ", employeeCount=" + employeeCount +
                '}';
    }
}
