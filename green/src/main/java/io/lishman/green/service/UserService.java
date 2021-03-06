package io.lishman.green.service;

import io.lishman.green.entity.UserEntity;
import io.lishman.green.exception.UserResourceNotFoundException;
import io.lishman.green.model.User;
import io.lishman.green.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        LOGGER.info("Get All Users");
        return userRepository.findAll()
                .stream()
                .map(User::fromUserEntity)
                .collect(Collectors.toList());
    }

    public User getUserById(final Long id) {
        LOGGER.info("Get User {}", id);
        return userRepository.findById(id)
                .map(User::fromUserEntity)
                .orElseThrow(() -> new UserResourceNotFoundException(id));
    }

    public User createUser(final User user) {
        LOGGER.info("Create User {}", user.getUserName());

        final var userEntity = UserEntity.fromUser(user);

        final var savedUserEntity = this.userRepository.save(userEntity);

        return User.fromUserEntity(savedUserEntity);
    }

    public User updateUser(final Long id, final User user) {
        LOGGER.info("Update User {}", id);

        if (!userRepository.existsById(id)) {
            throw new UserResourceNotFoundException(id);
        }

        final var userWithId = user.cloneWithNewId(id);
        final var userEntity = UserEntity.fromUser(userWithId);

        final var savedUserEntity = this.userRepository.save(userEntity);

        return User.fromUserEntity(savedUserEntity);
    }

    public void deleteUser(final Long id) {
        LOGGER.info("Delete User {}", id);

        if (!userRepository.existsById(id)) {
            throw new UserResourceNotFoundException(id);
        }

        userRepository.deleteById(id);
    }
}
