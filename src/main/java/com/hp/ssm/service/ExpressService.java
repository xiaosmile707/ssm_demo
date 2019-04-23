package com.hp.ssm.service;

import com.hp.ssm.model.Express;

import java.util.List;

public interface ExpressService {
    void addExpress(Express express);

    List<Express> findAllExpressListByUserId(Integer userId);

    Express getExpressDetail(String uuid);
}
