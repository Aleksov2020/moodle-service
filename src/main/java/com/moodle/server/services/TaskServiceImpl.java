package com.moodle.server.services;

import com.moodle.server.exceptions.NotFoundException;
import com.moodle.server.models.Task;
import com.moodle.server.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Task not found")
        );
    }

    @Override
    public List<Integer> generateMassive(Integer countOfElements) {
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        for (int i = 0; i < countOfElements; i++) {
            integerArrayList.add((int) (Math.random() * 1000));
        }
        return integerArrayList;
    }
}
