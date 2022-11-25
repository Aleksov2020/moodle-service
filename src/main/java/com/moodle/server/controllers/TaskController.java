package com.moodle.server.controllers;

import com.moodle.server.models.Task;
import com.moodle.server.services.GroupService;
import com.moodle.server.services.TaskListService;
import com.moodle.server.services.TaskService;
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
    private final GroupService groupService;
    private final TaskListService taskListService;

    public TaskController(TaskService taskService,
                          GroupService groupService,
                          TaskListService taskListService) {
        this.taskService = taskService;
        this.groupService = groupService;
        this.taskListService = taskListService;
    }

    @GetMapping("/tasks")
    public String allTasks(Map<String, Object> model) {
        model.put("listTasks", taskService.findAll());
        return "tasks";
    }

    @GetMapping("/task")
    public String singleTask(@RequestParam Long taskId,
                           Map<String, Object> model) {
        model.put("task", taskService.findById(taskId));
        model.put("listGroups", groupService.findAll());
        return "task";
    }

    @PostMapping("/send-task-to-group")
    public String sendTaskToTheGroup(@RequestParam Long groupId,
                                     @RequestParam Long taskId) {
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
                                @RequestParam String taskOutputValues) {
        taskService.saveTask(
                new Task(
                        taskName,
                        taskDescription,
                        taskInputValues,
                        taskOutputValues
                )
        );
        //TODO redirect to certain task maybe
        return "redirect:/tasks";
    }

}
