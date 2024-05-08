package com.kn.initialmusic.pojo;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Album {
    private Integer id;
    private String album_ID;
    private String album_Name;
    private String singer_ID;
    private String singer_name;
    private String song_ID;
    private String create_Time;
    private String album_Introduction;
    private String album_Cover;
}
