package com.moodle.server.controllers;

import com.moodle.server.models.Group;
import com.moodle.server.services.GroupService;
import com.moodle.server.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class AdminController {
    private final GroupService groupService;
    private final UserService userService;

    public AdminController(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    @PostMapping("/new-group")
    public String createNewGroup(@RequestParam String groupNumber, @RequestParam String shortDescription, Map<String, Object> model) {
        groupService.saveGroup(
                new Group(
                    groupNumber,
                    shortDescription
                )
        );
        model.put("addGroupSuccess", "Group was created successfully");
        return "redirect:/admin";
    }

    @PostMapping("/new-user")
    public String createNewUser(@RequestParam String username,
                                @RequestParam String password,
                                @RequestParam Long groupId,
                                Map<String, Object> model) {
        userService.registerNewUser(
                null,
                null,
                null,
                groupService.findById(groupId),
                username,
                password
        );
        model.put("addUserSuccess", "User was created successfully");
        model.put("username", username);
        model.put("password", password);
        model.put("group", groupId);
        return "redirect:/admin";
    }


}
