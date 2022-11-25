package com.moodle.server.controllers;

import com.moodle.server.models.Task;
import com.moodle.server.services.TaskListService;
import com.moodle.server.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {



    @GetMapping("/")
    public String homePage(Map<String, Object> model) {
        return "home";
    }



}
