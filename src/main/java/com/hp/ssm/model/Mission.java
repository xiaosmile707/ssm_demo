package com.hp.ssm.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class Mission {
    private int id;
    private int submitId;
    private String name;
    private String submitUserName;
    private String content;
    private String description;
    private BigDecimal reward;
    private Date startTime;
    private Date endTime;
    private String status;
    private int rate;
    private String startMouth;
    private int commentCount;
    private List<Comment> comments;
}
