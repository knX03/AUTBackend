package com.kn.initialmusic.pojo;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class commentList {
    private String comment;
    private String user_name;
    private String postingTime;
    private String user_avatar;
}
