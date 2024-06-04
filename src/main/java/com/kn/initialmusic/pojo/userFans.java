package com.kn.initialmusic.pojo;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class userFans {
    private String fan_id;
    private String fan_name;
    private String fan_avatar;
    private String fan_introduction;
    private int fan_follow;
}
