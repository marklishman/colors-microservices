package io.lishman.green.repository;


import io.lishman.green.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findByNameContainingIgnoreCase(final String nameContains);

}
