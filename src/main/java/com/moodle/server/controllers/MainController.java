package com.moodle.server.controllers;

import com.moodle.server.services.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class MainController {
    private final GroupService groupService;

    public MainController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/")
    public String homePage(HttpServletRequest httpServletRequest,
                           Map<String, Object> model) {
        if (httpServletRequest.isUserInRole("ADMIN")) {
            model.put("listGroups", groupService.findAll());
        }

        return "home";
    }
}
