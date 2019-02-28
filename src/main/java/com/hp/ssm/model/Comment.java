package com.hp.ssm.model;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    private int id;
    private String content;
    private int submitId;
    private Date commentTime;
    private int missionId;
}
