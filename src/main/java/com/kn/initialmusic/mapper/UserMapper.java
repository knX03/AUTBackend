package com.kn.initialmusic.mapper;

import com.kn.initialmusic.pojo.User;
import com.kn.initialmusic.pojo.userFans;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
     *
     * @param user_ID
     * @return
     */
    String selectUserIDByEmail(String user_ID);

    /**
     * 注册用户
     *
     * @param user
     */
    int registerUser(User user);

    /**
     * 忘记密码
     *
     * @param user_Email
     * @param user_Password
     */
    int changePassword(@Param("user_Email") String user_Email, @Param("user_Password") String user_Password);

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

    /**
     * 获取用户的粉丝列表
     *
     * @param user_ID 用户ID
     * @return 粉丝列表
     */
    List<userFans> getUserFans(String user_ID);

    /**
     * 用户回关粉丝
     *
     * @param user_ID 用户ID
     * @param fan_id  粉丝ID
     * @return 是否互关成功
     */
    int userFollowFan(@Param("user_ID") String user_ID, @Param("fan_id") String fan_id);

    /**
     * 用户取关粉丝
     *
     * @param user_ID 用户ID
     * @param fan_id  粉丝ID
     * @return 是否取关成功
     */
    int userUnfollowFan(@Param("user_ID") String user_ID, @Param("fan_id") String fan_id);

    /**
     * 获取用户的关注列表
     *
     * @param user_ID 用户ID
     * @return 关注的列表
     */
    List<User> getUserFollow(String user_ID);

    /**
     * 获取用户的关注数与粉丝数
     *
     * @param user_ID 用户ID
     * @return 用户的关注数与粉丝数
     */
    Map<String, Integer> getSumFollowAndFan(String user_ID);

    /**
     * 用户是否关注另一个用户
     *
     * @param user_ID 用户ID
     * @param ID      另一个用户ID
     * @return 是否关注
     */
    String ifFollowUser(@Param("user_ID") String user_ID, @Param("ID") String ID);

    /**
     * 用户ID是否是另一个用户user_ID的粉丝
     *
     * @param user_ID 用户ID
     * @param ID      另一个用户ID
     * @return 是否关注成功
     */
    String ifFanForUser(@Param("user_ID") String user_ID, @Param("ID") String ID);

    /**
     * 用户关注另一个用户
     *
     * @param user_ID 用户ID
     * @param ID      另一个用户ID
     * @return 是否关注成功
     */
    int followUser(@Param("user_ID") String user_ID, @Param("ID") String ID, @Param("feo") int feo);


    /**
     * 用户取消关注另一个用户
     *
     * @param user_ID 用户ID
     * @param ID      另一个用户ID
     * @return 是否取消关注成功
     */
    int unFollowUser(@Param("user_ID") String user_ID, @Param("ID") String ID);

    /**
     * 添加ID用户的粉丝为user_ID用户
     *
     * @param user_ID 用户ID:粉丝
     * @param ID      用户ID
     * @return 是否添加成功
     */
    int addUserFan(@Param("user_ID") String user_ID, @Param("ID") String ID,
                   @Param("fanFoll") int fanFoll);

}
