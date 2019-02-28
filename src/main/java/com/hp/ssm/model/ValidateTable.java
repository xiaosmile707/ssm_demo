package com.hp.ssm.model;

import lombok.Data;

import java.util.Date;

@Data
public class ValidateTable {
    private int id;
    private int userId;
    private String validateCode;
    private Date startTime;
    private Date endTime;
}
