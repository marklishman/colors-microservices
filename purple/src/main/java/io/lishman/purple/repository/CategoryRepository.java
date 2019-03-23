package io.lishman.purple.repository;

import io.lishman.purple.entity.CategoryEntity;
import io.lishman.purple.entity.CategoryNameProjection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "categories", collectionResourceRel = "categories", excerptProjection = CategoryNameProjection.class)
public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByName(final String name);

}
