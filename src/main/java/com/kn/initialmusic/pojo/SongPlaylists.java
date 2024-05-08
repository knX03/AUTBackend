package com.kn.initialmusic.pojo;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SongPlaylists {
    private Integer id;
    private String playlist_ID;
    private String playlist_Name;
    private String create_By;
    private String create_Time;
    private String playlist_Introduction;
    private String playlist_Cover;
}
