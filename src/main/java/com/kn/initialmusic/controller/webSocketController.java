package com.kn.initialmusic.controller;

import cn.hutool.json.JSONUtil;
import com.kn.initialmusic.webSocket.MyWebSocketHandler;
import com.kn.initialmusic.webSocket.pojo.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;

@RestController
@CrossOrigin
@RequestMapping("/msg")
public class webSocketController {

    //发送信息
    @PostMapping("/send")
    public HttpStatus sendMessage(@RequestBody Message msg) {
        String message = JSONUtil.toJsonStr(msg);
        MyWebSocketHandler webSocketHandler = new MyWebSocketHandler();
        webSocketHandler.sendMessage(message);
        return HttpStatus.OK;
    }
}

