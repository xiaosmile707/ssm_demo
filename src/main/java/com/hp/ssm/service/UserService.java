package com.hp.ssm.service;

import com.hp.ssm.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);

    long getExceptionUserByEmail(String email);

    void setUserActive(int id);

    boolean checkAccount(User user);

    User getUserByEmail(String email);

    User getUserById(int id);

    String getSecretByEmail(String email);

    void updateSecretByEmail(String email, String secret);

    void setTwoStepsActive(String email);

    void setTwoStepsUnActive(String email);

    void updateUser(User user);

    boolean checkTwoStepsActiveByEmail(String email);

    void userRealNameAuth(int userId, String idNumber);

    void addUserPic(int userId, String userPic);

    String getUserPic(Integer userId);

    void setUserVerified(String email);

    List<User> getUnRNAuthUserListByUserType(Integer userType);

    List<User> getAllUsersByUserType(Integer userType);

    void resetPwd(String email, String newPwd);

    void updateUserSelective(User user);
}
