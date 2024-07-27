package com.kn.initialmusic.controller;

import com.kn.initialmusic.pojo.Message;
import com.kn.initialmusic.pojo.Result;
import com.kn.initialmusic.pojo.User;
import com.kn.initialmusic.util.UserHolder;
import com.kn.initialmusic.webSocket.service.MessageService;
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
        Result result;
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        result = messageService.getMessageUser(user_ID);
        UserHolder.removeUser();
        return result;
    }

    @GetMapping("/UserMess")
    public Result getUserMess(@RequestParam String userID) {
        Result result;
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        result = messageService.getMessage(user_ID, userID);
        UserHolder.removeUser();
        return result;
    }

}
