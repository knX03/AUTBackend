package com.kn.initialmusic.mapper;

import com.kn.initialmusic.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {



    /**
     * 查询id是否重复
     *
     * @param id
     * @return
     */
    String isRepeatedByID(String id);

    /**
     * 检查用户名是否存在
     *
     * @param user_Name
     * @return
     */
    String checkName(String user_Name);


    /**
     * 登录验证
     *
     * @param user_Email
     * @return
     */
    String selectPasswordByEmail(String user_Email);

    /**
     * 获取username
     *
     * @param user_Email
     * @return
     */
    String selectUserNameByEmail(String user_Email);

    /**
     * 获取user_ID
     * @param user_ID
     * @return
     */
    String selectUserIDByEmail(String user_ID);

    /**
     * 注册用户
     *
     * @param user
     */
    void registerUser(User user);

    /**
     * 忘记密码
     *
     * @param user_Email
     * @param user_Password
     */
    void changePassword(@Param("user_Email") String user_Email, @Param("user_Password") String user_Password);

    /**
     * 查询用户填写的邮箱是否存在
     *
     * @param user_Email
     * @return
     */
    String selectUserEmailByUserEmail(String user_Email);

    /**
     * 根据用户ID查询详情
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
    int changeUserInfo(@Param("user") User user, @Param("user_ID") String user_ID);
}
