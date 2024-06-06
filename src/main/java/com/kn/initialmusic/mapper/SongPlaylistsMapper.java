package com.kn.initialmusic.mapper;

import com.kn.initialmusic.pojo.PLTag;
import com.kn.initialmusic.pojo.Song;
import com.kn.initialmusic.pojo.SongPlaylists;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;

@Mapper
public interface SongPlaylistsMapper {

    /**
     * 查询所有歌单
     *
     * @return
     */
    List<SongPlaylists> selectAllPlaylists();

    /**
     * 查询id是否重复
     *
     * @param id
     * @return
     */
    String isRepeatedByID(String id);


    /**
     * 检查用户名是否存在
     *
     * @param playlist_Name
     * @return
     */
    String checkPlaylistName(String playlist_Name);

    /**
     * 随机查询9条歌单
     *
     * @return
     */
    List<SongPlaylists> selectNineRandomPlaylist();

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
     * @param user_ID
     * @return
     */
    int collectPlaylist(@Param("playlist_ID") String playlist_ID,
                        @Param("user_ID") String user_ID);

    /**
     * 查询歌单是否已收藏
     *
     * @param playlist_ID
     * @param user_ID
     * @return
     */
    String ifCollectPlaylist(@Param("playlist_ID") String playlist_ID,
                             @Param("user_ID") String user_ID);

    /**
     * 是否为自己创建的歌单
     *
     * @param playlist_ID 歌单ID
     * @param user_ID     用户ID
     * @return 是否
     */
    String ifMyPlaylist(@Param("playlist_ID") String playlist_ID,
                         @Param("user_ID") String user_ID);

    /**
     * 修改歌单资料
     *
     * @param songPlaylists
     * @return
     */
    int changePlaylistInfo(SongPlaylists songPlaylists);

    /**
     * 创建歌单
     *
     * @param songPlaylists
     * @return
     */
    int createNewPlaylist(SongPlaylists songPlaylists);

    /**
     * 保存歌单的标签
     *
     * @param playlist_ID 歌单ID
     * @param tag_ID      标签ID
     * @return 修改的数据库条数
     */
    int insertPlaylistTag(@Param("playlist_ID") String playlist_ID, @Param("tag_ID") String tag_ID);

    /**
     * 查询相应歌单的标签列表
     *
     * @param playlist_ID 歌单ID
     * @return 歌单的标签列表
     */
    List<PLTag> selectPlaylistTags(String playlist_ID);

    /**
     * 删除歌单的标签
     *
     * @param playlist_ID 歌单ID
     * @return
     */
    int deletePlaylistTag(String playlist_ID);

    /**
     * 歌曲是否已经收藏至歌单
     *
     * @param playlist_ID
     * @param song_ID
     * @return
     */
    String ifExistSong(@Param("playlist_ID") String playlist_ID,
                       @Param("song_ID") String song_ID);

    /**
     * 将歌曲收藏至指定歌单
     *
     * @param playlist_ID
     * @param song_ID
     * @return
     */
    int collectSongToPlaylist(@Param("playlist_ID") String playlist_ID,
                              @Param("song_ID") String song_ID);

    /**
     * 删除指定歌单的歌曲
     *
     * @param playlist_ID
     * @param song_ID
     * @return
     */
    int deleteSongByPlaylistID(@Param("playlist_ID") String playlist_ID,
                               @Param("song_ID") String song_ID);

    /**
     * 删除歌单
     *
     * @param user_ID
     * @param playlist_ID
     * @return
     */
    void deletePlaylistByUser(@Param("user_ID") String user_ID,
                              @Param("playlist_ID") String playlist_ID);


    /**
     * 获取日推的歌曲
     */
    List<String> getDailySong();

    /**
     * 根据日期修改日推的歌曲
     *
     * @return
     */

    int changeDailyList(@Param("song_ID") String song_ID, @Param("id") int id);


    /**
     * 根据日期修改日推的信息
     *
     * @return
     */
    int changeDailyListInfo(@Param("day") String day,
                            @Param("create_time") Date create_time,
                            @Param("cover") String cover);

    /**
     * 移除喜欢的歌单
     *
     * @param playlist_ID
     * @param user_ID
     * @return
     */
    int deleteLikePlaylist(@Param("playlist_ID") String playlist_ID,
                           @Param("user_ID") String user_ID);

    /**
     * 根据歌单标签查询其他相同标签的歌单
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
    List<PLTag> getAllPLTag();
}
