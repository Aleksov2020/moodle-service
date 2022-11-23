package com.moodle.server.services;

import com.moodle.server.models.Task;

import java.util.List;

public interface TaskService {
    List<Task> findAll();
    Task saveTask(Task task);
    Task findById(Long id);

    List<Integer> generateMassive(Integer countOfElements);
}
