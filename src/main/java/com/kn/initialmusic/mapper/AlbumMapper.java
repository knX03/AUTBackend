package com.kn.initialmusic.mapper;

import com.kn.initialmusic.pojo.Album;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AlbumMapper {

    /**
     * 查询所有专辑
     *
     * @return
     */
    List<Album> selectAllAlbum();

    /**
     * 查询id是否重复
     *
     * @param id
     * @return
     */
    String isRepeatedByID(String id);

    /**
     * 随机查询9个专辑
     *
     * @return
     */
    List<Album> selectTenRandomAlbum();

    /**
     * 创建专辑
     *
     * @param album
     * @return
     */
    void creatAlbum(Album album);

    /**
     * 创建歌手与专辑的关系
     *
     * @param singer_Name
     * @param album_Name
     */
    void creatSingerAlbum(@Param("singer_Name") String singer_Name, @Param("album_Name") String album_Name);

    /**
     * 检查专辑名称是否重复
     *
     * @param album_Name
     * @return
     */
    String checkAlbumName(String album_Name);

    /**
     * 查询专辑详情
     *
     * @param album_ID
     * @return
     */
    Album selectDetailAlbum(String album_ID);

    /**
     * 查询用户收藏的专辑
     *
     * @param user_ID
     * @return
     */
    List<Album> selectLikeAlbum(String user_ID);

    /**
     * 查询专辑是否已收藏
     *
     * @param album_ID
     * @param user_ID
     * @return
     */

    String ifCollectAlbum(@Param("album_ID") String album_ID, @Param("user_ID") String user_ID);

    /**
     * 收藏专辑
     *
     * @param album_ID 专辑ID
     * @param user_ID  用户ID
     * @return 操作的数据数
     */
    int collectAlbum(@Param("album_ID") String album_ID, @Param("user_ID") String user_ID);

    /**
     * 移除收藏的专辑
     *
     * @param album_ID 专辑ID
     * @param user_ID  用户ID
     * @return 操作的数据数
     */
    int deleteLikeAlbum(@Param("user_ID") String user_ID, @Param("album_ID") String album_ID);

    /**
     * @param singer_ID
     * @return
     */
    List<Album> getSingerAlbums(String singer_ID);
}
