package com.hp.ssm.service;

import com.hp.ssm.model.Admin;

import java.util.List;

public interface AdminService {
    Admin findAdminByAccount(String account);

    boolean checkAdminIsExist(String account);

    void addAdmin(Admin admin);

    void updateAdminSelective(Admin admin);

    List<Admin> getUnActiveAdmins();

    List<Admin> getAllAdmins();
}
