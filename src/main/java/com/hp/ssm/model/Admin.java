package com.hp.ssm.model;

import lombok.Data;

@Data
public class Admin {
    private Integer id;
    private String account;
    private String password;
    private Integer active;
}
