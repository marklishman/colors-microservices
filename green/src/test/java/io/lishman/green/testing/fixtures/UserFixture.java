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
                1L,
                "Leanne",
                "Graham",
                "Bret",
                "Sincere@april.biz",
                "1-770-736-8031 x56442",
                17,
                "hildegard.org"
        );
    }

    public static User chelseyDietrich() {
        return User.newInstance(
                5L,
                "Chelsey",
                "Dietrich",
                "Kamren",
                "Lucio_Hettinger@annie.ca",
                "(254)954-1289",
                36,
                "demarco.info"
        );
    }

    public static User nicholasRunolfsdottir() {
        return User.newInstance(
                8L,
                "Nicholas",
                "Runolfsdottir V",
                "Maxime_Nienow",
                "Sherwood@rosamond.me",
                "586.493.6943 x140",
                43,
                "jacynthe.com"
        );
    }

    public static User bobSmithWithNullId() {
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

    public static User bobSmith() {
        return User.newInstance(
                6L,
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

    public static UserEntity chelseyDietrichEntity() {
        return UserEntity.fromUser(UserFixture.chelseyDietrich());
    }


    public static UserEntity nicholasRunolfsdottirEntity() {
        return UserEntity.fromUser(UserFixture.nicholasRunolfsdottir());
    }

    public static UserEntity bobSmithWithNullIdEntity() {
        return UserEntity.fromUser(UserFixture.bobSmithWithNullId());
    }

    public static UserEntity bobSmithEntity() {
        return UserEntity.fromUser(UserFixture.bobSmith());
    }

    public static String bobSmithJson() {
        return  "{\n" +
                "  \"firstName\": \"Bob\",\n" +
                "  \"lastName\": \"Smith\",\n" +
                "  \"userName\": \"user.me\",\n" +
                "  \"email\": \"abc@email.com\",\n" +
                "  \"phoneNumber\": \"01772 776453\",\n" +
                "  \"age\": 25,\n" +
                "  \"website\": \"www.example.com\"\n" +
                "}";
    }
}
