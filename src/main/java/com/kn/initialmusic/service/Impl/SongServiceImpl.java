package com.kn.initialmusic.service.Impl;

import com.kn.initialmusic.mapper.SongMapper;
import com.kn.initialmusic.pojo.PageBean;
import com.kn.initialmusic.pojo.Song;
import com.kn.initialmusic.service.GenerateIDService;
import com.kn.initialmusic.service.SongService;
import com.kn.initialmusic.util.songUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongMapper songMapper;
    @Autowired
    private GenerateIDService generateIDService;

    @Override
    public Boolean saveSong(Song song) {

        String songID = generateIDService.generateSongID();
        song.setSong_ID(songID);
        try {
            songMapper.saveSong(song);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
        songUtil.formatSDirectory(songs);

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
    public List<String> searchSong(String searchValue) {
        List<String> searchList = new ArrayList<>();
        List<String> strings = songMapper.searchSong(searchValue);
        List<List<String>> lists = songMapper.searchAll(searchValue);
        for (List<String> list : lists) {
            if (list != null && list.size() > 0) {
                searchList.addAll(list);
            }
        }
        return searchList;
    }

    @Override
    public Boolean deleteLikeSong(String user_ID, String song_ID) {
        int flag = songMapper.deleteLikeSong(user_ID, song_ID);
        return flag > 0;
    }

}
