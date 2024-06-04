package com.kn.initialmusic.pojo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {
    private String message_id;
    private String poster_id;
    private String poster_name;
    private String poster_avatar;
    private String post_time;
    private String message_content;
}
