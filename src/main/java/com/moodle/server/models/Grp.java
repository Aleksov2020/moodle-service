package com.moodle.server.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "grp")
public class Grp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long groupId;
    private String groupNum;
    private String groupDescription;
}
