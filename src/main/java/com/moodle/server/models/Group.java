package com.moodle.server.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "grp")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;
    private String groupNum;
    private String groupDescription;

    public Group(String groupNum, String groupDescription) {
        this.groupNum = groupNum;
        this.groupDescription = groupDescription;
    }
}
