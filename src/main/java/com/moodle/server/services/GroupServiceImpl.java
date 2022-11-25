package com.moodle.server.services;

import com.moodle.server.exceptions.NotFoundException;
import com.moodle.server.models.Group;
import com.moodle.server.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public Group findById(Long id) {
        return groupRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Group not found")
        );
    }

    @Override
    public void deleteGroupById(Long id) {
        if (groupRepository.findById(id).isPresent()) {
            groupRepository.deleteById(id);
        }
    }
}
