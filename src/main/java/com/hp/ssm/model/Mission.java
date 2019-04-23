package com.hp.ssm.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class Mission {
    private int id;
    private int submitId;
    private String content;
    private String description;
    private Date startTime;
    private Date endTime;
    private String status;
    private String pic;
    private int type;
    private String expressUUID;
    private String location;
    private Integer step;
    private String destination;
    private Integer receiveId;
    private String receiveUserName;
}
