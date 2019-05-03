package io.lishman.green.repository;

import io.lishman.green.entity.UserEntity;
import io.lishman.green.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@DataJpaTest
@Sql("/user-data.sql")
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    @Test
    public void testExample() {
        List<UserEntity> userEntities = this.repository.findAll();
        assertThat(userEntities, hasSize(5));
    }

    @Test
    public void anotherExample() {
        entityManager.persist(userEntity());
        entityManager.flush();
        List<UserEntity> userEntities = this.repository.findAll();
        assertThat(userEntities, hasSize(6));
    }

    private UserEntity userEntity() {
        return UserEntity.fromUser(user());
    }

    private User user() {
        return User.newInstance(
                null,
                "Bob",
                "Smith",
                "user.me",
                "abc@email.com",
                "01772 776453",
                25,
                "www.example.com"
        );
    }
}
