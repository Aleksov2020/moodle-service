package com.moodle.server.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "grp_list")
public class GrpList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long groupListId;
    private Long groupId;
    private Long usrId;
}
