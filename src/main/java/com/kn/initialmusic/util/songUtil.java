package com.kn.initialmusic.util;

import com.kn.initialmusic.pojo.Song;

import java.util.List;

public class songUtil {

    public static void formatSDirectory(List<Song> songs) {
        for (Song song : songs) {
            String newSong_Directory = "http://localhost:5173/" + song.getSong_Directory();
            song.setSong_Directory(newSong_Directory);
        }
    }
}
