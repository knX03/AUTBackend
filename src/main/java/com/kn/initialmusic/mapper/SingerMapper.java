package com.kn.initialmusic.mapper;

import com.kn.initialmusic.pojo.Album;
import com.kn.initialmusic.pojo.Result;
import com.kn.initialmusic.pojo.Singer;
import com.kn.initialmusic.pojo.Song;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SingerMapper {
    /**
     * 查询id是否重复
     *
     * @param id
     * @return
     */
    String isRepeatedByID(String id);


    /**
     * 查询所有歌手
     *
     * @return
     */
    List<Singer> selectAllSingers();

    /**
     * 查询歌手的详情
     *
     * @param singer_ID
     * @return
     */
    Singer selectDetailSinger(String singer_ID);


    /**
     * @param langType   歌手语言
     * @param singerType 歌手类型
     * @param alphabet   歌手首字母
     * @return
     */
    List<Singer> singerSelector(@Param("langType") String langType,
                                @Param("singerType") String singerType,
                                @Param("alphabet") String alphabet);

    /**
     * 用户是否是歌手
     *
     * @param user_ID 用户ID
     * @return 歌手信息
     */
    Singer getSingerByUser(String user_ID);

    /**
     * 申请成为歌手
     *
     * @param singer 歌手对象
     * @return 是否申请成功
     */

    int applySinger(Singer singer);

    /**
     * 查询歌手的粉丝数量
     *
     * @param singer_ID 歌手ID
     * @return 粉丝数据
     */
    int selectArtistFansNum(String singer_ID);
    //    Map<String, Integer> aselectArtistsFansNum(String singer_ID);

    /**
     * 查询歌手的作品数据
     *
     * @param singer_ID 歌手的ID
     * @return 作品数据
     */
    List<Integer> selectArtistWorkData(String singer_ID);


    /**
     * 查询歌手的歌曲数据
     *
     * @param singer_ID 歌手的ID
     * @return 歌曲数据
     */
    List<Song> selectArtistSongData(String singer_ID);

    /**
     * 查询歌手的专辑数据
     *
     * @param singer_ID 歌手的ID
     * @return 专辑数据
     */
    List<Album> selectArtistAlbumData(String singer_ID);
}
