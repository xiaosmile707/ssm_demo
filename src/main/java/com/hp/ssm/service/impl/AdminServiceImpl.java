package com.hp.ssm.service.impl;

import com.hp.ssm.dao.AdminDao;
import com.hp.ssm.model.Admin;
import com.hp.ssm.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Override
    public Admin findAdminByAccount(String account) {
        return adminDao.findByAccount(account);
    }

    @Override
    public boolean checkAdminIsExist(String account) {
        return adminDao.countByAccount(account) > 0;
    }

    @Override
    public void addAdmin(Admin admin) {
        adminDao.insert(admin);
    }

    @Override
    public void updateAdminSelective(Admin admin) {
        adminDao.updateByPrimaryKeySelective(admin);
    }

    @Override
    public List<Admin> getUnActiveAdmins() {
        return adminDao.getByActive(0);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminDao.getAllAdmins();
    }
}
