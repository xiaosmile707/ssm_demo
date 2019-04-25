package com.hp.ssm.service.impl;

import com.hp.ssm.dao.ExpressDao;
import com.hp.ssm.dao.MissionDao;
import com.hp.ssm.model.Express;
import com.hp.ssm.model.Mission;
import com.hp.ssm.service.ExpressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpressServiceImpl implements ExpressService {
    @Autowired
    private ExpressDao expressDao;
    @Autowired
    private MissionDao missionDao;

    @Override
    public void addExpress(Express express) {
        expressDao.addExpress(express);
    }

    @Override
    public List<Express> findAllExpressListByUserId(Integer userId) {
        return expressDao.findByUserId(userId);
    }

    @Override
    public Express getExpressDetail(String uuid) {
        return expressDao.selectByPrimaryKey(uuid);
    }

    @Override
    public List<Express> findAllExpressList() {
        List<Express> expressList = expressDao.getAllExpress();
        for (Express express : expressList) {
            List<Mission> missions = missionDao.findByExpressUUID(express.getUuid());
            if (missions != null && !missions.isEmpty()) {
                express.setMissions(missions);
            }
        }
        return expressList;
    }
}
