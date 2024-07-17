package com.kn.initialmusic.service;

import com.kn.initialmusic.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class GenerateIDService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SongMapper songMapper;
    @Autowired
    private AlbumMapper albumMapper;
    @Autowired
    private SongPlaylistsMapper songPlaylistsMapper;
    @Autowired
    private SingerMapper singerMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private MessageMapper messageMapper;


    public String generateUserID() {
        String userId = null;
        userId = String.valueOf((int) ((Math.random() * 9 + 1) * 10000));
        String repeatedByID = userMapper.isRepeatedByID(userId);
        while (repeatedByID != null) {
            userId = String.valueOf((int) ((Math.random() * 9 + 1) * 10000));
            repeatedByID = userMapper.isRepeatedByID(userId);
        }
        return userId;
    }

    public String generateSongID() {
        String songId = null;
        songId = String.valueOf((int) ((Math.random() * 9 + 1) * 10000));
        String repeatedByID = songMapper.isRepeatedByID(songId);
        while (repeatedByID != null) {
            songId = String.valueOf((int) ((Math.random() * 9 + 1) * 10000));
            repeatedByID = songMapper.isRepeatedByID(songId);
        }
        return songId;

    }

    public String generateAlbumID() {
        String albumId = null;
        albumId = String.valueOf((int) ((Math.random() * 9 + 1) * 10000));
        String repeatedByID = albumMapper.isRepeatedByID(albumId);
        while (repeatedByID != null) {
            albumId = String.valueOf((int) ((Math.random() * 9 + 1) * 10000));
            repeatedByID = albumMapper.isRepeatedByID(albumId);
        }
        return albumId;

    }

    public String generateSongPlaylistsID() {
        String SongPlaylistsID = null;
        SongPlaylistsID = String.valueOf((int) ((Math.random() * 9 + 1) * 10000));
        String repeatedByID = songPlaylistsMapper.isRepeatedByID(SongPlaylistsID);
        while (repeatedByID != null) {
            SongPlaylistsID = String.valueOf((int) ((Math.random() * 9 + 1) * 10000));
            repeatedByID = songPlaylistsMapper.isRepeatedByID(SongPlaylistsID);
        }
        return SongPlaylistsID;

    }

    public String generateSingerID() {
        String SingerID = null;
        SingerID = String.valueOf((int) ((Math.random() * 9 + 1) * 10000));
        String repeatedByID = singerMapper.isRepeatedByID(SingerID);
        while (repeatedByID != null) {
            SingerID = String.valueOf((int) ((Math.random() * 9 + 1) * 10000));
            repeatedByID = singerMapper.isRepeatedByID(SingerID);
        }
        return SingerID;

    }

    public String generateAdminID() {
        String AdminID = null;
        AdminID = String.valueOf((int) ((Math.random() * 9 + 1) * 10000));
        String repeatedByID = adminMapper.isRepeatedByID(AdminID);
        while (repeatedByID != null) {
            AdminID = String.valueOf((int) ((Math.random() * 9 + 1) * 10000));
            repeatedByID = adminMapper.isRepeatedByID(AdminID);
        }
        return AdminID;
    }

}
