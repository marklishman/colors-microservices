package io.lishman.cyan.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class Category {

    private final Long id;
    private final String name;
    private final String description;

    private Category(final Long id,
                     final String name,
                     final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @JsonCreator
    private static Category newInstance(@JsonProperty("id") final Long id,
                                        @JsonProperty("name") final String name,
                                        @JsonProperty("description") final String description) {
        return new Category(id, name, description);
    }

    public Category cloneWithNewId(final Long id) {
        return Category.newInstance(id, name, description);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(name, category.name) &&
                Objects.equals(description, category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
