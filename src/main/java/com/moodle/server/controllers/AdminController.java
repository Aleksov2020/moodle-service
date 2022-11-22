package com.moodle.server.controllers;

import com.moodle.server.models.Group;
import com.moodle.server.models.UserEntity;
import com.moodle.server.services.GroupService;
import com.moodle.server.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
@Slf4j
@Controller
public class AdminController {
    @Autowired
    GroupService groupService;
    @Autowired
    UserService userService;

    @GetMapping("/admin-page")
    public String homePage(Map<String, Object> model) {
        model.put("listGroups", groupService.findAll());
        model.put("listUsers", userService.findAll());
        return "admin";
    }

    @PostMapping("/new-group")
    public String createNewGroup(@RequestParam String groupNumber, @RequestParam String shortDescription, Map<String, Object> model) {
        groupService.saveGroup(
                new Group(
                    groupNumber,
                    shortDescription
                )
        );
        model.put("addGroupSuccess", "Create group was success");
        return "admin";
    }

    @PostMapping("/new-user")
    public String createNewUser(@RequestParam String username, @RequestParam String password, @RequestParam Long groupId, Map<String, Object> model) {
        log.info(groupId.toString());
        userService.registerNewUser(
                null,
                null,
                null,
                groupService.findById(groupId),
                username,
                password
        );
        model.put("addUserSuccess", "Create user was success");
        model.put("username", username);
        model.put("password", password);
        model.put("group", groupId);
        return "admin";
    }
}
