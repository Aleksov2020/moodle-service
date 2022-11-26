package com.moodle.server.controllers;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.moodle.server.models.Task;
import com.moodle.server.models.TaskList;
import com.moodle.server.services.GroupService;
import com.moodle.server.services.TaskListService;
import com.moodle.server.services.TaskService;
import com.moodle.server.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class TaskController {
    private final TaskService taskService;
    private final GroupService groupService;
    private final UserService userService;
    private final TaskListService taskListService;

    public TaskController(TaskService taskService,
                          GroupService groupService,
                          TaskListService taskListService,
                          UserService userService) {
        this.taskService = taskService;
        this.groupService = groupService;
        this.taskListService = taskListService;
        this.userService = userService;
    }

    @GetMapping("/tasks")
    public String allTasks(HttpServletRequest httpServletRequest,
                           Authentication authentication,
                           Map<String, Object> model) {
        if (httpServletRequest.isUserInRole("ADMIN")) {
            model.put("listTasks", taskService.findAll());
        } else {
            List<TaskList> passedTasksList = taskListService.findPassedTaskListByUserId(
                    userService.findUserByName(authentication.getName()).getId()
            );
            List<TaskList> notPassedTasksList = taskListService.findWaitingTaskListByUserId(
                    userService.findUserByName(authentication.getName()).getId()
            );
            model.put("passedTasks", passedTasksList);
            model.put("notPassedTasks", notPassedTasksList);

        }
        return "tasks";
    }

    @GetMapping("/task")
    public String singleTask(@RequestParam Long taskId,
                             HttpServletRequest httpServletRequest,
                             Map<String, Object> model) {
        model.put("task", taskService.findById(taskId));
        if (httpServletRequest.isUserInRole("ADMIN")) {
            model.put("listGroups", groupService.findAll());
        }
        // TODO print which groups get this task
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
        return "redirect:/tasks";
    }

    @PostMapping("/send-answer")
    public String checkAnswer(Authentication authentication,
                              @RequestParam String answerCode,
                              @RequestParam String input,
                              @RequestParam String language,
                              @RequestParam Long taskId,
                              Map<String, Object> model) throws UnirestException {
        String result = taskService.checkAnswer(
                answerCode,
                input,
                language,
                taskService.findById(taskId),
                userService.findUserByName(authentication.getName())
        );

        if ((result.equals("INCORRECT_ANSWER")) || (result.equals("CODE_ERROR"))) {
            model.put("resultStatus", result);
            model.put("task", taskService.findById(taskId));
            return "task";
        }

        return "redirect:/tasks";
    }

}
