package com.kn.initialmusic.webSocket;

import cn.hutool.json.JSONUtil;
import com.kn.initialmusic.pojo.User;
import com.kn.initialmusic.util.UserHolder;
import com.kn.initialmusic.webSocket.pojo.Message;
import com.kn.initialmusic.webSocket.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MyWebSocketHandler implements WebSocketHandler {

    private static final Map<String, WebSocketSession> SESSIONS = new ConcurrentHashMap<>();

    @Autowired
    private MessageService messageService;
    /*
     * 提供一个spring context上下文(解决方案)
     */
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        MyWebSocketHandler.applicationContext = applicationContext;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        User user = UserHolder.getUser();
        String userId = user.getUser_ID();
        SESSIONS.put(userId, session);
        System.out.println(String.format("成功建立连接~ userId: %s", userId));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String msg = message.getPayload().toString();
        sendMessage(msg);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("连接出错");
        if (session.isOpen()) {
            session.close();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("连接已关闭,status:" + closeStatus);
        UserHolder.removeUser();
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 指定发消息
     *
     * @param message
     */
    public static void sendMessage(String message) {
        //将消息推送至指定用户

        //将消息Json转化成功bean对象
        Message msg = JSONUtil.toBean(message, Message.class);
        String recipientId = msg.getRecipient_ID();
        WebSocketSession webSocketSessionR = SESSIONS.get(recipientId);
//        WebSocketSession webSocketSessionP = SESSIONS.get(msg.getPoster_ID());
        if (webSocketSessionR == null || !webSocketSessionR.isOpen()) {
            saveMess(msg);
        } else {
            try {
                String formatMess = formatMess(message);
                System.out.println(formatMess);
                webSocketSessionR.sendMessage(new TextMessage(formatMess));
                saveMess(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static void saveMess(Message message) {
        MessageService messageService = applicationContext.getBean(MessageService.class);
        messageService.saveMessage(message);
    }

    private static String formatMess(String message) {
        MessageService messageService = applicationContext.getBean(MessageService.class);
        return messageService.formatMessage(message);
    }

}
