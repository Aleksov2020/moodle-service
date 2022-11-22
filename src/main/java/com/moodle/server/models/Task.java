package com.moodle.server.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taskId;

    private String name;
    private String description;
    private String input;
    private String output;

    public Task(String name, String description, String input, String output) {
        this.name = name;
        this.description = description;
        this.input = input;
        this.output = output;
    }
}
