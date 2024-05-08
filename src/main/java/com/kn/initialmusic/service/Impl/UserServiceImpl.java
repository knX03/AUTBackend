package com.kn.initialmusic.service.Impl;

import com.kn.initialmusic.mapper.UserMapper;
import com.kn.initialmusic.pojo.User;
import com.kn.initialmusic.service.GenerateIDService;
import com.kn.initialmusic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GenerateIDService generateIDService;


    @Override
    public Boolean loginVerify(String user_email, String user_password) {
        String password = userMapper.selectPasswordByEmail(user_email);
        return user_password.equals(password);
    }

    @Override
    public Boolean registerUser(User user) {
        String userID = generateIDService.generateUserID();
        user.setUser_ID(userID);

        try {
            userMapper.registerUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public Boolean changePassword(String user_email, String user_password) {

        String email = userMapper.selectUserEmailByUserEmail(user_email);
        if (email != null) {
            userMapper.changePassword(user_email, user_password);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Boolean ifExistEmail(String user_email) {
        String email = userMapper.selectUserEmailByUserEmail(user_email);
        return email != null;

    }

    @Override
    public User selectDetailByID(String user_ID) {
        User user = userMapper.selectDetailByID(user_ID);
        return user;
    }

    @Override
    public User selectDetailByEmail(String user_Email) {
        User user = userMapper.selectDetailByEmail(user_Email);
        return user;
    }

    @Override
    public Boolean changeUserInfo(User user, String user_ID) {
        int changeNum = userMapper.changeUserInfo(user, user_ID);

        return changeNum > 0;
    }

    @Override
    public String selectNameByEmail(String User_Email) {
        String user_Name = userMapper.selectUserNameByEmail(User_Email);
        return user_Name;
    }

    @Override
    public String selectIDByEmail(String User_Email) {
        String user_ID = userMapper.selectUserIDByEmail(User_Email);
        return user_ID;
    }

    @Override
    public Boolean checkName(String user_Name) {
        String checkFlag = userMapper.checkName(user_Name);
        return checkFlag != null;
    }
}
