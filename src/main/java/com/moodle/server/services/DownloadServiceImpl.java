package com.moodle.server.services;

import com.moodle.server.models.UserEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class DownloadServiceImpl implements DownloadService{

    @Override
    public InputStream downloadUsersFromGroupList(Long groupId, List<UserEntity> userEntityList) {
        String resultString = "";
        for(UserEntity student : userEntityList){
            resultString += "Username : " + student.getUsername() + " Password : " + student.getPassword() + "\n";
        }
        return new ByteArrayInputStream(resultString.getBytes());
    }
}
