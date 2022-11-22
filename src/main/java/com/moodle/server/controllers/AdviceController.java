package com.moodle.server.controllers;

import com.moodle.server.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceController {
    @ExceptionHandler(NotFoundException.class)
    public void handleNotFoundException(NotFoundException message){
        System.out.print("404");
    }
}
