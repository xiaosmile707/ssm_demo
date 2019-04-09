package com.hp.ssm.dao;

import com.hp.ssm.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    void addUser(@Param("user") User user);

    long getExceptionUserByEmail(String email);

    void setUserActive(int id);

    String getPasswordByEmail(User user);

    User getUserByEmail(String email);

    User getUserById(int id);

    String getSecretByEmail(String email);

    void updateUser(User user);

    void updateSecretByEmail(String email, String secret);

    void setTwoStepsActive(String email);

    void setTwoStepsUnActive(String email);

    Boolean checkTwoStepsActiveByEmail(String email);

    void userRealNameAuth(int userId, String idNumber);


    void addUserPic(@Param("userId") int userId, @Param("userPic") String userPic);

    String getUserPic(@Param("userId")Integer userId);

    void addMissionRate(@Param("missionId")int missionId);
}
