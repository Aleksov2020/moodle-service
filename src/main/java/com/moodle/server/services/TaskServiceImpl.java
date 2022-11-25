package com.moodle.server.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.moodle.server.exceptions.NotFoundException;
import com.moodle.server.models.Attempt;
import com.moodle.server.models.Task;
import com.moodle.server.models.TaskList;
import com.moodle.server.models.UserEntity;
import com.moodle.server.repository.TaskListRepository;
import com.moodle.server.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;
    private final AttemptService attemptService;

    public TaskServiceImpl(TaskRepository taskRepository,
                           AttemptService attemptService,
                           TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.attemptService = attemptService;
        this.taskListRepository = taskListRepository;
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
    public List<Task> findTasksByUserId(Long userId) {
        return null;
    }

    @Override
    public List<Integer> generateMassive(Integer countOfElements) {
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        for (int i = 0; i < countOfElements; i++) {
            integerArrayList.add((int) (Math.random() * 1000));
        }
        return integerArrayList;
    }

    @Override
    public String checkAnswer(String answer, String input, String language, Task task, UserEntity user) throws UnirestException {
        JSONObject result = getCompileResult(answer, input, language);

        if (result.getBoolean("success")) {
            if (task.getOutput().equals(result.getString("output"))) {
                log.info("Answer is correct");

                TaskList taskListUpdated = taskListRepository.findByUserEntity_IdAndTask_TaskId(user.getId(), task.getTaskId());
                List<Attempt> attemptList = taskListUpdated.getAttempts();
                taskListUpdated.setWasAnswered(java.time.LocalDateTime.now());
                taskListUpdated.setIsPassed(true);

                taskListUpdated.setMark(
                        setMark(
                            attemptList.size()
                        )
                );

                attemptList.add(attemptService.createAttempt(user, task, result.getString("output"), answer, true));
                taskListUpdated.setAttempts(attemptList);

                taskListRepository.save(taskListUpdated);

                return "CORRECT_ANSWER";
            } else {
                log.info("Answer is incorrect");

                TaskList taskListUpdated = taskListRepository.findByUserEntity_IdAndTask_TaskId(user.getId(), task.getTaskId());
                List<Attempt> attemptList = taskListUpdated.getAttempts();
                attemptList.add(attemptService.createAttempt(user, task, result.getString("output"), answer, false));
                taskListUpdated.setAttempts(attemptList);
                taskListRepository.save(taskListUpdated);

                return "INCORRECT_ANSWER";
            }
        }
        log.info("error" + result.getString("error"));
        attemptService.createAttempt(user, task, result.getString("error"), answer, false);
        return "CODE_ERROR";
    }

    private JSONObject getCompileResult(String code, String input, String language) throws UnirestException {
        if (input == null) {
            input = "";
        }

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("https://codex-api.herokuapp.com")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("code", code)
                .field("language", language)
                .field("input", input)
                .asString();
        return new JSONObject(response.getBody());
    }

    private String setMark(Integer countOfAttempts) {
        switch(countOfAttempts) {
            case 0:
                return "5+";
            case 1:
                return "5";
            case 2:
                return "5-";
            case 3:
                return "4+";
            case 4:
                return "4";
            case 5:
                return "4-";
            case 6:
                return "3+";
            case 7:
                return "3";
            case 8:
                return "3-";
            case 9:
                return "2";
            default:
                return "0";
        }
    }
}
