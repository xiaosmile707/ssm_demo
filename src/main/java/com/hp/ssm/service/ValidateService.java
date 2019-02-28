package com.hp.ssm.service;

import com.hp.ssm.model.ValidateTable;

import java.util.Date;

public interface ValidateService {
    void addValidation(ValidateTable validateTable);

    ValidateTable getTableByValidateCode(String validate);

    void delValidationById(int id);
}
