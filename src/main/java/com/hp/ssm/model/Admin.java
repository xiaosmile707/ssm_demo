package com.hp.ssm.model;

import lombok.Data;

@Data
public class Admin {
    private int id;
    private String account;
    private String password;
    private String twoStepsSecret;
}
