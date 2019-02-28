package com.hp.ssm.dao;


import com.hp.ssm.model.ValidateTable;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidateDao {
    void addValidateTable(ValidateTable table);

    ValidateTable getTableByValidateCode(String validateCode);

    void delValidationById(int id);
}
