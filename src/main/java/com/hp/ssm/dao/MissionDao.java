package com.hp.ssm.dao;

import com.hp.ssm.model.Mission;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MissionDao {
    List<Mission> getAllMission(int startIndex,int pageSize);
    int getMissionCount();
    Mission getMissionById(int id);
    List<Integer> getMissionIdsByUserId(int id);
    List<Mission> getMissionsByUserIdAndStatus(int userId,String status);
    List<Mission> getMissionsBySubmitId(int submitId);
    void addMission(Mission mission);
    void validateMission(int missionId,String status);
}
