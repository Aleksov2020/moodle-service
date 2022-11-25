package com.moodle.server.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "task_list")
public class TaskList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Task task;
    @OneToOne
    private UserEntity userEntity;
    private String mark;
    @ColumnDefault("false")
    private Boolean isPassed;
    private LocalDateTime deadLine;
    private LocalDateTime wasAnswered;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Attempt> attempts = new ArrayList<>();

    public TaskList(Task task, UserEntity userEntity, Boolean isPassed) {
        this.task = task;
        this.userEntity = userEntity;
        this.isPassed = isPassed;
    }
}
