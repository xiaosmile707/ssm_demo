package com.hp.ssm.dao;

import com.hp.ssm.model.Express;
import org.apache.ibatis.annotations.Param;import org.springframework.stereotype.Repository;

@Repository
public interface ExpressDao {
    void addExpress(@Param("express")Express express);
}
