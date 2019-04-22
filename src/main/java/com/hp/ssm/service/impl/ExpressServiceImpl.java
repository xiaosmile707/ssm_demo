package com.hp.ssm.service.impl;

import com.hp.ssm.dao.ExpressDao;
import com.hp.ssm.model.Express;
import com.hp.ssm.service.ExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpressServiceImpl implements ExpressService {
    @Autowired
    private ExpressDao expressDao;

    @Override
    public void addExpress(Express express) {
        expressDao.addExpress(express);
    }
}
