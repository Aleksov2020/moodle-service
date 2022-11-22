package com.moodle.server.services;

import com.moodle.server.models.Group;

import java.util.List;

public interface GroupService {
    Group saveGroup(Group group);

    List<Group> findAll();

    Group findById(Long id);
}
