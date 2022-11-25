package com.moodle.server.services;

import com.moodle.server.models.Group;
import com.moodle.server.models.Task;
import com.moodle.server.models.TaskList;
import com.moodle.server.models.UserEntity;
import com.moodle.server.repository.TaskListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskListServiceImpl implements TaskListService{

    private final UserService userService;
    private final TaskListRepository taskListRepository;

    public TaskListServiceImpl(UserService userService,
                               TaskListRepository taskListRepository) {
        this.userService = userService;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public void sendToGroupTask(Task task, Group group) {
        List<UserEntity> userEntityList = userService.findByGroupId(group.getGroupId());
        for (UserEntity student : userEntityList){
            taskListRepository.save(
                    new TaskList(
                            task,
                            student,
                            false
                    )
            );
        }
    }

    @Override
    public List<TaskList> findPassedTaskListByUserId(Long userId) {
        return taskListRepository.findTaskListsByUserEntity_IdAndIsPassed(userId, true);
    }

    @Override
    public List<TaskList> findWaitingTaskListByUserId(Long userId) {
        return taskListRepository.findTaskListsByUserEntity_IdAndIsPassed(userId, false);
    }
}
