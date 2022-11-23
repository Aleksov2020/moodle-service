package com.moodle.server.services;

import com.moodle.server.models.UserEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface DownloadService {
    InputStream downloadUsersFromGroupList(Long groupId,  List<UserEntity> userEntityList) throws IOException;
}
