package com.moodle.server.controllers;

import com.moodle.server.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage(Authentication authentication) {
        // TODO admin and home same templates -> join
        // Rewrite
        if (userService.findUserByName(authentication.getName()).getRoles().contains("ADMIN")) {
             return "admin";
        } else {
            return "home";
        }
    }
}
