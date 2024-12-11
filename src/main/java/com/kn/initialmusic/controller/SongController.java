package com.kn.initialmusic.controller;

import com.kn.initialmusic.pojo.PageBean;
import com.kn.initialmusic.pojo.Result;
import com.kn.initialmusic.pojo.Song;
import com.kn.initialmusic.pojo.User;
import com.kn.initialmusic.service.SongService;
import com.kn.initialmusic.util.UserHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.kn.initialmusic.util.songUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/song")
public class SongController {

    //歌曲存放的系统磁盘路径
    private final static String SAVE_PATH_song = "D:\\Workspeace\\vue3\\src\\songDirectory\\";

    private final static String SAVE_PATH_songCover = "D:\\Workspeace\\vue3\\src\\photos\\songCover\\";


    //歌曲封面路径
    private static String songCover_PATH;

    @Autowired
    private SongService songService;

    //歌曲的详细信息
    @GetMapping("/songDE")
    public Result songDetail(@RequestParam("songID") String songID) {
        return songService.songDetail(songID);
    }

    /*查询相关专辑的歌曲*/
    @GetMapping("/SongByAlbum")
    public Result selectSongByAlbum(@RequestParam("album_ID") String album_ID) {
        Result result = new Result();
        List<Song> songs = songService.selectSongByAlbum(album_ID);
        result.setCode(200);
        result.setData(songs);
        return result;
    }

    /*查询相关歌手的歌曲*/
    @GetMapping("/SongBySinger")
    public Result selectSongBySinger(@RequestParam("singer_ID") String singer_ID) {
        Result result = new Result();
        List<Song> songs = songService.selectSongBySinger(singer_ID);
        result.setCode(200);
        result.setData(songs);
        return result;
    }

    /*查询用户喜欢的歌曲*/
    @GetMapping("/SongByUser")
    public Result selectSongByUser(@RequestParam("currentPage") Integer currentPage,
                                   @RequestParam("pageSize") Integer pageSize) {
        Result result = new Result();
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        PageBean<Song> pageBean = songService.selectSongByUser(user_ID, currentPage, pageSize);
        result.setCode(200);
        result.setData(pageBean);
        UserHolder.removeUser();
        return result;
    }


    /*查询喜欢的歌曲*/
    @GetMapping("/selectLikeSong")
    public Result selectLikeSong() {
        Result result = new Result();
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        List<String> songNames = songService.selectLikeSong(user_ID);
        result.setCode(200);
        result.setData(songNames);
        UserHolder.removeUser();
        return result;
    }

    /*喜欢歌曲*/
    @GetMapping("/likeSong")
    public Result likeSong(@RequestParam("song_ID") String song_ID) {
        Result result = new Result();
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        Boolean flag = songService.likeSong(user_ID, song_ID);
        if (flag) {
            result.setCode(200);
        } else {
            result.setCode(500);
            result.setMsg("服务器内部错误！");
        }
        UserHolder.removeUser();
        return result;
    }

    /*移除喜欢的歌曲*/
    @GetMapping("/deleteLikeSong")
    public Result deleteLikeSong(@RequestParam("song_ID") String song_ID) {
        Result result = new Result();
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        Boolean flag = songService.deleteLikeSong(user_ID, song_ID);
        if (flag) {
            result.setCode(200);
        } else {
            result.setCode(500);
            result.setMsg("服务器内部错误！");
        }
        UserHolder.removeUser();
        return result;
    }
}
