package com.kn.initialmusic.service;

import com.kn.initialmusic.pojo.Result;
import com.kn.initialmusic.pojo.Singer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SingerService {

    /**
     * 查询所有歌手
     *
     * @return 所有歌手列表
     */
    List<Singer> selectAllSingers();

    /**
     * 查询歌手的详情
     *
     * @param singer_ID 歌手ID
     * @return 对应的歌手对象
     */
    Singer selectDetailSinger(String singer_ID);

    /**
     * 筛选歌手
     *
     * @param langType   歌手语言
     * @param singerType 歌手类型
     * @param alphabet   歌手首字母
     * @return 筛选后的歌手列表
     */
    List<Singer> singerSelector(String langType, String singerType, String alphabet);

    /**
     * 判断用户是否是歌手
     *
     * @param user_ID 用户ID
     * @return 歌手信息
     */
    Result getSingerByUser(String user_ID);

    /**
     * 歌手信息退出
     *
     * @param singer_token 歌手token
     * @return 是否成功退出
     */
    Boolean sinLogOff(String singer_token);

    /**
     * 申请成为歌手
     *
     * @param singer 歌手对象
     * @return 是否申请成功
     */

    Result applySinger(Singer singer);

    /**
     * 查询歌手的粉丝数据
     *
     * @param singer_ID 歌手ID
     * @return 粉丝数据
     */
    Result selectArtistFansData(String singer_ID);

    /**
     * 查询歌手的作品数据
     *
     * @param singer_ID 歌手的ID
     * @return 作品数据
     */
    Result selectArtistWorkData(String singer_ID);


    /**
     * 查询歌手的歌曲数据
     *
     * @param singer_ID 歌手的ID
     * @return 歌曲数据
     */
    Result selectArtistSongData(String singer_ID);


    /**
     * 查询歌手的专辑数据
     *
     * @param singer_ID 歌手的ID
     * @return 专辑数据
     */
    Result selectArtistAlbumData(String singer_ID);
}
