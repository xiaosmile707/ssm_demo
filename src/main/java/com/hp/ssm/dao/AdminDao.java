package com.hp.ssm.dao;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.hp.ssm.model.Admin;

public interface AdminDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    Admin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    Admin findByAccount(@Param("account")String account);

    Integer countByAccount(@Param("account")String account);

    List<Admin> getByActive(@Param("active")Integer active);

    List<Admin> getAllAdmins();
}
