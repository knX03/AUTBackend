package com.kn.initialmusic.webSocket.pojo;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class mess {
    private String recipient_ID;
    private String recipient_Name;
    private String recipient_Avatar;
    private String poster_ID;
    private String poster_Name;
    private String poster_Avatar;
    private String messageText;
    private String post_Time;
}
