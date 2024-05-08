package com.kn.initialmusic.controller;

import com.kn.initialmusic.pojo.Result;
import com.kn.initialmusic.pojo.User;
import com.kn.initialmusic.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@RestController
@CrossOrigin
@RequestMapping("/user")
public class userController {

    //头像绝对路径
    private final static String SAVE_PATH_USERAVATAR =
            "D:\\Workspeace\\vue3\\vue3\\src\\photos\\userAvatar\\";


    //头像项目路径
    private final static String FILE_SAVE_PREFIX_userAvatar = "static/photos/userAvatar/";

    //头像路径
    private static String userAvatar_PATH;

    @Autowired
    private UserService userService;
    @Autowired
    private EmailSendService emailSendService;


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

    /*登录接口*/
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
            String user_ID = userService.selectIDByEmail(email);
            result.setCode(200);
            result.setData(user_ID);
            result.setMsg("登录成功！");
        } else {
            result.setCode(400);
            result.setData("error");
            result.setMsg("账号或密码错误，请重试！");
        }

        return result;
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

    /*注册接口*/
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


        /*获取生成的验证码*/
        String checkCode = (String) request.getSession().getAttribute("code");
        //比对验证码
        if (!checkCode.equals(code)) {
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
        boolean session = request.getSession().isNew();
        String checkCode = (String) request.getSession().getAttribute("code");
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
        /*随机生成4位验证码*/
        Random random = new Random();
        String user_Email = map.get("user_Email");
        String code = "";
        for (int i = 0; i < 4; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            if ("char".equalsIgnoreCase(charOrNum)) {
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                code += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                code += String.valueOf(random.nextInt(10));
            }
        }
        /*存储生成验证码*/
        HttpSession session = request.getSession();
        session.setAttribute("code", code);
        //向指定邮箱发送验证码
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

    /*todo 修改用户资料(其他表使用user_ID存储，试着改变)*/
    @PostMapping("/changeUserInfo")
    public Result changeUserInfo(@RequestBody User user) {
        Result result = new Result();
        String user_ID = user.getUser_ID();//user_ID
        if (userAvatar_PATH != null) {
            user.setUser_Avatar(userAvatar_PATH);
        }
        Boolean flag = userService.changeUserInfo(user, user_ID);
        if (flag) {
            result.setCode(200);
            result.setData(user_ID);
            result.setMsg("修改成功！");
        } else {
            result.setCode(500);
            result.setMsg("内部服务器异常！");
        }
        return result;
    }


    /*上传头像(todo 得回到idea刷新才会出现头像)*/
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
}
