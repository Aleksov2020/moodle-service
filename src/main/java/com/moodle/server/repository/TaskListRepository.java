package com.moodle.server.repository;

import com.moodle.server.models.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    List<TaskList> findTaskListsByUserEntity_IdAndIsPassed(Long userId, Boolean isPassed);

    TaskList findByUserEntity_IdAndTask_TaskId(Long userId, Long taskId);
}
