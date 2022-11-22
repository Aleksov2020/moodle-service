package com.moodle.server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class MainController {
    @GetMapping("/")
    public String homePage(Map<String, Object> model) {
        return "home";
    }
}
