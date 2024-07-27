package com.kn.initialmusic.service;

import com.kn.initialmusic.pojo.PLTag;
import com.kn.initialmusic.pojo.Result;
import com.kn.initialmusic.pojo.Song;
import com.kn.initialmusic.pojo.SongPlaylists;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SongPlaylistsService {

    /**
     * 查询所有歌单
     *
     * @return 所有歌单列表
     */
    Result selectAllPlaylists();

    /**
     * 检查歌单名是否存在
     *
     * @param playlist_Name 歌单名称
     * @return 是否存在
     */
    Boolean checkPlaylistName(String playlist_Name);


    /**
     * 随机查询9条歌单
     *
     * @return 随机9个歌单列表
     */
    List<SongPlaylists> NineRandomPlaylist();

    /**
     * 根据歌单的ID查询详情
     *
     * @param playlist_ID 歌单ID
     * @return 相应的歌单对象
     */
    Result selectDetailByID(String playlist_ID);

    /**
     * 查询歌单的标签列表
     *
     * @param playlist_ID 歌单ID
     * @return 对应歌单的标签列表
     */
    List<PLTag> selectPlaylistTags(String playlist_ID);

    /**
     * 查询用户创建的歌单
     *
     * @param user_ID 用户ID
     * @return 相应用户创建的歌单列表
     */
    Result selectCreatePlaylist(String user_ID);

    /**
     * 查询用户收藏的歌单
     *
     * @param user_ID 用户ID
     * @return 用户收藏的歌单列表
     */
    Result selectLikePlaylist(String user_ID);

    /**
     * 查询歌单内容
     *
     * @param playlist_ID 歌单ID
     * @return 相应歌单的歌曲列表
     */
    Result selectSongByPlaylist(String playlist_ID);


    /**
     * 收藏歌单
     *
     * @param playlist_ID 歌单ID
     * @return 是否收藏成功
     */
    Boolean collectPlaylist(String playlist_ID, String user_ID);

    /**
     * 查询是否已收藏歌单
     *
     * @param playlist_ID 歌单ID
     * @param user_ID     用户ID
     * @return 用户所收藏的歌单列表
     */
    Boolean ifCollectPlaylist(String playlist_ID, String user_ID);

    /**
     * 是否为自己创建的歌单
     *
     * @param playlist_ID 歌单ID
     * @param user_ID     用户ID
     * @return 是否
     */
    Boolean ifMyPlaylist(String playlist_ID, String user_ID);

    /**
     * 修改歌单资料
     *
     * @param songPlaylists 修改后的歌单对象
     * @return 是否修改成功
     */
    Result changePlaylistInfo(SongPlaylists songPlaylists);

    /**
     * @param songPlaylists 待创建的歌单对象
     * @return 是否创建成功
     */
    Result createNewPlaylist(SongPlaylists songPlaylists);

    /**
     * 歌曲是否已经收藏至歌单
     *
     * @param playlist_ID 歌单ID
     * @param song_ID     歌曲ID
     * @return 歌曲是否已经收藏至歌单
     */
    Boolean ifExistSong(String playlist_ID, String song_ID);

    /**
     * 将歌曲收藏至指定歌单
     *
     * @param playlist_ID 歌单ID
     * @param song_ID     歌曲ID
     * @return 是否收藏成功
     */
    Boolean collectSongToPlaylist(String playlist_ID, String song_ID);

    /**
     * 删除指定歌单的歌曲
     *
     * @param playlist_ID 歌单ID
     * @param song_ID     歌曲ID
     * @return 是否删除成功
     */
    Boolean deleteSongByPlaylistID(String playlist_ID, String song_ID);

    /**
     * 删除歌单
     *
     * @param user_ID     用户ID
     * @param playlist_ID 歌单ID
     * @return 用户是否删除歌单
     */
    Boolean deletePlaylistByUser(String user_ID, String playlist_ID);

    /**
     * 根据日期修改日推
     *
     * @return 是否修改日推成功
     */
    Boolean changeDailyList(String playlist_ID);

    /**
     * 移除喜欢的歌单
     *
     * @param playlist_ID 歌单ID
     * @param user_ID     用户ID
     * @return 是否取消收藏歌单
     */
    Boolean deleteLikePlaylist(String playlist_ID, String user_ID);

    /**
     * 根据歌单标签查询
     *
     * @param playlist_tag 歌单标签
     * @return 歌单列表
     */
    List<SongPlaylists> selectPlaylistByTag(String playlist_tag);

    /**
     * 查询所有歌单标签
     *
     * @return 歌单标签List
     */
    Result getAllPLTag();
}
