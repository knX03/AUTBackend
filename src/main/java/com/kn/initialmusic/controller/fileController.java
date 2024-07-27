package com.kn.initialmusic.controller;

import com.kn.initialmusic.pojo.*;
import com.kn.initialmusic.service.AlbumService;
import com.kn.initialmusic.service.SingerService;
import com.kn.initialmusic.service.SongService;
import com.kn.initialmusic.util.SingerHolder;
import com.kn.initialmusic.util.UserHolder;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;

import static com.kn.initialmusic.controller.Code.SUCCESS;
import static com.kn.initialmusic.controller.Code.VALUES_REPEAT;


@RestController
@CrossOrigin
@RequestMapping("/file")
public class fileController {

    //歌曲存储绝对路径
    private final static String SAVE_PATH_song = "D:\\Workspeace\\vue3\\src\\songDirectory\\";


    //专辑封面绝对路径
    private final static String SAVE_PATH_ALBUMCOVER = "D:\\Workspeace\\vue3\\src\\photos\\albumCover\\";
    private final static String SAVE_PATH_SINGERAVATAR = "D:\\Workspeace\\vue3\\src\\photos\\singerAvatar\\";

    //线上存储绝对路径
    private final static String online_SAVE_PATH_song = "/www/wwwroot/AUTMusic/vue3/assets/songDirectory/";
    private final static String onLine_SAVE_PATH_ALBUMCOVER = "/www/wwwroot/AUTMusic/vue3/assets/albumCover/";
    private final static String onLine_SAVE_PATH_SINGERAVATAR = "/www/wwwroot/AUTMusic/vue3/assets/singerAvatar/";
    //线上项目路径
    private final static String onLine_FILE_SAVE_PREFIX_songDirectory = "/assets/songDirectory/";
    private final static String onLine_FILE_SAVE_PREFIX_songCover = "/assets/singerAvatar/";
    private final static String onLine_FILE_SAVE_PREFIX_albumCover = "/assets/albumCover/";


    @Autowired
    private AlbumService albumService;

    @Autowired
    private SongService songService;
    @Autowired
    private SingerService singerService;

    @PostMapping("/download")
    public void download(@RequestBody String filepath, HttpServletResponse response) throws IOException {
        /*if (filepath != null) {
            File file = new File(filepath);
            if (file.exists()) {
                // 取得文件名。
                String filename = file.getName();
                // 取得文件的后缀名。
                String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
                String name = filename + ext;
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, "utf-8"));
                FileInputStream inputStream = new FileInputStream(file);//创建文件输入流对象
                ServletOutputStream outputStream = response.getOutputStream();//创建文件输出流对象
                byte[] buf = new byte[1024]; //定义一个字节数组作为缓冲区，并指定一次读取1024个字节
                int len; //记住读入缓冲区的字节长度
                while ((len = inputStream.read(buf)) != -1) { //判断文件是否读完（读完后会返回-1）
                    //注意不要用output.write(buf); 否则可能导致最终写入的文件比原文件大，如果文件是图片的话，那么就会失真
                    outputStream.write(buf, 0, len); //从buf缓冲区中读取数据，从第一个字节开始读，一次读取len，并将读取出来的数据写入文件输出流对象中
                }
                inputStream.close();
                outputStream.close();
            }
        }*/
    }

    @PostMapping("/uploadAlCover")
    public Result uploadAlCover(@RequestParam("alCover") MultipartFile file) throws IOException {
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
        result.setCode(200);
        result.setData("/src/photos/albumCover/" + filename);
        result.setMsg("上传成功！");
        return result;
    }

    //todo 上传进入待审核表
    @PostMapping("/uploadSong")
    public Result uploadSong(@RequestParam("file") MultipartFile[] file, @RequestParam("songName") String[] songNames, @RequestPart("album") Album album) throws IOException {
        Result result = new Result();
        Singer singer = SingerHolder.getSinger();
        String singer_ID = singer.getSinger_ID();
        String albumId = album.getAlbum_ID();
        Song song = new Song();
        if (albumId.isEmpty()) {
            album.setSinger_ID(singer_ID);
            albumId = albumService.createAlbum(album);
        }
        int i = 0;
        for (MultipartFile multipartFile : file) {
            //获取文件名字
            String fileName = multipartFile.getOriginalFilename();//歌曲名称
            String path = SAVE_PATH_song + fileName;
            File filePath = new File(path);
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
            outputStream.write(multipartFile.getBytes());
            outputStream.flush();
            outputStream.close();
            /*存入数据库*/
            String song_Directory = "/src/songDirectory/" + fileName;//歌曲路径
            Boolean eFlag = songService.ifExistBySongDirectory(song_Directory);
            if (eFlag) {
                result.setCode(VALUES_REPEAT);
                result.setData(songNames[i]);
                i++;
                break;
            } else {
                song.setSong_Name(songNames[i]);
                song.setSinger_ID(singer_ID);
                song.setAlbum_ID(albumId);
                song.setSong_Directory(song_Directory);
                song.setSong_Cover(album.getAlbum_Cover());
                songService.saveSong(song);
                result.setCode(SUCCESS);
                i++;
            }
        }
        SingerHolder.removeSinger();
        return result;
    }

    @PostMapping("/uploadSGAvatar")
    public Result uploadSGAvatar(@RequestParam("singerAvatar") MultipartFile file) throws IOException {
        Result result = new Result();
        //获取文件名字
        String filename = file.getOriginalFilename();
        //以上述路径创建File对象
        String path = SAVE_PATH_SINGERAVATAR + filename;
        File filePath = new File(path);
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
        outputStream.write(file.getBytes());
        outputStream.flush();
        outputStream.close();
        /*封面路径*/
        result.setCode(200);
        result.setData("/src/photos/singerAvatar/" + filename);
        result.setMsg("上传成功！");
        return result;
    }
}
