package com.moodle.server.services;

import com.moodle.server.models.Attempt;
import com.moodle.server.models.Task;
import com.moodle.server.models.UserEntity;
import com.moodle.server.repository.AttemptRepository;
import org.springframework.stereotype.Service;

@Service
public class AttemptServiceImpl implements AttemptService{
    private final AttemptRepository attemptRepository;

    public AttemptServiceImpl(AttemptRepository attemptRepository) {
        this.attemptRepository = attemptRepository;
    }

    @Override
    public Attempt createAttempt(UserEntity user, Task task, String output, String answerCode, Boolean isCorrect) {
        return attemptRepository.save(
                new Attempt(
                        user,
                        task,
                        java.time.LocalDateTime.now(),
                        answerCode,
                        output,
                        isCorrect
                )
        );
    }
}
