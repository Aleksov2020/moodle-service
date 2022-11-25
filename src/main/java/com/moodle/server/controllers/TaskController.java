package com.moodle.server.controllers;

import com.moodle.server.models.Task;
import com.moodle.server.services.GroupService;
import com.moodle.server.services.TaskListService;
import com.moodle.server.services.TaskService;
import com.moodle.server.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@Slf4j
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;
    private final GroupService groupService;
    private final TaskListService taskListService;

    public TaskController(TaskService taskService,
                          UserService userService,
                          GroupService groupService,
                          TaskListService taskListService) {
        this.taskService = taskService;
        this.groupService = groupService;
        this.userService = userService;
        this.taskListService = taskListService;
    }

    @GetMapping("/tasks")
    public String homePage(Map<String, Object> model) {
        model.put("listTasks", taskService.findAll());
        return "tasks";
    }

    @GetMapping("/task")
    public String homePage(@RequestParam Long taskId,
                           Map<String, Object> model) {
        model.put("task", taskService.findById(taskId));
        model.put("listGroups", groupService.findAll());
        return "task";
    }

    @PostMapping("/generate-massive")
    public String generateMassive(@RequestParam Integer countOfElements,
                                  Map<String, Object> model) {
        model.put("generatedMassive", String.valueOf(taskService.generateMassive(countOfElements)));
        return "tasks";
    }

    @PostMapping("/send-task-to-group")
    public String sendTaskToTheGroup(@RequestParam Long groupId,
                                     @RequestParam Long taskId) {
        log.info("Send tasks to group : " + groupId.toString());
        taskListService.sendToGroupTask(
                taskService.findById(taskId),
                groupService.findById(groupId)
        );
        return "redirect:/tasks";
    }

    @PostMapping("/create-task")
    public String createNewTask(@RequestParam String taskName,
                                @RequestParam String taskDescription,
                                @RequestParam String taskInputValues,
                                @RequestParam String taskOutputValues,
                                Map<String, Object> model) {
        taskService.saveTask(
                new Task(
                        taskName,
                        taskDescription,
                        taskInputValues,
                        taskOutputValues
                )
        );
        //TODO redirect to certain task
        return "redirect:/tasks";
    }

}
