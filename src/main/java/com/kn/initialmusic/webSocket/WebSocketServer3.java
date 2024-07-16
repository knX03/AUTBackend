/*
package com.kn.initialmusic.webSocket;


import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@ServerEndpoint(value = "/webSocket/{userId}")//encoders = {MyEncoder.class}
public class WebSocketServer3 {
    private final Logger logger = LoggerFactory.getLogger(WebSocketServer3.class);
    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象
    private static final CopyOnWriteArraySet<WebSocketServer3> webSockets = new CopyOnWriteArraySet<>();
    // 会话池,用来存在线连接数
    private static final Map<String, Session> sessionPool = new HashMap<String, Session>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId) {
//        HashMap<String, Session> map = new HashMap<>();
//        map.put(userId, session);
//        MessageHolder.saveSession(map);

        try {
            this.session = session;
            webSockets.add(this);
            sessionPool.put(userId, session);
            logger.info("WebSocket消息: 有新的连接，总数为:" + webSockets.size());
        } catch (Exception e) {
        }
    }

    //收到客户端消息后调用的方法
    @OnMessage
    public void onMessage(String message) {
        logger.info("WebSocket消息: 收到客户端消息:" + message);
    }

}
*/
