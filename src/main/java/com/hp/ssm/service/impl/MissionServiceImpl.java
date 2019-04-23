package com.hp.ssm.service.impl;

import com.hp.ssm.dao.MissionDao;
import com.hp.ssm.model.Mission;
import com.hp.ssm.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MissionServiceImpl implements MissionService {
    @Autowired
    private MissionDao missionDao;

    @Override
    public List<Mission> getMissionsByUUID(String expressUUID) {
        return missionDao.findByExpressUUID(expressUUID);
    }

    @Override
    public Mission getMissionById(Integer missionId) {
        return missionDao.selectByPrimaryKey(missionId);
    }

    @Override
    public List<Mission> getMissionsBySubmitUserId(Integer userId) {
        return missionDao.findBySubmitId(userId);
    }

    @Override
    public List<Mission> getMissionsByUserIdAndStatus(Integer userId, String status) {
        return missionDao.findBySubmitIdAndStatus(userId,status);
    }

    @Override
    public void addMission(Mission mission) {
        missionDao.insert(mission);
    }

    @Override
    public String getMissionPic(Integer missionId) {
        return missionDao.findPicById(missionId);
    }

    @Override
    public void addMissionPic(Integer missionId, String toString) {
        missionDao.updatePicById(toString,missionId);
    }
}
