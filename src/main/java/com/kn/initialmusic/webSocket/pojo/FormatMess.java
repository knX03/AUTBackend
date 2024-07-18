package com.kn.initialmusic.webSocket.pojo;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FormatMess {
    private String poster_ID;
    private String poster_Name;
    private String poster_Avatar;
    private String messageText;
    private String messageType;
    private String post_Time;
}
