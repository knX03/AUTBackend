package com.kn.initialmusic.service;

import com.kn.initialmusic.pojo.Result;
import com.kn.initialmusic.pojo.User;
import com.kn.initialmusic.pojo.userFans;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
public interface UserService {

    /**
     * @param user_email    用户邮箱
     * @param user_password 用户密码
     * @return 是否存在此用户
     */

    Boolean loginVerify(String user_email, String user_password);

    /**
     * @param map 登录表单，包含Email和验证码code
     * @return 是否能够登录
     */
    Result userLoginCode(Map<String, Object> map);

    /**
     * @param User_Email 用户邮箱
     * @return 用户名
     */

    String selectNameByEmail(String User_Email);

    /**
     * @param User_Email 用户邮箱
     * @return 用户ID
     */
    String selectIDByEmail(String User_Email);

    /**
     * 检查用户名是否存在
     *
     * @param user_Name 用户名
     * @return 用户名是否存在
     */
    Boolean checkName(String user_Name);


    /**
     * 注册账号
     *
     * @param user 待注册的用户对象
     * @return 是否注册成功
     */
    Boolean registerUser(User user);

    /**
     * 忘记密码
     *
     * @param user_email    用户邮箱
     * @param user_password 用户密码
     * @return 是否修改成功
     */
    Boolean changePassword(String user_email, String user_password);

    /**
     * 查询用户填写的邮箱是否存在
     *
     * @param user_email 用户邮箱
     * @return 用户邮箱是否存在
     */
    Boolean ifExistEmail(String user_email);

    /**
     * 根据用户名称查询详情
     *
     * @param user_ID 用户ID
     * @return 相应的用户对象
     */
    User selectDetailByID(String user_ID);

    /**
     * 根据用户邮箱查询详情
     *
     * @param user_Email 用户邮箱
     * @return 对应的用户对象
     */
    User selectDetailByEmail(String user_Email);

    /**
     * 修改用户资料
     *
     * @param user 待修改的用户对象
     * @return 是否修改成功
     */
    Boolean changeUserInfo(User user, String user_ID);

    /**
     * 用户退出登录
     *
     * @param user_token 用户token
     * @return 是否成功退出
     */
    Boolean logOff(String user_token);

    /**
     * 获取用户的关注数与粉丝数
     *
     * @param user_ID 用户ID
     * @return 用户的关注数与粉丝数
     */
    Map<String, Integer> getSumFollowAndFan(String user_ID);

    /**
     * 获取用户的粉丝列表
     *
     * @param user_ID 用户ID
     * @return 粉丝列表
     */
    List<userFans> getUserFans(String user_ID);

    //暂时不用
    /**
     * 用户回关粉丝
     *
     * @param user_ID 用户ID
     * @param fan_id  粉丝ID
     * @return 是否互关成功
     */
    boolean userFollowFan(String user_ID, String fan_id);

    //暂时不用
    /**
     * 用户取关粉丝
     *
     * @param user_ID 用户ID
     * @param fan_id  粉丝ID
     * @return 是否取关成功
     */
    boolean userUnfollowFan(String user_ID, String fan_id);

    /**
     * 获取用户的关注列表
     *
     * @param user_ID 用户ID
     * @return 关注的列表
     */
    List<User> getUserFollow(String user_ID);

    /**
     * 用户是否关注另一个用户
     *
     * @param user_ID 用户ID
     * @param ID      另一个用户ID
     * @return 是否关注
     */
    Boolean ifFollowUser(String user_ID, String ID);

    /**
     * 用户关注另一个用户
     *
     * @param user_ID 用户ID
     * @param ID      另一个用户ID
     * @return 是否关注成功
     */
    Boolean followUser(String user_ID, String ID);

    /**
     * 用户取消关注另一个用户
     *
     * @param user_ID 用户ID
     * @param ID      另一个用户ID
     * @return 是否取消关注成功
     */
    Boolean unFollowUser(String user_ID, String ID);
}
