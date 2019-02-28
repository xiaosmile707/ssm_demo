package com.hp.ssm.service;

import com.hp.ssm.model.ValidateTable;

public interface ValidateService {
    void addValidation(ValidateTable validateTable);

    ValidateTable getTableByValidateCode(String validate);

    void delValidationById(int id);
}
