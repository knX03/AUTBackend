package com.kn.initialmusic.webSocket;


import cn.hutool.json.JSONUtil;
import com.kn.initialmusic.webSocket.pojo.Message;
import com.kn.initialmusic.webSocket.service.MessageService;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint(value = "/message/{id}")//encoders = {MyEncoder.class}
@Component
public class WebSocketServer {

    @Autowired
    private MessageService messageService;
    /*
     * 提供一个spring context上下文(解决方案)
     */
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketServer.applicationContext = applicationContext;
    }


    private static final Map<String, Session> onlineUsers = new ConcurrentHashMap<>();


    @OnOpen
    public void onOpen(Session session, @PathParam(value = "id") String userId) throws IOException {
//        User user = UserHolder.getUser();
//        String userId = user.getUser_ID();
        onlineUsers.put(userId, session);
        session.getBasicRemote().sendText("hello");
    }

    //收到客户端消息后调用的方法
    @OnMessage
    public void onMessage(String message) throws IOException {
        //将消息推送至指定用户

        //将消息Json转化成功bean对象
        Message bean = JSONUtil.toBean(message, Message.class);
        String toUserID = bean.getRecipient_ID();
        String beanMessage = bean.getMessage();
        //获取消息接收方
        Session session = onlineUsers.get(toUserID);
        if (session == null) {
            saveMess(bean);
        } else {
            boolean open = session.isOpen();
            //推送消息
            try {
                session.getBasicRemote().sendText(beanMessage);
            } catch (Exception ex) {
                System.out.println(ex);
                saveMess(bean);
            }
        }
    }

    private void saveMess(Message message) {
        MessageService messageService = applicationContext.getBean(MessageService.class);
        messageService.saveMessage(message);
        System.out.println(message);
    }

    @OnClose
    public void onClose() {
//        UserHolder.removeUser();
        System.out.println("close");
    }
}
