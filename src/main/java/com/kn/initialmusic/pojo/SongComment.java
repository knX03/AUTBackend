package com.kn.initialmusic.pojo;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SongComment {
    private Integer id;
    private String song_ID;
    private String comment;
    private String creator;
    private String postingTime;
}
