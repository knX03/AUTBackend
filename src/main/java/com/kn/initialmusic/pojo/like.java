package com.kn.initialmusic.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class like {
    private String user_Name;
    private String song_Name;
    private String singer_Name;
    private String album_Name;

    public like() {
    }

    public like(String user_Name, String song_Name, String singer_Name, String album_Name) {
        this.user_Name = user_Name;
        this.song_Name = song_Name;
        this.singer_Name = singer_Name;
        this.album_Name = album_Name;
    }
}
