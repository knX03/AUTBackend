package com.kn.initialmusic.pojo;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SongLibrary {
    private Integer id;
    private String song_ID;
    private String singer_ID;
    private String album_ID;
    private String addDate;


}
