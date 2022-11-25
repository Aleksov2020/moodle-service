package com.moodle.server.services;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.moodle.server.models.Task;
import com.moodle.server.models.UserEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface TaskService {
    List<Task> findAll();
    Task saveTask(Task task);
    Task findById(Long id);
    List<Task> findTasksByUserId(Long userId);
    List<Integer> generateMassive(Integer countOfElements);
    String checkAnswer(String answer, String input, String language, Task task, UserEntity user) throws UnirestException;
}
