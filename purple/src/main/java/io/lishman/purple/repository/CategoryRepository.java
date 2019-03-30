package io.lishman.purple.repository;

import io.lishman.purple.entity.CategoryEntity;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource (
        path = "categories",
        collectionResourceRel = "categories",
        collectionResourceDescription = @Description("A collection of categories"),
        itemResourceRel = "category",
        itemResourceDescription = @Description("A single category")
)
public interface CategoryRepository extends Repository<CategoryEntity, Long> {

    Optional<CategoryEntity> findById(final Long id);

    List<CategoryEntity> findAll();

    Optional<CategoryEntity> findByName(final String name);

}
