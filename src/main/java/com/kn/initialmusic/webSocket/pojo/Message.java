package com.kn.initialmusic.webSocket.pojo;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {
    private String fUser_ID;
    private String sUser_ID;
    private String recipient_ID;
    private String poster_ID;
    private String post_time;
    private String message;
    private String messageType;
}
