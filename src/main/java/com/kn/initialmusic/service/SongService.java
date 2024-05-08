package com.kn.initialmusic.service;

import com.kn.initialmusic.pojo.PageBean;
import com.kn.initialmusic.pojo.Song;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.util.List;

@Transactional
public interface SongService {

    /**
     * 保存歌曲
     *
     * @return
     * @Param song
     */
    Boolean saveSong(Song song);

    /**
     * 查询是否已经存储歌曲
     *
     * @param songDirectory
     * @return
     */
    Boolean ifExistBySongDirectory(String songDirectory);

    /**
     * 查询相关专辑的歌曲
     *
     * @param album_ID
     * @return
     */
    List<Song> selectSongByAlbum(String album_ID);

    /**
     * 查询相关歌手的歌曲
     *
     * @param singer_ID
     * @return
     */
    List<Song> selectSongBySinger(String singer_ID);

    /**
     * 查询用户喜欢的歌曲
     *
     * @param user_ID
     * @return
     */
    PageBean<Song> selectSongByUser(String user_ID , Integer currentPage , Integer pageSize);

    /**
     * 查询用户喜欢的歌曲
     *
     * @param user_ID
     * @return
     */
    List<String> selectLikeSong(String user_ID);

    /**
     * 喜欢歌曲
     *
     * @param user_ID
     * @param song_ID
     * @return
     */
    Boolean likeSong(String user_ID, String song_ID);

    /**
     * 不喜欢歌曲
     *
     * @param user_ID
     * @param song_Name
     * @return
     */
    Boolean dislikeSong(String user_ID, String song_Name);

    /**
     * 搜索功能
     *
     * @return
     */
    List<String> searchSong(String searchValue);

    /**
     * 移除喜欢的歌曲
     *
     * @param song_ID
     * @param user_ID
     * @return
     */
    Boolean deleteLikeSong(String user_ID, String song_ID);
}
