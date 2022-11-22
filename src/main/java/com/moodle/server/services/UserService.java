package com.moodle.server.services;
import com.moodle.server.models.Group;
import com.moodle.server.models.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity findUserById(Long userId);

    boolean registerNewUser(String firstName,
                            String lastName,
                            String middleName,
                            Group group,
                            String username,
                            String password);

    UserEntity findUserByName(String username);

    List<UserEntity> findAll();

    UserEntity saveUser(UserEntity userEntity);

    boolean deleteUser(Long userId);
}
