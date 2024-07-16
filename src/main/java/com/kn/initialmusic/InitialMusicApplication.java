package com.kn.initialmusic;

import com.kn.initialmusic.webSocket.MyWebSocketHandler;
import com.kn.initialmusic.webSocket.WebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class InitialMusicApplication {

    public static void main(String[] args) {
//        SpringApplication.run(InitialMusicApplication.class, args);
        //解决springboot和websocket之间使用@autowired注入为空问题
        ConfigurableApplicationContext applicationContext = SpringApplication.run(InitialMusicApplication.class, args);
        //这里将Spring Application注入到websocket类中定义的Application中。
//        WebSocketServer.setApplicationContext(applicationContext);
        MyWebSocketHandler.setApplicationContext(applicationContext);

    }

}
