package com.hp.ssm.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Express {
    private String uuid;
    private int userId;
    private String name;
    private int status;
    private String type;
    private String sendName;
    private String receiveName;
    private String sendCity;
    private String sendAddress;
    private String receiveCity;
    private String receiveAddress;
    private BigDecimal money;
    private Date sendTime;
    private Date receiveTime;
    private String sendPhone;
    private String receivePhone;
    private double weight;
}
