package com.hp.ssm.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class User {
    private int id;
    private String email;
    private String password;
    private String name;
    private int sex;
    private String address;
    private String phone;
    private String idNumber;
    private BigDecimal money;
    private String twoStepsSecret;
    private Boolean isActive;
    private Boolean rnauth;
}
