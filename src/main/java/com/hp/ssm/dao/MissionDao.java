package com.hp.ssm.dao;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.hp.ssm.model.Mission;

public interface MissionDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Mission record);

    int insertSelective(Mission record);

    Mission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Mission record);

    int updateByPrimaryKey(Mission record);

    List<Mission> findByExpressUUID(@Param("expressUUID")String expressUUID);

    List<Mission> findBySubmitId(@Param("submitId")Integer submitId);

	List<Mission> findBySubmitIdAndStatus(@Param("submitId")Integer submitId,@Param("status")String status);

	String findPicById(@Param("id")Integer id);

	int updatePicById(@Param("updatedPic")String updatedPic,@Param("id")Integer id);


}
