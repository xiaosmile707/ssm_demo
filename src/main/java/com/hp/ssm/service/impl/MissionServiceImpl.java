package com.hp.ssm.service.impl;

import com.hp.ssm.dao.CommentDao;
import com.hp.ssm.dao.MissionDao;
import com.hp.ssm.model.Comment;
import com.hp.ssm.model.Mission;
import com.hp.ssm.model.PageCollection;
import com.hp.ssm.service.MissionService;
import com.hp.ssm.utils.GetMouthByDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MissionServiceImpl implements MissionService {
    @Autowired
    private MissionDao missionDao;
    @Autowired
    private CommentDao commentDao;
    @Override
    public PageCollection<Mission> getAllMission(int pageSize, int pageNo) {
        PageCollection<Mission> coll = new PageCollection<>();
        //分页查询参数初始化
        int totalCount = missionDao.getMissionCount();
        int currentPageNo = (int) Math.ceil((double) totalCount / (double) pageSize);
        int endPageIndex = (currentPageNo - 1) * pageSize;
        int startIndex = (pageNo - 1) * pageSize;
        startIndex = (startIndex > endPageIndex) ? endPageIndex : startIndex;
        pageNo = (pageNo > currentPageNo) ? currentPageNo : pageNo;
        //查询任务集合
        List<Mission> missions = missionDao.getAllMission(startIndex,pageSize);
        for (Mission mission:missions
             ) {
            try {
                List<Comment> comments=commentDao.getCommentsByMissionId(mission.getId());
                List<Comment> commentList=new ArrayList<>(comments);
                mission.setComments(commentList);
                mission.setCommentCount(commentDao.getCommentCountByMissionId(mission.getId()));
                mission.setStartMouth(GetMouthByDate.getMouth(mission.getStartTime()));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("月份转换在MissionServiceImpl出错");
            }
        }
        coll.setPageNo(pageNo);
        coll.setTotalCount(totalCount);
        coll.setTotalPages(currentPageNo);
        coll.setItems(missions);
        return coll;
    }

    @Override
    public Mission getMissionById(int id) {
        return missionDao.getMissionById(id);
    }

    @Override
    public List<Mission> getMissionsById(int id) {
        List<Integer> ids=missionDao.getMissionIdsByUserId(id);
        List<Mission> missions=new ArrayList<>();
        for (Integer missionId:ids
             ) {
            Mission mission=missionDao.getMissionById(missionId);
            missions.add(mission);
        }
        return missions;
    }

    @Override
    public List<Mission> getMissionsByUserIdAndStatus(int userId, String status) {
        return missionDao.getMissionsByUserIdAndStatus(userId, status);
    }

    @Override
    public List<Mission> getMissionsBySubmitId(int submitId) {
        return missionDao.getMissionsBySubmitId(submitId);
    }

    @Override
    public void addMission(Mission mission) {
        String status=mission.getStatus();
        status=(status == null)?"未开始":status;
        mission.setStatus(status);
        missionDao.addMission(mission);
    }

    @Override
    public void validateMission(int missionId, String status) {
        missionDao.validateMission(missionId,status);
    }
}
