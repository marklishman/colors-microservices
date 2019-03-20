package io.lishman.purple.repository;

import io.lishman.purple.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface GroupJpaRepository extends JpaRepository<Group, Long> {
}
