package com.moodle.server.controllers;

import com.moodle.server.models.TaskList;
import com.moodle.server.services.TaskListService;
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

    public StudentController(UserService userService,
                             TaskListService taskListService) {
        this.userService = userService;
        this.taskListService = taskListService;
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
}
