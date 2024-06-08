package com.kn.initialmusic.service;

import com.kn.initialmusic.pojo.Album;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AlbumService {
    /**
     * 查询所有专辑
     *
     * @return 所有专辑列表
     */
    List<Album> selectAllAlbum();


    /**
     * 随机查询9个专辑
     *
     * @return 10个专辑列表
     */
    List<Album> TenRandomAlbum();

    /**
     * 创建专辑
     *
     * @param album 专辑对象
     * @return 是否创建成功
     */
    Boolean createAlbum(Album album);

    /**
     * 检查专辑名称是否重复
     *
     * @param album_Name 专辑名称
     * @return 是否重复
     */
    Boolean checkAlbumName(String album_Name);

    /**
     * 在singeralbum表保存数据
     *
     * @param singer_Name 歌手名称
     * @param album_Name  专辑名称
     * @return 专辑是否创建成功
     */
    Boolean creatSingerAlbum(String singer_Name, String album_Name);

    /**
     * 查询专辑详情
     *
     * @param album_ID 专辑ID
     * @return 专辑对象
     */
    Album selectDetailAlbum(String album_ID);

    /**
     * 查询用户收藏的专辑
     *
     * @param user_ID 用户ID
     * @return 用户收藏的专辑列表
     */
    List<Album> selectLikeAlbum(String user_ID);

    /**
     * 查询是否已收藏专辑
     *
     * @param album_ID 专辑ID
     * @param user_ID  用户ID
     * @return 用户是否收藏了专辑
     */
    Boolean ifCollectAlbum(String album_ID, String user_ID);

    /**
     * 收藏专辑
     *
     * @param album_ID 专辑ID
     * @param user_ID  用户ID
     * @return 是否收藏成功
     */
    Boolean collectAlbum(String album_ID, String user_ID);


    /**
     * 移除收藏的专辑
     *
     * @param album_ID 专辑ID
     * @param user_ID  用户ID
     * @return boolean值
     */
    Boolean deleteLikeAlbum(String user_ID, String album_ID);


    /**
     * 歌手的专辑
     *
     * @param singer_ID 歌手ID
     * @return 专辑列表
     */
    List<Album> getSingerAlbums(String singer_ID);


    /**
     * 用户的专辑
     *
     * @param user_ID 用户ID
     * @return 专辑列表
     */
    List<Album> getUserAlbums(String user_ID);
}
