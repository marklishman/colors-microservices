package io.lishman.green.repository;

import io.lishman.green.entity.UserEntity;
import io.lishman.green.testing.fixtures.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

class UserRepositoryTest {

    @DataJpaTest
    @Sql("/user-data.sql")
    @Nested
    @DisplayName("findAll() method")
    class FindAll {

        @Autowired
        private UserRepository repository;

        @Test
        @DisplayName("Given users exist in the database, then all these users are returned")
        void existingData() {

            final List<UserEntity> userEntities = repository.findAll();

            assertThat(userEntities, hasSize(5));
        }

        @Test
        @DisplayName("Given users exist in the database, " +
                "when another user is added, " +
                "then all the users including the new one are returned")
        void newData(@Autowired TestEntityManager entityManager) {
            entityManager.persist(UserFixture.bobSnithEntity());
            entityManager.flush();

            final List<UserEntity> userEntities = repository.findAll();

            assertThat(userEntities, hasSize(6));
        }
    }

    @DataJpaTest
    @Sql("/user-data.sql")
    @Nested
    @DisplayName("findByLastNameContainingIgnoreCaseOrderByLastNameDesc() method")
    class FindByLastNameContainingIgnoreCaseOrderByLastNameDesc {

        @Autowired
        private UserRepository repository;

        @Test
        @DisplayName("Given users exist in the database, then all these users are returned")
        void existingData() {

            final List<UserEntity> userEntities = repository.findByLastNameContainingIgnoreCaseOrderByLastNameDesc("CH");

            assertThat(userEntities, hasSize(2));
            assertThat(userEntities.get(0).getLastName(), is(equalTo("Dietrich")));
            assertThat(userEntities.get(1).getLastName(), is(equalTo("Bauch")));
        }
    }
}
