package com.kn.initialmusic.controller;

import com.kn.initialmusic.pojo.*;
import com.kn.initialmusic.service.AlbumService;
import com.kn.initialmusic.service.SingerService;
import com.kn.initialmusic.service.SongService;
import com.kn.initialmusic.util.SingerHolder;
import com.kn.initialmusic.util.UserHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/album")
public class albumController {



    @Autowired
    private AlbumService albumService;

    @Autowired
    private SongService songService;

    @Autowired
    private SingerService singerService;


    //查询所有专辑
    @GetMapping("/allAlbums")
    public Result selectAllAlbum() {
        return albumService.selectAllAlbum();
    }

    //随机查询10个专辑
    @GetMapping("/TenRandomAlbum")
    public Result TenRandomAlbum() {
        Result result = new Result();
        List<Album> albums = albumService.TenRandomAlbum();
        if (albums != null) {
            result.setCode(200);
            result.setData(albums);
        } else {
            result.setCode(500);
            result.setMsg("服务器异常，数据库查询数据为空");
        }
        return result;
    }


    //检查专辑是否存在
    @GetMapping("/checkAlbumName")
    public Result checkAlbumName(@RequestParam String album_Name) {
        Result result = new Result();
        Boolean flag = albumService.checkAlbumName(album_Name);
        if (flag) {
            result.setCode(302);
            result.setMsg("专辑已存在！");
        } else {
            result.setCode(200);
        }
        return result;
    }

    //查询专辑详细信息
    @GetMapping("/selectDetail")
    public Result selectDetailAlbum(@RequestParam String album_ID) {
        Result result = new Result();
        Album album = albumService.selectDetailAlbum(album_ID);
        result.setCode(200);
        result.setData(album);
        return result;
    }

    //查询用户收藏的专辑
    @GetMapping("/likeAlbum")
    public Result selectLikeAlbum() {
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        Result result = new Result();
        List<Album> albums = albumService.selectLikeAlbum(user_ID);
        result.setCode(200);
        result.setData(albums);
        UserHolder.removeUser();
        return result;
    }

    /*查询是否已收藏专辑*/
    @GetMapping("/ifCollectAlbum")
    public Result ifCollectAlbum(@RequestParam("album_ID") String album_ID) {
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        Result result = new Result();
        Boolean flag = albumService.ifCollectAlbum(album_ID, user_ID);
        if (flag) {
            result.setCode(302);
            result.setMsg("专辑已收藏！");
        } else {
            result.setCode(200);
            result.setMsg("专辑未收藏！");
        }
        UserHolder.removeUser();
        return result;
    }

    /*收藏专辑*/
    @PutMapping("/collectAlbum")
    public Result collectAlbum(@RequestParam("album_ID") String album_ID) {
        Result result = new Result();
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        Boolean flag = albumService.collectAlbum(album_ID, user_ID);
        if (flag) {
            result.setCode(200);
            result.setMsg("收藏成功！");
        } else {
            result.setCode(500);
            result.setMsg("内部服务器异常！");
        }
        UserHolder.removeUser();
        return result;
    }

    /*移除喜欢的专辑*/
    @PutMapping("/deleteLikeAlbum")
    public Result deleteLikeAlbum(@RequestParam("album_ID") String album_ID) {
        Result result = new Result();
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        Boolean flag = albumService.deleteLikeAlbum(user_ID, album_ID);
        if (flag) {
            result.setCode(200);
        } else {
            result.setCode(500);
            result.setMsg("服务器内部错误！");
        }
        UserHolder.removeUser();
        return result;
    }

    /*歌手的专辑*/
    @GetMapping("/getSingerAlbums")
    public Result getSingerAlbums(@RequestParam("singer_ID") String singer_ID) {
        Result result = new Result();
        List<Album> albums = albumService.getSingerAlbums(singer_ID);
        if (albums != null) {
            result.setCode(200);
            result.setData(albums);
        } else {
            result.setCode(500);
            result.setMsg("服务器内部错误！");
        }
        return result;
    }

    /*歌手的专辑*/
    @GetMapping("/getUserAlbums")
    public Result getUserAlbums() {
        Result result = new Result();
        Singer singer = SingerHolder.getSinger();
        String singer_ID = singer.getSinger_ID();
        List<Album> albums = albumService.getUserAlbums(singer_ID);
        if (!albums.isEmpty()) {
            result.setCode(200);
            result.setData(albums);
        } else {
            result.setCode(302);
            result.setMsg("用户未创建专辑！");
        }
        SingerHolder.removeSinger();
        return result;
    }
}
