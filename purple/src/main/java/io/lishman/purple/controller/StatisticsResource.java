package io.lishman.purple.controller;

import org.springframework.hateoas.core.Relation;

import java.util.Objects;

@Relation(value = "statistics", collectionRelation = "statistics")
public class StatisticsResource {

    private String name;
    private Integer count;

    public StatisticsResource(String name, Integer count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatisticsResource that = (StatisticsResource) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, count);
    }

    @Override
    public String toString() {
        return "StatisticsResource{" +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
