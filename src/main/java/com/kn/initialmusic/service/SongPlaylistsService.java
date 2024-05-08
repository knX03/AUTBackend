package com.kn.initialmusic.service;

import com.kn.initialmusic.pojo.Song;
import com.kn.initialmusic.pojo.SongPlaylists;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SongPlaylistsService {

    /**
     * 查询所有歌单
     *
     * @return
     */
    List<SongPlaylists> selectAllPlaylists();

    /**
     * 检查歌单名是否存在
     *
     * @param playlist_Name
     * @return
     */
    Boolean checkPlaylistName(String playlist_Name);


    /**
     * 随机查询9条歌单
     *
     * @return
     */
    List<SongPlaylists> selectSongPlaylist();

    /**
     * 根据歌单的名字查询详情
     *
     * @param playlist_ID
     * @return
     */
    SongPlaylists selectDetailByID(String playlist_ID);

    /**
     * 查询用户创建的歌单
     *
     * @param user_ID
     * @return
     */
    List<SongPlaylists> selectCreatePlaylist(String user_ID);

    /**
     * 查询用户收藏的歌单
     *
     * @param user_ID
     * @return
     */
    List<SongPlaylists> selectLikePlaylist(String user_ID);

    /**
     * 查询歌单内容
     *
     * @param playlist_ID
     * @return
     */
    List<Song> selectSongByPlaylist(String playlist_ID);


    /**
     * 收藏歌单
     *
     * @param playlist_ID
     * @return
     */
    Boolean collectPlaylist(String playlist_ID, String user_ID);

    /**
     * 查询是否已收藏歌单
     *
     * @param playlist_ID
     * @param user_ID
     * @return
     */
    Boolean ifCollectPlaylist(String playlist_ID, String user_ID);

    /**
     * 修改歌单资料
     *
     * @param songPlaylists
     * @param playlist_ID
     * @return
     */
    Boolean changePlaylistInfo(SongPlaylists songPlaylists, String playlist_ID);

    /**
     * 创建歌单
     *
     * @return
     */
    Boolean createNewPlaylist(SongPlaylists songPlaylists);

    /**
     * 歌曲是否已经收藏至歌单
     *
     * @param playlist_ID
     * @param song_ID
     * @return
     */
    Boolean ifExistSong(String playlist_ID, String song_ID);

    /**
     * 将歌曲收藏至指定歌单
     *
     * @param playlist_ID
     * @param song_ID
     * @return
     */
    Boolean collectSongToPlaylist(String playlist_ID, String song_ID);

    /**
     * 删除指定歌单的歌曲
     *
     * @param playlist_ID
     * @param song_ID
     * @return
     */
    Boolean deleteSongByPlaylistID(String playlist_ID, String song_ID);

    /**
     * 删除歌单
     *
     * @param user_ID
     * @param playlist_ID
     * @return
     */
    Boolean deletePlaylistByUser(String user_ID, String playlist_ID);

    /**
     * 根据日期修改日推
     *
     * @return
     */
    Boolean changeDailyList();

    /**
     * 移除喜欢的歌单
     *
     * @param playlist_ID
     * @param user_ID
     * @return
     */
    Boolean deleteLikePlaylist(String playlist_ID, String user_ID);
}
