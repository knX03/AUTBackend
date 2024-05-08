package com.kn.initialmusic.service;

import com.kn.initialmusic.pojo.Album;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AlbumService {
    /**
     * 查询所有专辑
     *
     * @return
     */
    List<Album> selectAllAlbum();


    /**
     * 随机查询9个专辑
     *
     * @return
     */
    List<Album> selectAlbumNine();

    /**
     * 创建专辑
     *
     * @param album
     * @return
     */
    Boolean createAlbum(Album album);

    /**
     * 检查专辑名称是否重复
     *
     * @param album_Name
     * @return
     */
    Boolean checkAlbumName(String album_Name);

    /**
     * 在singeralbum表保存数据
     *
     * @param singer_Name
     * @param album_Name
     * @return
     */
    Boolean creatSingerAlbum(String singer_Name, String album_Name);

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
     * 查询是否已收藏专辑
     *
     * @param album_ID
     * @param user_ID
     * @return
     */
    Boolean ifCollectAlbum(String album_ID, String user_ID);

    /**
     * 收藏专辑
     *
     * @param album_ID
     * @param user_ID
     * @return
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
     * @param singer_ID
     * @return
     */
    List<Album> getSingerAlbums(String singer_ID);
}
