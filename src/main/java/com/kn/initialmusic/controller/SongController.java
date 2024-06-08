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

    //项目路径
    private final static String FILE_SAVE_PREFIX_song = "src/songDirectory/";
    //歌曲封面项目路径
    private final static String FILE_SAVE_PREFIX_songCover = "static/photos/songCover/";

    //歌曲封面路径
    private static String songCover_PATH;

    @Autowired
    private SongService songService;


    @PostMapping("/uploadMusic")
    /*将路径加文件名称与歌曲信息存储至数据库（路径即path：项目路径）*/
    public Result upMusic(@RequestParam("file") MultipartFile file, @RequestPart("song") Song song, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result = new Result();
        //获取文件名字
        String filename = file.getOriginalFilename();
        String filePathmu = new String(SAVE_PATH_song);
        //String filePathmu = new String(SAVE_PATH_song);

        /*绝对路径*/
        String path = filePathmu + filename;
        File filePath = new File(path);
        //通过缓存区输出流BufferedOutputStream的对象来将上传的文件写入filePathmu文件夹中
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
        outputStream.write(file.getBytes());
        outputStream.flush();
        outputStream.close();

        /*写入数据库*/
        String song_Directory = "src/songDirectory/" + filename;
        song.setSong_Directory(song_Directory);
        song.setSong_Cover(songCover_PATH);
        Boolean saveSongFlag = songService.saveSong(song);
        if (saveSongFlag) {
            result.setCode(200);
            result.setMsg("上传成功！");
        } else {
            result.setCode(500);
            result.setMsg("存储数据库失败！");
        }
        return result;
    }

    /*将路径加文件名称与歌曲信息存储至数据库（路径即path：项目路径）*/
    @PostMapping("/uploadMuCover")
    public Result upCover(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result = new Result();
        //获取文件名字
        String filename = file.getOriginalFilename();
        //String filePathmu = new String("src/main/resources/" + FILE_SAVE_PREFIX_songCover);
        String filePathmu = new String(SAVE_PATH_songCover);
        //以上述路径创建File对象
        String path = filePathmu + filename;
        File filePath = new File(path);
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
        outputStream.write(file.getBytes());
        outputStream.flush();
        outputStream.close();
        /*写入数据库*/
        /*封面路径*/
        songCover_PATH = "src/photos/songCover/" + filename;
        result.setCode(200);
        result.setData("src/photos/songCover/" + filename);
        result.setMsg("上传成功！");
        return result;
    }


    /*多文件上传 */
    @PostMapping("/uploadMore")
    public Result uploadMore(@RequestParam("file") MultipartFile[] file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result = new Result();

        for (MultipartFile multipartFile : file) {
            //获取文件名字
            String filename = multipartFile.getOriginalFilename();

            //通过缓存区输出流BufferedOutputStream的对象来将上传的文件写入filePathmu文件夹中
            String filePathmu = new String(SAVE_PATH_song);
            String path = filePathmu + filename;
            File filePath = new File(path);
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
            outputStream.write(multipartFile.getBytes());
            outputStream.flush();
            outputStream.close();
            result.setCode(200);
            result.setMsg("上传成功！");
        }
        return result;
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

    /*搜索歌曲*/
    @GetMapping("/searchSong")
    public Result searchSong(String searchValue) {
        Result result = new Result();
        List<String> strings = songService.searchSong(searchValue);
        result.setCode(200);
        result.setData(strings);
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
