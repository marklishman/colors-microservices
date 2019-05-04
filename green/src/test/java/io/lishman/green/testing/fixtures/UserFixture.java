package io.lishman.green.testing.fixtures;

import io.lishman.green.entity.UserEntity;
import io.lishman.green.model.User;

import java.util.List;

public class UserFixture {

    private UserFixture() {
    }

    public static List<User> users() {
        return List.of(
                leanneGraham(),
                nicholasRunolfsdottir()
        );
    }

    public static User leanneGraham() {
        return User.newInstance(
                16L,
                "Leanne",
                "Graham",
                "Bret",
                "Sincere@april.biz",
                "1-770-736-8031 x56442",
                17,
                "hildegard.org"
        );
    }

    public static User nicholasRunolfsdottir() {
        return User.newInstance(
                23L,
                "Nicholas",
                "Runolfsdottir V",
                "Maxime_Nienow",
                "Sherwood@rosamond.me",
                "586.493.6943 x140",
                43,
                "jacynthe.com"
        );
    }

    public static User bobSmith() {
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

    public static UserEntity leanneGrahamEntity() {
        return UserEntity.fromUser(UserFixture.leanneGraham());
    }
}
