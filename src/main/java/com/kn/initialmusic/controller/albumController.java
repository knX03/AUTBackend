package com.kn.initialmusic.controller;

import com.kn.initialmusic.pojo.Album;
import com.kn.initialmusic.pojo.Result;
import com.kn.initialmusic.pojo.Song;
import com.kn.initialmusic.service.AlbumService;
import com.kn.initialmusic.service.SingerService;
import com.kn.initialmusic.service.SongService;
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

    //项目路径
    private final static String SAVE_PATH_song = "D:\\Workspeace\\InitialMusic\\src\\main\\resources\\static\\songDirectory\\";
    private final static String FILE_SAVE_PREFIX_song = "static/songDirectory/";

    //专辑封面绝对路径
    private final static String SAVE_PATH_ALBUMCOVER = "D:\\Workspeace\\vue3\\vue3\\src\\photos\\albumCover\\";

    //专辑封面项目路径
    private final static String FILE_SAVE_PREFIX_albumCover = "static/photos/albumCover/";

    //歌曲封面路径
    private static String ALBUMCOVER_PATH;


    @Autowired
    private AlbumService albumService;

    @Autowired
    private SongService songService;

    @Autowired
    private SingerService singerService;

    //查询所有专辑
    @GetMapping("/allAlbums")
    public Result selectAllAlbum() {
        Result result = new Result();
        List<Album> albums = albumService.selectAllAlbum();
        result.setCode(200);
        result.setData(albums);
        return result;
    }

    //随机查询9个专辑
    @GetMapping("/selectAlbumNine")
    public Result selectAlbumNine() {
        Result result = new Result();
        List<Album> albums = albumService.selectAlbumNine();
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

    /*将路径加文件名称与歌曲信息存储至数据库*/
    @PostMapping("/uploadMuCover")
    public Result upCover(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result = new Result();
        //获取文件名字
        String filename = file.getOriginalFilename();
        String filePathmu = new String(SAVE_PATH_ALBUMCOVER);
        //以上述路径创建File对象
        String path = filePathmu + filename;
        File filePath = new File(path);
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
        outputStream.write(file.getBytes());
        outputStream.flush();
        outputStream.close();
        /*写入数据库*/
        /*封面路径*/
        ALBUMCOVER_PATH = "src/photos/albumCover/" + filename;
        result.setCode(200);
        result.setData("src/photos/albumCover/" + filename);
        result.setMsg("上传成功！");
        return result;
    }


    /*多文件上传 */
    @PostMapping("/uploadMore")
    public Result uploadMore(@RequestParam("file") MultipartFile[] file, @RequestPart("album") Album album, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Result result = new Result();
        String album_name = album.getAlbum_Name();//专辑名称
        String singer_name = album.getSinger_name();//歌手名称
        String create_time = album.getCreate_Time();//创建时间
        album.setAlbum_Cover(ALBUMCOVER_PATH);//专辑封面
        Boolean alFlag = albumService.createAlbum(album);
        albumService.creatSingerAlbum(singer_name, album_name);
        if (alFlag) {
            result.setCode(200);
        } else {
            result.setCode(500);
        }
        for (MultipartFile multipartFile : file) {
            //获取文件名字
            String filename = multipartFile.getOriginalFilename();//歌曲名称

            //通过缓存区输出流BufferedOutputStream的对象来将上传的文件写入filePathmu文件夹中
            String filePathmu = new String(SAVE_PATH_song);
            String path = filePathmu + filename;
            File filePath = new File(path);
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
            outputStream.write(multipartFile.getBytes());
            outputStream.flush();
            outputStream.close();
            /*存入数据库*/
            String song_Directory = "src/songDirectory/" + filename;//歌曲路径

            Boolean eFlag = songService.ifExistBySongDirectory(song_Directory);
            if (eFlag) {
                break;
            } else {
                Song song = new Song();
                song.setSong_Name(filename);
                song.setSong_Cover(ALBUMCOVER_PATH);
                song.setSong_Directory(song_Directory);
                song.setAlbum_ID(album_name);
                song.setCreated_Time(create_time);
                song.setSinger_ID(singer_name);
                Boolean sFlag = songService.saveSong(song);
                if (sFlag) {
                    result.setCode(200);
                } else {
                    result.setCode(500);
                }
            }
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
    public Result selectLikeAlbum(@RequestParam String user_ID) {
        Result result = new Result();
        List<Album> albums = albumService.selectLikeAlbum(user_ID);
        result.setCode(200);
        result.setData(albums);
        return result;
    }

    /*查询是否已收藏专辑*/
    @GetMapping("/ifCollectAlbum")
    public Result ifCollectAlbum(@RequestParam("album_ID") String album_ID, @RequestParam("user_ID") String user_ID) {
        Result result = new Result();
        Boolean flag = albumService.ifCollectAlbum(album_ID, user_ID);
        if (flag) {
            result.setCode(302);
            result.setMsg("专辑已收藏！");
        } else {
            result.setCode(200);
            result.setMsg("专辑未收藏！");
        }
        return result;
    }

    /*收藏专辑*/
    @GetMapping("/collectAlbum")
    public Result collectAlbum(@RequestParam("album_ID") String album_ID, @RequestParam("user_ID") String user_ID) {
        Result result = new Result();
        Boolean flag = albumService.collectAlbum(album_ID, user_ID);
        if (flag) {
            result.setCode(200);
            result.setMsg("收藏成功！");
        } else {
            result.setCode(500);
            result.setMsg("内部服务器异常！");
        }
        return result;
    }

    /*移除喜欢的专辑*/
    @GetMapping("/deleteLikeAlbum")
    public Result deleteLikeAlbum(@RequestParam("album_ID") String album_ID, @RequestParam("user_ID") String user_ID) {
        Result result = new Result();
        Boolean flag = albumService.deleteLikeAlbum(user_ID, album_ID);
        if (flag) {
            result.setCode(200);
        } else {
            result.setCode(500);
            result.setMsg("服务器内部错误！");
        }
        return result;
    }

    /*移除喜欢的专辑*/
    @GetMapping("/getSingerAlbums")
    public Result getSingerAlbums(@RequestParam("singer_ID") String singer_ID) {
        Result result = new Result();
        List<Album> albums = albumService.getSingerAlbums(singer_ID);
        if (albums!=null) {
            result.setCode(200);
            result.setData(albums);
        } else {
            result.setCode(500);
            result.setMsg("服务器内部错误！");
        }
        return result;
    }


}
