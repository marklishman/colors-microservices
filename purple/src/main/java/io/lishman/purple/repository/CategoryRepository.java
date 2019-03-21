package io.lishman.purple.repository;

import io.lishman.purple.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
