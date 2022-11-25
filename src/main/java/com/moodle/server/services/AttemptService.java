package com.moodle.server.services;

import com.moodle.server.models.Attempt;
import com.moodle.server.models.Task;
import com.moodle.server.models.UserEntity;

public interface AttemptService {
    Attempt createAttempt(UserEntity user, Task task, String output, String answerCode, Boolean isCorrect);
}
