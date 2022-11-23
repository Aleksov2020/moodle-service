package com.moodle.server.repository;

import com.moodle.server.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    void deleteUserEntitiesByGroup_GroupId(Long groupId);

    List<UserEntity> findUserEntitiesByGroup_GroupId(Long groupId);

    boolean existsByUsername(String username);

    @Query(value = "select nextval('users_id_seq')", nativeQuery = true)
    public Long getNextValMySequence();
}
