package com.kn.initialmusic.controller;

import com.kn.initialmusic.pojo.Message;
import com.kn.initialmusic.pojo.Result;
import com.kn.initialmusic.pojo.User;
import com.kn.initialmusic.service.MessageService;
import com.kn.initialmusic.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/message")
public class messageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/getUserMessages")
    public Result getUserMessages() {
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        List<Message> messages = messageService.getMessages(user_ID);
        Result result = new Result();
        if (!messages.isEmpty()) {
            result.setCode(200);
            result.setData(messages);
        } else {
            result.setCode(301);
            result.setData(messages);
        }
        UserHolder.removeUser();
        return result;
    }

    //todo 发送消息
    /*@GetMapping("/postMess")
    public Result postMess(@RequestParam("user_ID") String user_ID) {
        Boolean flag = messageService.postMessage();
        Result result = new Result();
        if (flag) {
            result.setCode(200);
        }
        return result;
    }*/

    @GetMapping("/delMess")
    public Result delMess(@RequestParam("mess_ID") String mess_ID) {
        Result result = new Result();
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        Boolean flag = messageService.delMess(user_ID, mess_ID);
        if (flag) {
            result.setCode(200);
        } else {
            result.setCode(500);
        }
        UserHolder.removeUser();
        return result;
    }
}
