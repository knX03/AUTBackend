package com.kn.initialmusic.controller;


import com.kn.initialmusic.pojo.*;
import com.kn.initialmusic.service.SongPlaylistsService;
import com.kn.initialmusic.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/songPlaylist")
public class songPlaylistsController {

    //歌单封面存放的系统磁盘路径
    private final static String SAVE_PATH_songPlaylistCover =
            "D:\\Workspeace\\vue3\\src\\photos\\songPlaylistCover\\";

    //歌单封面项目路径
    private final static String FILE_SAVE_PREFIX_songPlaylistCover = "static/photos/songPlaylistCover/";

    //歌单封面路径
    private static String songPlaylistCover_PATH;

    private static final String DAILYLIST_ID = "10000";

    @Autowired
    private SongPlaylistsService songPlaylistsService;

    /*查询所有歌单*/
    @GetMapping("/allPlaylists")
    public Result selectAllPlaylists() {
        Result result = new Result();
        List<SongPlaylists> songPlaylists = songPlaylistsService.selectAllPlaylists();

        result.setCode(200);
        result.setData(songPlaylists);
        return result;
    }

    /*歌单名称是否存在*/
    @GetMapping("/checkPlaylistName")
    public Result checkPlaylistName(@RequestParam String playlist_Name) {
        Result result = new Result();
        Boolean flag = songPlaylistsService.checkPlaylistName(playlist_Name);
        if (flag) {
            result.setCode(302);
            result.setMsg("名称重复！");
        } else {
            result.setCode(200);
            result.setMsg("success");
        }
        return result;
    }


    /*随机查询十个歌单*/
    @GetMapping("/NineRandomPlaylist")
    public Result NineRandomPlaylist() {
        Result result = new Result();
        List<SongPlaylists> songPlaylists = songPlaylistsService.NineRandomPlaylist();
        if (songPlaylists != null) {
            result.setCode(200);
            result.setData(songPlaylists);
        } else {
            result.setCode(500);
            result.setMsg("服务器异常，数据库查询数据为空");
        }
        return result;
    }

    /*根据歌单ID查询歌单详情*/
    @GetMapping("/DetailByID")
    public Result selectByName(@RequestParam("playlist_ID") String playlist_ID) {
        Result result = new Result();
        SongPlaylists songPlaylist = songPlaylistsService.selectDetailByID(playlist_ID);
        if (songPlaylist != null) {
            result.setCode(200);
            result.setData(songPlaylist);
        } else {
            result.setCode(500);
            result.setMsg("内部服务器异常！");
        }
        return result;
    }

    /*查询歌单的标签列表*/
    @GetMapping("/selectPlaylistTags")
    public Result selectPlaylistTags(@RequestParam("playlist_ID") String playlist_ID) {
        Result result = new Result();
        List<PLTag> plTags = songPlaylistsService.selectPlaylistTags(playlist_ID);
        if (plTags != null) {
            result.setCode(200);
            result.setData(plTags);
        } else {
            result.setCode(500);
            result.setMsg("内部服务器异常！");
        }
        return result;
    }

    /*根据用户ID查询创建歌单列表*/
    @GetMapping("/createPlaylist")
    public Result selectCreatePlaylist() {
        Result result = new Result();
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        List<SongPlaylists> songPlaylists = songPlaylistsService.selectCreatePlaylist(user_ID);
        if (songPlaylists != null) {
            result.setCode(200);
            result.setData(songPlaylists);
        } else {
            result.setCode(500);
            result.setMsg("内部服务器异常！");
        }
        UserHolder.removeUser();
        return result;
    }

    /*根据用户ID查询收藏歌单列表*/
    @GetMapping("/likePlaylist")
    public Result selectLikePlaylist() {
        Result result = new Result();
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        List<SongPlaylists> songPlaylists = songPlaylistsService.selectLikePlaylist(user_ID);
        if (songPlaylists != null) {
            result.setCode(200);
            result.setData(songPlaylists);
        } else {
            result.setCode(500);
            result.setMsg("内部服务器异常！");
        }
        UserHolder.removeUser();
        return result;
    }

    /*查询歌单里的歌*/
    @GetMapping("/selectSongByPlaylist")
    public Result selectSongByPlaylist(@RequestParam("playlist_ID") String playlist_ID) {
        Result result = new Result();
        List<Song> songs = songPlaylistsService.selectSongByPlaylist(playlist_ID);
        if (songs != null) {
            result.setCode(200);
            result.setData(songs);
        } else {
            result.setCode(500);
            result.setMsg("内部服务器异常!");
        }
        return result;
    }

    /*根据日常修改日推*/
    @GetMapping("/changeDailyList")
    public Result changeDailyList() {
        Result result = new Result();
        SongPlaylists songPlaylists = songPlaylistsService.selectDetailByID(DAILYLIST_ID);
        String createTime = songPlaylists.getCreate_Time();
        String date = String.valueOf(new Date(System.currentTimeMillis()));
        if (date.equals(createTime)) {
            result.setCode(200);
            return result;
        }
        Boolean flag = songPlaylistsService.changeDailyList();
        if (flag) {
            result.setCode(200);
        } else {
            result.setCode(500);
            result.setMsg("内部服务器异常!");
        }
        return result;
    }

    /*收藏歌单*/
    @GetMapping("/collectPlaylist")
    public Result collectPlaylist(@RequestParam("playlist_ID") String playlist_ID) {
        Result result = new Result();
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        Boolean flag = songPlaylistsService.collectPlaylist(playlist_ID, user_ID);
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

    /*查询是否已收藏歌单*/
    @GetMapping("/ifCollectPlaylist")
    public Result ifCollectPlaylist(@RequestParam("playlist_ID") String playlist_ID) {
        Result result = new Result();
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        Boolean flag = songPlaylistsService.ifCollectPlaylist(playlist_ID, user_ID);
        if (flag) {
            result.setCode(302);
            result.setMsg("歌单已收藏！");
        } else {
            result.setCode(200);
            result.setMsg("歌单未收藏！");
        }
        UserHolder.removeUser();
        return result;
    }

    @GetMapping("/ifMyPlaylist")
    public Result ifMyPlaylist(@RequestParam("playlist_ID") String playlist_ID) {
        Result result = new Result();
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        Boolean flag = songPlaylistsService.ifMyPlaylist(playlist_ID, user_ID);
        result.setCode(200);
        result.setData(flag);
        UserHolder.removeUser();
        return result;
    }


    /*修改歌单资料*/
    @PostMapping("/changePlaylistInfo")
    public Result changeUserInfo(@RequestBody SongPlaylists songPlaylists) {
        Result result = new Result();
        Boolean spFlag = songPlaylistsService.changePlaylistInfo(songPlaylists);
        if (spFlag) {
            result.setCode(200);
            result.setData(spFlag);
            result.setMsg("修改成功！");
        } else {
            result.setCode(500);
            result.setMsg("内部服务器异常！");
        }
        return result;
    }


    /*上传封面*/
    @PostMapping("/uploadCover")
    public Result uploadCover(@RequestParam("file") MultipartFile file) throws IOException {
        Result result = new Result();
        //获取文件名字
        String filename = file.getOriginalFilename();
        String filePathmu = new String(SAVE_PATH_songPlaylistCover);
        //以上述路径创建File对象
        String path = filePathmu + filename;
        File filePath = new File(path);
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
        outputStream.write(file.getBytes());
        outputStream.flush();
        outputStream.close();
        /*头像路径*/
        songPlaylistCover_PATH = "src/photos/songPlaylistCover/" + filename;
        result.setCode(200);
        result.setData("src/photos/songPlaylistCover/" + filename);
        result.setMsg("上传成功！");
        return result;
    }

    /*创建歌单*/
    @PostMapping("/createNewPlaylist")
    public Result createNewPlaylist(@RequestBody SongPlaylists songPlaylists) {
        Result result = new Result();
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        songPlaylists.setCreate_By(user_ID);
        /*存入数据库*/
        Boolean flag = songPlaylistsService.createNewPlaylist(songPlaylists);
        if (flag) {
            result.setCode(200);
            result.setMsg("创建成功！");
        } else {
            result.setCode(500);
            result.setMsg("内部服务器异常！");
        }
        UserHolder.removeUser();
        return result;
    }

    /*歌曲是否已经收藏至歌单*/
    @GetMapping("/IESong")
    public Result ifExistSong(@RequestParam String playlist_ID, @RequestParam String song_ID) {
        Result result = new Result();
        Boolean flag = songPlaylistsService.ifExistSong(playlist_ID, song_ID);
        if (flag) {
            result.setCode(302);
            result.setMsg("歌曲已经收藏！");
        } else {
            result.setCode(200);
            result.setMsg("success");
        }
        return result;
    }

    /*将歌曲收藏至指定歌单*/
    @GetMapping("/CLSong")
    public Result collectSongToPlaylist(@RequestParam String playlist_ID, @RequestParam String song_ID) {
        Result result = new Result();
        Boolean flag = songPlaylistsService.collectSongToPlaylist(playlist_ID, song_ID);
        if (flag) {
            result.setCode(200);
            result.setMsg("success");
        }
        return result;
    }

    /*删除指定歌单的歌曲*/
    @GetMapping("/DESong")
    public Result deleteSongByPlaylistID(@RequestParam String playlist_ID, @RequestParam String song_ID) {
        Result result = new Result();
        Boolean flag = songPlaylistsService.deleteSongByPlaylistID(playlist_ID, song_ID);
        if (flag) {
            result.setCode(200);
            result.setMsg("success");
        }
        return result;
    }

    /*删除歌单*/
    @GetMapping("/DEPlaylist")
    public Result deletePlaylistByUser(@RequestParam String playlist_ID) {
        Result result = new Result();
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        Boolean flag = songPlaylistsService.deletePlaylistByUser(user_ID, playlist_ID);
        if (flag) {
            result.setCode(200);
            result.setMsg("success");
        }
        UserHolder.removeUser();
        return result;
    }

    /*移除喜欢的歌单*/
    @GetMapping("/deleteLikePlaylist")
    public Result deleteLikePlaylist(@RequestParam("playlist_ID") String playlist_ID) {
        Result result = new Result();
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        Boolean flag = songPlaylistsService.deleteLikePlaylist(playlist_ID, user_ID);
        if (flag) {
            result.setCode(200);
        } else {
            result.setCode(500);
            result.setMsg("服务器内部错误！");
        }
        UserHolder.removeUser();
        return result;
    }

    /*查询所有歌单标签*/
    @GetMapping("/getAllPLTag")
    public Result getAllPLTag() {
        Result result = new Result();
        List<PLTag> plTags = songPlaylistsService.getAllPLTag();
        if (!plTags.isEmpty()) {
            result.setCode(200);
            result.setData(plTags);
        } else {
            result.setCode(500);
            result.setMsg("服务器内部错误！");
        }
        return result;
    }

    /*根据歌单标签查询*/
    @GetMapping("/selectPlaylistByTag")
    public Result selectPlaylistByTag(@RequestParam("playlist_tag") String playlist_Tag) {
        Result result = new Result();
        List<SongPlaylists> songPlaylists = songPlaylistsService.selectPlaylistByTag(playlist_Tag);
        if (!songPlaylists.isEmpty()) {
            result.setCode(200);
            result.setData(songPlaylists);
        } else {
            result.setCode(500);
            result.setMsg("服务器内部错误！");
        }
        return result;
    }
}
