package com.moodle.server.services;

import com.moodle.server.models.Group;
import com.moodle.server.models.Task;
import com.moodle.server.models.TaskList;

import java.util.List;

public interface TaskListService {
    void sendToGroupTask(Task task, Group groupId);

    List<TaskList> findPassedTaskListByUserId(Long userId);

    List<TaskList> findWaitingTaskListByUserId(Long userId);
}
