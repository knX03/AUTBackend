package com.kn.initialmusic.pojo;

import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SysMess {
    private String user_ID;
    private String mess_text_title;
    private String mess_text;
    private Timestamp mess_time;
}
