package com.moodle.server.services;

import com.moodle.server.models.UserEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class DownloadServiceImpl implements DownloadService{

    @Override
    public InputStream downloadUsersFromGroupList(Long groupId, List<UserEntity> userEntityList) {
        StringBuilder resultString = new StringBuilder();
        for(UserEntity student : userEntityList){
            resultString.append("Username : ").append(student.getUsername()).append(" Password : ").append(student.getDecodePassword()).append("\n");
        }
        return new ByteArrayInputStream(resultString.toString().getBytes());
    }
}
