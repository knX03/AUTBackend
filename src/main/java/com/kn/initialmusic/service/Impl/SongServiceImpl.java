package com.kn.initialmusic.service.Impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.kn.initialmusic.mapper.SongMapper;
import com.kn.initialmusic.pojo.PageBean;
import com.kn.initialmusic.pojo.Result;
import com.kn.initialmusic.pojo.Song;
import com.kn.initialmusic.service.GenerateIDService;
import com.kn.initialmusic.service.SongService;
import com.kn.initialmusic.util.songUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

import static com.kn.initialmusic.controller.Code.SUCCESS;
import static com.kn.initialmusic.util.RedisConstants.CACHE_SEARCH_KEY;
import static com.kn.initialmusic.util.songUtil.searchValue;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongMapper songMapper;
    @Autowired
    private GenerateIDService generateIDService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result songDetail(String song_ID) {
        Result result = new Result();
        Song song = songMapper.songDetail(song_ID);
        if (song != null) {
            result.setCode(SUCCESS);
            result.setData(song);
        }
        return result;
    }

    @Override
    public Boolean saveSong(Song song) {
        String songID = generateIDService.generateSongID();
        song.setSong_ID(songID);
        int flag = songMapper.saveSong(song);
        return flag > 0;
    }


    @Override
    public Boolean ifExistBySongDirectory(String songDirectory) {
        String flag = songMapper.ifExistBySongDirectory(songDirectory);
        return flag != null;
    }

    @Override
    public List<Song> selectSongByAlbum(String album_ID) {
        List<Song> songs = songMapper.selectSongByAlbum(album_ID);
        songUtil.formatSDirectory(songs);
        return songs;
    }

    @Override
    public List<Song> selectSongBySinger(String singer_ID) {
        List<Song> songs = songMapper.selectSongBySinger(singer_ID);
        songUtil.formatSDirectory(songs);
        return songs;
    }

    @Override
    public PageBean<Song> selectSongByUser(String user_ID, Integer currentPage, Integer pageSize) {

        int begin = (currentPage - 1) * pageSize;
        int size = pageSize;

        int totalCount = songMapper.selectTotalCount(user_ID);

        List<Song> songs = songMapper.selectSongByUser(user_ID, begin, size);
        //格式化歌曲路径
//        songUtil.formatSDirectory(songs);

        PageBean<Song> pageBean = new PageBean<>();
        pageBean.setRows(songs);
        pageBean.setTotalCount(totalCount);

        return pageBean;
    }

    @Override
    public List<String> selectLikeSong(String user_ID) {
        List<String> songNames = songMapper.selectLikeSong(user_ID);
        return songNames;
    }

    @Override
    public Boolean likeSong(String user_ID, String song_ID) {
        int flag = songMapper.likeSong(user_ID, song_ID);
        return flag > 0;
    }


    @Override
    public Boolean deleteLikeSong(String user_ID, String song_ID) {
        int flag = songMapper.deleteLikeSong(user_ID, song_ID);
        return flag > 0;
    }

}
