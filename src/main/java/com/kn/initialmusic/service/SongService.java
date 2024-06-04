package com.kn.initialmusic.service;

import com.kn.initialmusic.pojo.PageBean;
import com.kn.initialmusic.pojo.Song;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.util.List;

@Transactional
public interface SongService {

    /**
     * 创建新歌曲
     *
     * @param song 待保存的歌曲对象
     * @return 是否保存成功
     */
    Boolean saveSong(Song song);

    /**
     * 查询是否已经存储歌曲
     *
     * @param songDirectory 歌曲存储路径
     * @return 是否已经存在
     */
    Boolean ifExistBySongDirectory(String songDirectory);

    /**
     * 查询相关专辑的歌曲
     *
     * @param album_ID 专辑ID
     * @return 相应专辑的歌曲列表
     */
    List<Song> selectSongByAlbum(String album_ID);

    /**
     * 查询相关歌手的歌曲
     *
     * @param singer_ID 歌手ID
     * @return 相应歌手的歌曲列表
     */
    List<Song> selectSongBySinger(String singer_ID);

    /**
     * 查询用户喜欢的歌曲
     *
     * @param user_ID 用户ID
     * @return 用户喜欢的歌曲列表
     */
    PageBean<Song> selectSongByUser(String user_ID, Integer currentPage, Integer pageSize);

    /**
     * 查询用户喜欢的歌曲
     *
     * @param user_ID 用户ID
     * @return 用户喜欢的歌曲列表
     */
    List<String> selectLikeSong(String user_ID);

    /**
     * 喜欢歌曲
     *
     * @param user_ID 用户ID
     * @param song_ID 歌曲ID
     * @return 是否将歌曲添加至我喜欢
     */
    Boolean likeSong(String user_ID, String song_ID);


    /**
     * 搜索功能
     *
     * @param searchValue 搜索输入的值
     * @return 匹配的歌曲列表
     */
    List<String> searchSong(String searchValue);

    /**
     * 移除喜欢的歌曲
     *
     * @param song_ID 歌曲ID
     * @param user_ID 用户ID
     * @return 是否将歌曲从我喜欢中移除
     */
    Boolean deleteLikeSong(String user_ID, String song_ID);
}
