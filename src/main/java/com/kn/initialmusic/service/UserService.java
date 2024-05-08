package com.kn.initialmusic.service;

import com.kn.initialmusic.pojo.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

    /*登录验证*/
    Boolean loginVerify(String user_email, String user_password);

    String selectNameByEmail(String User_Email);

    String selectIDByEmail(String User_Email);

    /**
     * 检查用户名是否存在
     *
     * @param user_Name
     * @return
     */
    Boolean checkName(String user_Name);


    /**
     * 注册账号
     *
     * @param user
     * @return
     */
    Boolean registerUser(User user);

    /**
     * 忘记密码
     *
     * @param user_email
     * @param user_password
     * @return
     */
    Boolean changePassword(String user_email, String user_password);

    /**
     * 查询用户填写的邮箱是否存在
     *
     * @param user_email
     * @return
     */
    Boolean ifExistEmail(String user_email);

    /**
     * 根据用户名称查询详情
     *
     * @param user_ID
     * @return
     */
    User selectDetailByID(String user_ID);

    /**
     * 根据用户邮箱查询详情
     *
     * @param user_Email
     * @return
     */
    User selectDetailByEmail(String user_Email);

    /**
     * 修改用户资料
     *
     * @param user
     * @return
     */
    Boolean changeUserInfo(User user, String user_ID);
}
