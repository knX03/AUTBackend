package com.kn.initialmusic.service.Impl;

import com.kn.initialmusic.mapper.AlbumMapper;
import com.kn.initialmusic.pojo.Album;
import com.kn.initialmusic.service.AlbumService;
import com.kn.initialmusic.service.GenerateIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private GenerateIDService generateIDService;


    @Override
    public List<Album> selectAllAlbum() {
        List<Album> albums = albumMapper.selectAllAlbum();
        return albums;
    }

    @Override
    public List<Album> selectAlbumNine() {
        return albumMapper.selectAlbumNine();
    }

    @Override
    public Boolean createAlbum(Album album) {
        String albumID = generateIDService.generateAlbumID();
        album.setAlbum_ID(albumID);
        try {
            albumMapper.creatAlbum(album);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Boolean checkAlbumName(String album_Name) {
        String albumName = albumMapper.checkAlbumName(album_Name);
        return albumName != null;
    }

    @Override
    public Boolean creatSingerAlbum(String singer_Name, String album_Name) {
        try {
            albumMapper.creatSingerAlbum(singer_Name, album_Name);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Album selectDetailAlbum(String album_ID) {
        return albumMapper.selectDetailAlbum(album_ID);
    }

    @Override
    public List<Album> selectLikeAlbum(String user_ID) {
        return albumMapper.selectLikeAlbum(user_ID);
    }

    @Override
    public Boolean ifCollectAlbum(String album_ID, String user_ID) {
        String flag = null;
        flag = albumMapper.ifCollectAlbum(album_ID, user_ID);
        return flag != null;
    }

    @Override
    public Boolean collectAlbum(String album_ID, String user_ID) {
        int flag = albumMapper.collectAlbum(album_ID, user_ID);
        return flag > 0;
    }

    @Override
    public Boolean deleteLikeAlbum(String user_ID, String album_ID) {
        int flag = albumMapper.deleteLikeAlbum(user_ID, album_ID);
        return flag > 0;
    }

    @Override
    public List<Album> getSingerAlbums(String singer_ID) {
        return albumMapper.getSingerAlbums(singer_ID);
    }
}
