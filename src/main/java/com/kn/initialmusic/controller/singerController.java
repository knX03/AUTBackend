package com.kn.initialmusic.controller;

import com.kn.initialmusic.pojo.Result;
import com.kn.initialmusic.pojo.Singer;
import com.kn.initialmusic.pojo.SongPlaylists;
import com.kn.initialmusic.pojo.User;
import com.kn.initialmusic.service.SingerService;
import com.kn.initialmusic.service.SongService;
import com.kn.initialmusic.util.SingerHolder;
import com.kn.initialmusic.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kn.initialmusic.controller.Code.SUCCESS;

@RestController
@CrossOrigin
@RequestMapping("/singer")
public class singerController {
    //项目路径
    private final static String FILE_SAVE_PREFIX_song = "static/songDirectory/";

    //歌曲绝对路径
    private final static String SAVE_PATH_SONG = "D:\\Workspeace\\vue3\\src\\songDirectory\\";

    //歌曲绝对项目路径
    private final static String SAVE_PATH_SONGCOVER = "D:\\Workspeace\\vue3\\src\\photos\\songCover\\";

    //歌曲封面项目路径
    private final static String FILE_SAVE_PREFIX_singerCover = "static/photos/albumCover/";

    //歌曲封面路径
    private static String albumCover_PATH;

    @Autowired
    private SongService songService;

    @Autowired
    private SingerService singerService;

    /*查询所有歌手*/
    @GetMapping("/allSingers")
    public Result selectAllSingers() {
        Result result = new Result();
        List<Singer> singers = singerService.selectAllSingers();
        result.setCode(200);
        result.setData(singers);
        return result;
    }

    /*歌手详情*/
    @GetMapping("/selectSingerDetail")
    public Result selectSingerDetail(@RequestParam String singer_ID) {
        Result result = new Result();
        Singer singer = singerService.selectDetailSinger(singer_ID);
        result.setCode(200);
        result.setData(singer);

        return result;
    }

    /*筛选歌手*/
    @PostMapping("/singerSelector")
    public Result singerSelector(@RequestBody String[] selectorList) {
        List<Singer> singers = singerService.singerSelector(selectorList[0],
                selectorList[1], selectorList[2]);
        Result result = new Result();
        result.setCode(200);
        result.setData(singers);
        return result;
    }


    @GetMapping("/getSingerByUser")
    public Result getSingerByUser() {
        User userH = UserHolder.getUser();
        String user_ID = userH.getUser_ID();
        Result result = singerService.getSingerByUser(user_ID);
        UserHolder.removeUser();
        return result;
    }

    @GetMapping("/sinLogOff")
    public Result sinLogOff(@RequestParam("singer_token") String singer_token) {
        Boolean flag = singerService.sinLogOff(singer_token);
        Result result = new Result();
        result.setCode(SUCCESS);
        result.setData(flag);
        result.setMsg("退出登录");
        return result;
    }

    @GetMapping("/getArtists")
    public Result getArtists() {
        Singer singer = SingerHolder.getSinger();
        Result result = new Result();
        result.setCode(SUCCESS);
        result.setData(singer);
        UserHolder.removeUser();
        return result;
    }

    /*创建歌单*/
    @PostMapping("/applySinger")
    public Result applySinger(@RequestBody Singer singer) {
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        singer.setUser_ID(user_ID);
        /*存入数据库*/
        Result result = singerService.applySinger(singer);
        UserHolder.removeUser();
        return result;
    }
}
