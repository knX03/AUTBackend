package com.kn.initialmusic.mapper;

import com.kn.initialmusic.pojo.Song;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SongMapper {

    /**
     * 保存歌曲
     *
     * @param song
     */
    int saveSong(Song song);


    /**
     * 查询id是否重复
     *
     * @param id
     * @return
     */
    String isRepeatedByID(String id);

    /**
     * 查询歌曲是否存在
     *
     * @param directory
     * @return
     */
    String ifExistBySongDirectory(String directory);

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
     * @param user_ID 用户ID
     * @param begin   当前页
     * @param size    当前页数据
     * @return 分页查询的歌曲
     */
    List<Song> selectSongByUser(@Param("user_ID") String user_ID, @Param("begin") Integer begin, @Param("size") Integer size);

    /**
     * 用户喜欢歌曲总数
     *
     * @param user_ID 用户ID
     * @return
     */
    int selectTotalCount(String user_ID);

    /**
     * 查询用户喜欢的歌曲
     *
     * @param user_ID
     * @return
     */
    List<String> selectLikeSong(String user_ID);


    /**
     * 搜索全部
     *
     * @param searchValue
     * @return
     */
    List<List<String>> searchAll(String searchValue);

    /**
     * 喜欢歌曲
     *
     * @param user_ID
     * @param song_ID
     * @return
     */
    int likeSong(@Param("user_ID") String user_ID, @Param("song_ID") String song_ID);

    /**
     * 移除歌曲
     *
     * @param song_ID
     * @param user_ID
     * @return
     */
    int deleteLikeSong(@Param("user_ID") String user_ID, @Param("song_ID") String song_ID);
}
