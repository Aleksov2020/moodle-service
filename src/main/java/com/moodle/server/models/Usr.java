package com.moodle.server.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "usr")
public class Usr {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long usr_id;
    private String username;
    private String firstName;
    private String lastName;
    private String middleName;
    private String groupId;

}
