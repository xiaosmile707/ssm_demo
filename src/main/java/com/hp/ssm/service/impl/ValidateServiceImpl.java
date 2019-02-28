package com.hp.ssm.service.impl;

import com.hp.ssm.dao.ValidateDao;
import com.hp.ssm.model.ValidateTable;
import com.hp.ssm.service.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidateServiceImpl implements ValidateService {
    @Autowired
    private ValidateDao validateDao;
    @Override
    public void addValidation(ValidateTable validateTable) {
        validateDao.addValidateTable(validateTable);
    }

    @Override
    public ValidateTable getTableByValidateCode(String validateCode) {
        return validateDao.getTableByValidateCode(validateCode);
    }

    @Override
    public void delValidationById(int id) {
        validateDao.delValidationById(id);
    }


}
