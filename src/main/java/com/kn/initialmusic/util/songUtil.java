package com.kn.initialmusic.util;

import com.kn.initialmusic.pojo.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class songUtil {

    public static void formatSDirectory(List<Song> songs) {
        for (Song song : songs) {
            String newSong_Directory = "http://localhost:5173/" + song.getSong_Directory();
            song.setSong_Directory(newSong_Directory);
        }
    }

    public static List<String> searchValue(List<String> searchList, String searchValue) {
        List<String> resList = new ArrayList<>();
        String pattern = "^" + searchValue;
        Pattern p = Pattern.compile(pattern);
        Matcher m = null;
        for (String s : searchList) {
            m = p.matcher(s);
            if (m.find()) {
                resList.add(s);
            }
        }
        return resList;
    }
}
