package com.moodle.server.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name="attempt")
public class Attempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private UserEntity user;
    @OneToOne
    private Task task;
    private LocalDateTime dateOfAttempt;
    @Column(length = 20000)
    private String sourceCode;
    @Column(length = 20000)
    private String output;
    private Boolean isCorrect;

    public Attempt(UserEntity user, Task task, LocalDateTime dateOfAttempt, String sourceCode, String output, Boolean isCorrect) {
        this.user = user;
        this.task = task;
        this.dateOfAttempt = dateOfAttempt;
        this.sourceCode = sourceCode;
        this.output = output;
        this.isCorrect = isCorrect;
    }
}
