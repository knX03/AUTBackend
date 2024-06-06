package com.kn.initialmusic.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.kn.initialmusic.mapper.UserMapper;
import com.kn.initialmusic.pojo.Result;
import com.kn.initialmusic.pojo.User;
import com.kn.initialmusic.pojo.userFans;
import com.kn.initialmusic.service.GenerateIDService;
import com.kn.initialmusic.service.UserService;
import com.kn.initialmusic.util.UserHolder;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.kn.initialmusic.util.RedisConstants.*;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GenerateIDService generateIDService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public Boolean loginVerify(String user_email, String user_password) {
        String password = userMapper.selectPasswordByEmail(user_email);
        return user_password.equals(password);
    }

    @Override
    public Result userLoginCode(Map<String, Object> map) {
        Result result = new Result();
        String user_Email = (String) map.get("user_Email");
        String code = (String) map.get("code");
        /*从redis获取生成的验证码*/
        String checkCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + user_Email);
        //比对验证码
        if (checkCode != null && !checkCode.equals(code)) {
            result.setCode(400);
            result.setMsg("CODE_ERROR");
            return result;
        }

        User user = userMapper.selectDetailByEmail(user_Email);
        //生成token
        String token = UUID.randomUUID().toString(true);

        //将user信息存到redis
        Map<String, Object> userMap = BeanUtil.beanToMap(user);
        String tokenKey = LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey,
                userMap);
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.DAYS);

        //登录成功删除验证码数据
        stringRedisTemplate.delete(LOGIN_CODE_KEY + user_Email);
        result.setCode(200);
        result.setData(token);
        return result;
    }

    @Override
    public Boolean registerUser(User user) {
        String userID = generateIDService.generateUserID();
        user.setUser_ID(userID);
        int flag = userMapper.registerUser(user);
        return flag > 0;
    }

    @Override
    public Boolean changePassword(String user_email, String user_password) {
        int flag = userMapper.changePassword(user_email, user_password);
        return flag > 0;
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
    public Boolean logOff(String user_token) {
        String tokenKey = LOGIN_USER_KEY + user_token;
        //Boolean delete = stringRedisTemplate.delete(tokenKey); 直接删除整个hash数据
        Set<Object> keys = stringRedisTemplate.opsForHash().keys(tokenKey);
        int size = keys.size();
        if (!keys.isEmpty()) {
            stringRedisTemplate.opsForHash().delete(tokenKey, keys.toArray()); //可选择删除哈希数据里的对应键值
        } else {
            return false;
        }
        UserHolder.removeUser();
        return true;
    }

    @Override
    public Map<String, Integer> getSumFollowAndFan(String user_ID) {
        Map<String, Integer> sumFollowAndFan = userMapper.getSumFollowAndFan(user_ID);
        return sumFollowAndFan;
    }

    @Override
    public List<userFans> getUserFans(String user_ID) {
        List<userFans> userFans = userMapper.getUserFans(user_ID);
        return userFans;
    }


    @Override
    public List<User> getUserFollow(String user_ID) {
        List<User> userFollow = userMapper.getUserFollow(user_ID);
        return userFollow;
    }

    @Override
    public Boolean ifFollowUser(String user_ID, String ID) {
        String flag = userMapper.ifFollowUser(user_ID, ID);
        return flag != null;
    }

    @Override
    public Boolean followUser(String user_ID, String ID) {
        String ffFlag = userMapper.ifFanForUser(user_ID, ID);
        if (ffFlag == null) {
            int flag = userMapper.followUser(user_ID, ID, 0);
            return flag > 0;
        } else {
            int f = userMapper.userFollowFan(user_ID, ID);
            //添加user_ID成为ID的粉丝
            int aFlag = userMapper.followUser(user_ID, ID, 1);
            return aFlag > 0 || f > 0;
        }
    }

    @Override
    public Boolean unFollowUser(String user_ID, String ID) {
        String ffFlag = userMapper.ifFanForUser(user_ID, ID);
        if (ffFlag == null) {
            //删除user_ID关注ID的数据
            int flag = userMapper.unFollowUser(user_ID, ID);
            return flag > 0;
        } else {
            int in = userMapper.userUnfollowFan(user_ID, ID);
            int flag = userMapper.unFollowUser(user_ID, ID);
            return flag > 0 || in > 0;
        }
    }

    //暂时不用
    @Override
    public String selectNameByEmail(String User_Email) {
        String user_Name = userMapper.selectUserNameByEmail(User_Email);
        return user_Name;
    }

    //暂时不用
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
