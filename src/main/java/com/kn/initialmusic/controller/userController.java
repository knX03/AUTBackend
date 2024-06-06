package com.kn.initialmusic.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.kn.initialmusic.pojo.Result;
import com.kn.initialmusic.pojo.User;
import com.kn.initialmusic.pojo.UserDTO;
import com.kn.initialmusic.pojo.userFans;
import com.kn.initialmusic.service.*;
import com.kn.initialmusic.util.UserHolder;
import com.kn.initialmusic.util.randomUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.kn.initialmusic.util.RedisConstants.*;


@RestController
@CrossOrigin
@RequestMapping("/user")
public class userController {

    //头像绝对路径
    private final static String SAVE_PATH_USERAVATAR =
            "D:\\Workspeace\\vue3\\src\\photos\\userAvatar\\";


    //头像项目路径
    private final static String FILE_SAVE_PREFIX_userAvatar = "static/photos/userAvatar/";

    //头像路径
    private static String userAvatar_PATH;

    @Autowired
    private UserService userService;
    @Autowired
    private EmailSendService emailSendService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /*用户名是否存在*/
    @GetMapping("/checkName")
    public Result checkName(@RequestParam String user_Name) {
        Result result = new Result();
        Boolean flag = userService.checkName(user_Name);
        if (flag) {
            result.setCode(302);
            result.setMsg("用户名重复！");
            return result;
        } else {
            result.setCode(200);
            result.setMsg("success");
            return result;
        }
    }

    /*密码登录接口*/
    @PostMapping("/userLogin")
    public Result loginUser(HttpServletResponse response, @RequestBody Map<String, Object> map) {
        Result result = new Result();
        String email = (String) map.get("user_Email");
        String password = (String) map.get("user_Password");
        Boolean remFlag = (Boolean) map.get("remFlag");
        Boolean flag = userService.loginVerify(email, password);
        if (flag) {
            if (remFlag) {
                Cookie cUserEmail = new Cookie("user_Email", email);
                Cookie cPassword = new Cookie("user_Password", password);
                //设置Cookie存活时间
                cUserEmail.setMaxAge(60 * 60 * 24 * 7);
                cPassword.setMaxAge(60 * 60 * 24 * 7);
                //发送Cookie
                response.addCookie(cUserEmail);
                response.addCookie(cPassword);
            }
            User user = userService.selectDetailByEmail(email);
            UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
            //生成token
            String token = UUID.randomUUID().toString(true);
            //将user信息存到redis
            Map<String, Object> userMap = BeanUtil.beanToMap(userDTO);
            String tokenKey = LOGIN_USER_KEY + token;
            stringRedisTemplate.opsForHash().putAll(tokenKey,
                    userMap);
            stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.DAYS);
            result.setCode(200);
            result.setData(token);
            result.setMsg("登录成功！");
        } else {
            result.setCode(400);
            result.setData("error");
            result.setMsg("账号或密码错误，请重试！");
        }
        return result;
    }


    /*验证码登录接口*/
    @PostMapping("/userLoginCode")
    public Result userLoginCode(@RequestBody Map<String, Object> map) {
        return userService.userLoginCode(map);
    }

    /*获取cookie接口*/
    @GetMapping("/cookie")
    public Result getCookie(HttpServletRequest request,
                            @CookieValue(name = "user_Email", defaultValue = "") String user_Email,
                            @CookieValue(name = "user_Password", defaultValue = "") String user_Password
    ) {
        Result result = new Result();
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_Email", user_Email);
        map.put("user_Password", user_Password);
        result.setData(map);
        return result;
    }

    /*todo 注册接口(前端传递user对象)*/
    @PostMapping("/userReg")
    public Result registerUser(HttpServletRequest request, @RequestBody Map<String, Object> map) throws MessagingException {
        Result result = new Result();
        String user_Name = (String) map.get("user_Name");
        String user_Sex = (String) map.get("user_Sex");
        Integer user_Age = (Integer) map.get("user_Age");
        String user_Email = (String) map.get("user_Email");
        String user_Password = (String) map.get("user_Password");
        String code = (String) map.get("code");
        String user_Avatar = "../photos/logo/avatarWhite.png";
        /*从redis获取生成的验证码*/
        String checkCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + user_Email);
        //比对验证码
        if (checkCode != null && !checkCode.equals(code)) {
            result.setCode(300);
            result.setMsg("CODE_ERROR");
            return result;
        }
        User user = new User(user_Name, user_Email, user_Password, user_Sex, user_Age);
        user.setUser_Avatar(user_Avatar);
        //注册账户
        Boolean RegFlag = userService.registerUser(user);
        if (RegFlag) {
            result.setCode(200);
            result.setFlag(RegFlag);
            result.setMsg("success");
            result.setData(user.getUser_Name());
            result.setData(user.getUser_Email());
            result.setData(user.getUser_ID());
        } else {
            result.setCode(402);
            result.setFlag(RegFlag);
            result.setMsg("error");
            result.setData("注册失败,请重试!");
        }
        return result;
    }

    /*忘记密码*/
    @PostMapping("/userForget")
    public Result forgetPassword(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        Result result = new Result();
        String user_Email = (String) map.get("user_Email");
        String user_Password = (String) map.get("user_Password");
        String code = (String) map.get("code");


        /*获取生成的验证码*/
        String checkCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + user_Email);
        //比对验证码
        if (!checkCode.equals(code)) {
            result.setCode(300);
            result.setMsg("CODE_ERROR");
            return result;
        }

        Boolean forgetFlag = userService.changePassword(user_Email, user_Password);
        if (forgetFlag) {
            result.setCode(200);
            result.setMsg("修改成功！");
            result.setData("success");
            return result;
        } else {
            result.setCode(500);
            result.setMsg("内部服务器异常！");
            result.setData("error");
            return result;
        }


    }

    /*查询用户是否已经登录的接口*/


    /*生成随机验证码并发送至指定邮箱接口*/
    @PostMapping("/generateCode")
    public Result generateCode(HttpServletRequest request, @RequestBody Map<String, String> map) throws MessagingException {
        Result result = new Result();
        String user_Email = map.get("user_Email");
        /*随机生成4位验证码*/
        randomUtil randomUtil = new randomUtil();
        String code = randomUtil.fourCode();
        /*存储生成验证码*/
        stringRedisTemplate.opsForValue().set(
                LOGIN_CODE_KEY + user_Email,
                code, LOGIN_CODE_TTL, TimeUnit.MINUTES);

        //向指定邮箱发送验证码
        request.getSession().setAttribute("code", code);
        Boolean sendFlag = emailSendService.sendCode(user_Email, code);
        if (sendFlag) {
            result.setCode(200);
            return result;
        } else {
            result.setCode(400);
            result.setMsg("发送验证码失败！");
            return result;
        }

    }

    /*判断邮箱是否存在*/
    @PostMapping("/ifExist")
    public Result ifExistEmail(@RequestBody Map<String, String> map) {
        Result result = new Result();
        String user_email = map.get("user_Email");
        /*先判断用户邮箱是否存在*/
        Boolean existFlag = userService.ifExistEmail(user_email);

        if (!existFlag) {
            result.setCode(301);
            result.setMsg("Email is absent");
            return result;
        } else {
            result.setCode(302);
            result.setMsg("Email is exist");
            return result;
        }
    }

    /*根据用户邮箱查询用户详情*/
    @GetMapping("/DetailByEmail")
    public Result selectDetailByEmail(@RequestParam("user_Email") String user_Email) {
        Result result = new Result();
        User user = userService.selectDetailByEmail(user_Email);
        if (user != null) {
            result.setCode(200);
            result.setData(user);
        } else {
            result.setCode(500);
            result.setMsg("内部服务器异常！");
        }
        return result;
    }

    /* 根据用户ID查询用户详情*/
    @GetMapping("/DetailByID")
    public Result selectDetailByID(@RequestParam String user_ID) {
        Result result = new Result();
        User user = userService.selectDetailByID(user_ID);
        if (user != null) {
            result.setCode(200);
            result.setData(user);
        } else {
            result.setCode(500);
            result.setMsg("内部服务器异常！");
        }
        return result;
    }

    @GetMapping("/userDetail")
    public Result userDetail() {
        Result result = new Result();
        User user = UserHolder.getUser();
        User lUser = userService.selectDetailByID(user.getUser_ID());
        if (lUser != null) {
            result.setCode(200);
            result.setData(lUser);
        } else {
            result.setCode(401);
            result.setMsg("用户未登录！");
        }
        UserHolder.removeUser();
        return result;
    }


    /*修改用户资料*/
    @PostMapping("/changeUserInfo")
    public Result changeUserInfo(@RequestBody User user) {
        Result result = new Result();
        User userH = UserHolder.getUser();
        String user_ID = userH.getUser_ID();
        Boolean flag = userService.changeUserInfo(user, user_ID);
        if (flag) {
            result.setCode(200);
            result.setData(flag);
            result.setMsg("修改成功！");
        } else {
            result.setCode(500);
            result.setMsg("内部服务器异常！");
        }
        UserHolder.removeUser();
        return result;
    }


    /*上传头像*/
    @PostMapping("/uploadAvatar")
    public Result uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException {
        Result result = new Result();
        //获取文件名字
        String filename = file.getOriginalFilename();
        String filePathmu = new String(SAVE_PATH_USERAVATAR);
        //以上述路径创建File对象
        String path = filePathmu + filename;
        File filePath = new File(path);
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
        outputStream.write(file.getBytes());
        outputStream.flush();
        outputStream.close();
        /*头像路径*/
        userAvatar_PATH = "src/photos/userAvatar/" + filename;
        result.setCode(200);
        result.setData("src/photos/userAvatar/" + filename);
        result.setMsg("上传成功！");
        return result;
    }

    //退出登录
    @GetMapping("/logOff")
    public Result logOff(@RequestParam("user_token") String user_token) {
        Boolean flag = userService.logOff(user_token);
        Result result = new Result();
        result.setCode(200);
        result.setData(flag);
        result.setMsg("退出登录");
        return result;
    }


    @PostMapping("/test")
    public String test() {
        System.out.println("successTest");
        return "successTest";
    }

    @GetMapping("/getSumFollowAndFan")
    public Result getSumFollowAndFan(@RequestParam("user_ID") String userID) {
        Result result = new Result();
        Map<String, Integer> sumFollowAndFan;
        if (userID.isEmpty()) {
            User user = UserHolder.getUser();
            String user_ID = user.getUser_ID();
            sumFollowAndFan = userService.getSumFollowAndFan(user_ID);
            UserHolder.removeUser();
        } else {
            sumFollowAndFan = userService.getSumFollowAndFan(userID);
        }
        if (sumFollowAndFan != null) {
            result.setCode(200);
            result.setData(sumFollowAndFan);
        } else {
            result.setCode(301);
        }
        return result;
    }

    @GetMapping("/getUserFans")
    public Result getUserFans(@RequestParam("user_ID") String userID) {
        Result result = new Result();
        List<userFans> userFans;
        if (userID.isEmpty()) {
            User user = UserHolder.getUser();
            String user_ID = user.getUser_ID();
            userFans = userService.getUserFans(user_ID);
            UserHolder.removeUser();
        } else {
            userFans = userService.getUserFans(userID);
        }
        if (!userFans.isEmpty()) {
            result.setCode(200);
            result.setData(userFans);
        } else {
            result.setCode(301);
        }
        return result;
    }

    @GetMapping("/userUnfollowFan")
    public Result userUnfollowFan(@RequestParam("fan_id") String fan_id) {
        Result result = new Result();
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        boolean flag = userService.unFollowUser(user_ID, fan_id);
        if (flag) {
            result.setCode(200);
        }
        UserHolder.removeUser();
        return result;
    }

    @GetMapping("/getUserFollow")
    public Result getUserFollow(@RequestParam("user_ID") String userID) {
        Result result = new Result();
        List<User> userList;
        if (userID.isEmpty()) {
            User user = UserHolder.getUser();
            String user_ID = user.getUser_ID();
            userList = userService.getUserFollow(user_ID);
            UserHolder.removeUser();
        } else {
            userList = userService.getUserFollow(userID);
        }
        if (!userList.isEmpty()) {
            result.setCode(200);
            result.setData(userList);
        } else {
            result.setCode(301);
        }
        return result;
    }

    @GetMapping("/ifFollowUser")
    public Result ifFollowUser(@RequestParam("ID") String ID) {
        Result result = new Result();
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        Boolean flag = userService.ifFollowUser(user_ID, ID);
        result.setData(flag);
        UserHolder.removeUser();
        return result;
    }

    @GetMapping("/followUser")
    public Result followUser(@RequestParam("ID") String ID) {
        Result result = new Result();
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        Boolean flag = userService.followUser(user_ID, ID);
        if (flag) {
            result.setCode(200);
        }
        UserHolder.removeUser();
        return result;
    }

    @GetMapping("/ifMy")
    public Result ifMy(@RequestParam("ID") String user_ID) {
        Result result = new Result();
        User user = UserHolder.getUser();
        String userID = user.getUser_ID();
        boolean flag = user_ID.equals(userID);
        result.setCode(200);
        result.setData(flag);
        UserHolder.removeUser();
        return result;
    }
}


