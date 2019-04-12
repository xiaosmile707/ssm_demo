package com.hp.ssm.service;


import com.hp.ssm.model.Mission;
import com.hp.ssm.model.PageCollection;

import java.util.List;

public interface MissionService {
    PageCollection<Mission> getAllMission(int pageSize, int pageNo);
    Mission getMissionById(int id);
    List<Mission> getMissionsById(int id);
    List<Mission> getMissionsByUserIdAndStatus(int userId,String status);
    List<Mission> getMissionsBySubmitId(int submitId);
    void addMission(Mission mission);
    void validateMission(int missionId,String status);

    void addMissionPic(Integer missionId, String pic);

    String getMissionPic(Integer missionId);
}
