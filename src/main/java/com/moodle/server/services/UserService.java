package com.moodle.server.services;

import com.moodle.server.models.Group;
import com.moodle.server.models.UserEntity;

import java.util.List;

public interface UserService {

    void deleteUsersByGroupId(Long groupId);

    boolean registerNewUser(String firstName,
                            String lastName,
                            String middleName,
                            Group group,
                            String username,
                            String password);

    List<UserEntity> generateUsers(Integer count, Group group);

    List<UserEntity> findByGroupId(Long groupId);

    UserEntity findUserByName(String username);

    UserEntity updateUser(UserEntity user, String password, String firstName, String lastName, String middleName, String email);

}
