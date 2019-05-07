package io.lishman.green.repository;

import io.lishman.green.entity.UserEntity;
import io.lishman.green.testing.fixtures.UserFixture;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static io.lishman.green.testing.matchers.UserEntityMatcher.matchesUserEntity;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.is;

@DataJpaTest
@Sql("/user-data.sql")
class UserRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    public void afterEach() {

        final String seq = jdbcTemplate.queryForObject(
                "SELECT sequence_name FROM information_schema.sequences LIMIT 1",
                String.class
        );

        jdbcTemplate.update("ALTER SEQUENCE green." + seq + " RESTART WITH 1");
    }

    @DataJpaTest
    @Sql("/user-data.sql")
    @Nested
    @DisplayName("findAll() method")
    class FindAll {

        @Autowired
        private UserRepository repository;

        @Test
        @DisplayName("Given users exist in the database, then all these users are returned")
        void listOfUser() {

            final List<UserEntity> actualUserEntities = repository.findAll();

            assertThat(actualUserEntities, hasSize(5));
            assertThat(actualUserEntities, Matchers.hasItems(
                    matchesUserEntity(UserFixture.leanneGrahamEntity()),
                    matchesUserEntity(UserFixture.chelseyDietrichEntity()))
            );
        }
    }

    @DataJpaTest
    @Sql("/user-data.sql")
    @Nested
    @DisplayName("findById(Long) method")
    class FindById {

        @Autowired
        private UserRepository repository;

        @Test
        @DisplayName("Given a user does not exist in the database, " +
                "when a search is made on a missing id, " +
                "then an empty optional is returned")
        void userDoesNotExist() {
            final Long MISSING_ID = 99L;
            final Optional<UserEntity> actualUserEntity = repository.findById(MISSING_ID);
            assertThat(actualUserEntity.isEmpty(), is(equalTo(true)));
        }

        @Test
        @DisplayName("Given a user exists in the database, " +
                "when a search is made on this users id, " +
                "then the user is returned")
        void userExists() {
            final Long ID = UserFixture.chelseyDietrich().getId();
            final UserEntity actualUserEntity = repository.findById(ID).get();
            assertThat(actualUserEntity, is(equalTo(UserFixture.chelseyDietrichEntity())));
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
        @DisplayName("Given users exist in the database, " +
                "when a search is performed on the surname, " +
                "then only the matching users are returned " +
                "and the search is case insensitive " +
                "and the results are ordered by descending surname")
        void userSearch() {

            final List<UserEntity> userEntities = repository.findByLastNameContainingIgnoreCaseOrderByLastNameDesc("CH");

            assertThat(userEntities, hasSize(2));
            assertThat(userEntities.get(0).getLastName(), is(equalTo("Dietrich")));
            assertThat(userEntities.get(1).getLastName(), is(equalTo("Bauch")));
        }
    }

    @DataJpaTest
    @Sql("/user-data.sql")
    @Nested
    @DisplayName("findById(Long) method")
    class createUser {

        @Autowired
        private UserRepository repository;

        @Test
        @DisplayName("Given users exists in the database, " +
                "when a new user is created, " +
                "then a row is added to the database, " +
                "and the id is the next in the sequence")
        void newUserAdded(@Autowired TestEntityManager entityManager) {
            final UserEntity insertedBobSmithEntity = repository.saveAndFlush(UserFixture.bobSmithEntity());

            assertThat(insertedBobSmithEntity, is(equalTo(UserFixture.bobSmithEntity())));

            final Long ID = UserFixture.bobSmith().getId();
            final UserEntity actualUserEntity = entityManager.find(UserEntity.class, ID);
            assertThat(actualUserEntity, is(equalTo(UserFixture.bobSmithEntity())));
        }
    }
}
