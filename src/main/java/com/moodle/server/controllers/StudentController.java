package com.moodle.server.controllers;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.moodle.server.models.TaskList;
import com.moodle.server.services.TaskListService;
import com.moodle.server.services.TaskService;
import com.moodle.server.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class StudentController {
    private final UserService userService;
    private final TaskListService taskListService;
    private final TaskService taskService;

    public StudentController(UserService userService, TaskListService taskListService,
                             TaskService taskService) {
        this.userService = userService;
        this.taskListService = taskListService;
        this.taskService = taskService;
    }
    @GetMapping("/profile")
    public String userProfile(Authentication authentication,
                           Map<String, Object> model) {
        List<TaskList> passedTasksList = taskListService.findPassedTaskListByUserId(
                userService.findUserByName(authentication.getName()).getId()
        );
        List<TaskList> notPassedTasksList = taskListService.findWaitingTaskListByUserId(
                userService.findUserByName(authentication.getName()).getId()
        );

        model.put("user", userService.findUserByName(authentication.getName()));
        model.put("passedTasks", passedTasksList);
        model.put("notPassedTasks", notPassedTasksList);
        model.put("countOfTasks", notPassedTasksList.size());
        return "profile";
    }

    @GetMapping("/my-tasks")
    public String allUserTasks(Authentication authentication,
                            Map<String, Object> model) {
        List<TaskList> passedTasksList = taskListService.findPassedTaskListByUserId(
                userService.findUserByName(authentication.getName()).getId()
        );
        List<TaskList> notPassedTasksList = taskListService.findWaitingTaskListByUserId(
                userService.findUserByName(authentication.getName()).getId()
        );

        model.put("passedTasks", passedTasksList);
        model.put("notPassedTasks", notPassedTasksList);
        return "profile-tasks";
    }

    @GetMapping("/my-task")
    public String userTask(@RequestParam Long taskId,
                            Map<String, Object> model) {
        model.put("task", taskService.findById(taskId));
        return "profile-task";
    }


    @PostMapping("/edit-profile")
    public String editUserProfile(Authentication authentication,
                                  @RequestParam String password,
                                  @RequestParam String firstName,
                                  @RequestParam String lastName,
                                  @RequestParam String middleName,
                                  @RequestParam String email) {
        // TODO check input values
        userService.updateUser(
                userService.findUserByName(authentication.getName()),
                password,
                firstName,
                lastName,
                middleName,
                email
        );
        return "redirect:/profile";
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
            return "profile-task";
        }

        return "redirect:/profile-tasks";
    }

}
