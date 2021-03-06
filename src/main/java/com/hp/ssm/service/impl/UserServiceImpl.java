package com.hp.ssm.service.impl;

import com.hp.ssm.dao.UserDao;
import com.hp.ssm.model.User;
import com.hp.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public void addUser(User user) {
         userDao.addUser(user);
    }

    @Override
    public long getExceptionUserByEmail(String email) {
        return userDao.getExceptionUserByEmail(email);
    }

    @Override
    public void setUserActive(int id) {
        userDao.setUserActive(id);
    }

    @Override
    public boolean checkAccount(User user) {
        String password=userDao.getPasswordByEmail(user);
        return user.getPassword().equals(password);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public String getSecretByEmail(String email) {
        return userDao.getSecretByEmail(email);
    }

    @Override
    public void updateSecretByEmail(String email, String secret) {
        userDao.updateSecretByEmail(email,secret);
    }

    @Override
    public void setTwoStepsActive(String email) {
        userDao.setTwoStepsActive(email);
    }

    @Override
    public void setTwoStepsUnActive(String email) {
        userDao.setTwoStepsUnActive(email);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public boolean checkTwoStepsActiveByEmail(String email) {
        Boolean check=userDao.checkTwoStepsActiveByEmail(email);
        return (check == null)?false:check;
    }

    @Override
    public void userRealNameAuth(int userId, String idNumber) {
        userDao.userRealNameAuth(userId,idNumber);
    }

    @Override
    public void addUserPic(int userId, String userPic) {
        userDao.addUserPic(userId,userPic);
    }

    @Override
    public String getUserPic(Integer userId) {
        return userDao.getUserPic(userId);
    }

    @Override
    public void setUserVerified(String email) {
        userDao.updateVerifiedById(2,email);
    }

    @Override
    public List<User> getUnRNAuthUserListByUserType(Integer userType) {
        return userDao.getByRnauthAndType(1,userType);
    }

    @Override
    public List<User> getAllUsersByUserType(Integer userType) {
        return userDao.getUserListByType(userType);
    }

    @Override
    public void resetPwd(String email, String newPwd) {
        userDao.resetPwd(email,newPwd);
    }

    @Override
    public void updateUserSelective(User user) {
        userDao.updateById(user);
    }
}
