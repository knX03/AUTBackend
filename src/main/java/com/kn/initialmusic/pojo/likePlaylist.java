package com.kn.initialmusic.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class likePlaylist {
    private String user_Name;
    private String playlist_Name;
    private String playlist_Cover;

    public likePlaylist() {
    }

    public likePlaylist(String user_Name, String playlist_Name, String playlist_Cover) {
        this.user_Name = user_Name;
        this.playlist_Name = playlist_Name;
        this.playlist_Cover = playlist_Cover;
    }
}
