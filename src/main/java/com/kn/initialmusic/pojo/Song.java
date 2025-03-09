package com.kn.initialmusic.pojo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Song {
    private Integer id;
    private String song_ID;
    private String song_Name;
    private String singer_Name;
    private String singer_ID;
    private String album_Name;
    private String album_ID;
    private String created_Time;
    private String song_Directory;
    private String song_Cover;
    private String lyric;
    private String examine;
}
