package com.moodle.server.controllers;

import com.moodle.server.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class AdviceController {
    @ExceptionHandler(NotFoundException.class)
    public void handleNotFoundException(NotFoundException message){
        log.info(message.getMessage());
    }
}
