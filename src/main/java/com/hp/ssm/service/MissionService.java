package com.hp.ssm.service;


import com.hp.ssm.model.Mission;

import java.util.List;

public interface MissionService {
    List<Mission> getMissionsByUUID(String expressUUID);

    Mission getMissionById(Integer missionId);

    List<Mission> getMissionsBySubmitUserId(Integer userId);

    List<Mission> getMissionsByUserIdAndStatus(Integer userId, String status);

    void addMission(Mission mission);

    String getMissionPic(Integer missionId);

    void addMissionPic(Integer missionId, String toString);
}
