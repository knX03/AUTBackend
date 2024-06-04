package com.kn.initialmusic.controller;


import com.kn.initialmusic.pojo.Result;
import com.kn.initialmusic.pojo.Singer;
import com.kn.initialmusic.service.SingerService;
import com.kn.initialmusic.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


}
