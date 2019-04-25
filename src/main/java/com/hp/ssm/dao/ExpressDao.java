package com.hp.ssm.dao;

import java.util.List;

import com.hp.ssm.model.Express;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpressDao {
    void addExpress(@Param("express") Express express);

    int deleteByPrimaryKey(String uuid);

    int insert(Express record);

    int insertSelective(Express record);

    Express selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(Express record);

    int updateByPrimaryKey(Express record);

    List<Express> findByUserId(@Param("userId") Integer userId);


    List<Express> getAllExpress();
}
