package com.moodle.server.services;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.moodle.server.models.Task;
import com.moodle.server.models.UserEntity;

import java.util.List;

public interface TaskService {
    List<Task> findAll();

    Task saveTask(Task task);

    Task findById(Long id);

    String checkAnswer(String answer, String input, String language, Task task, UserEntity user) throws UnirestException;
}
