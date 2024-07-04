package com.kn.initialmusic.service.Impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.kn.initialmusic.mapper.AlbumMapper;
import com.kn.initialmusic.pojo.Album;
import com.kn.initialmusic.pojo.Result;
import com.kn.initialmusic.pojo.SongPlaylists;
import com.kn.initialmusic.pojo.User;
import com.kn.initialmusic.service.AlbumService;
import com.kn.initialmusic.service.GenerateIDService;
import com.kn.initialmusic.util.UserHolder;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.kn.initialmusic.controller.Code.SUCCESS;
import static com.kn.initialmusic.util.RedisConstants.CACHE_ALALL_KEY;
import static com.kn.initialmusic.util.RedisConstants.CACHE_SPALL_KEY;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private GenerateIDService generateIDService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public Result selectAllAlbum() {
        Result result = new Result();
        String ALJson = stringRedisTemplate.opsForValue().get(CACHE_ALALL_KEY);
        if (ALJson != null) {
            //Json转List
            JSONArray array = JSONUtil.parseArray(ALJson);
            List<Album> albums = JSONUtil.toList(array, Album.class);
            result.setCode(SUCCESS);
            result.setData(albums);
            return result;
        }
        List<Album> albums = albumMapper.selectAllAlbum();
        stringRedisTemplate.opsForValue().set(CACHE_ALALL_KEY, JSONUtil.toJsonStr(albums));
        result.setCode(SUCCESS);
        result.setData(albums);
        return result;
    }

    @Override
    public List<Album> TenRandomAlbum() {
        Result result = selectAllAlbum();
        List<Album> Albums = (List<Album>) result.getData();
        List<Album> als = new ArrayList<>();
        HashSet<Integer> set = new HashSet<>();
        while (als.size() <= 9) {
            int index = (int) (Math.random() * Albums.size());
            if (set.add(index)) {
                als.add(Albums.get(index));
            }
        }
//        return albumMapper.selectTenRandomAlbum();
        return als;
    }

    @Override
    public String createAlbum(Album album) {
        String albumID = generateIDService.generateAlbumID();
        album.setAlbum_ID(albumID);
        int flag = albumMapper.creatAlbum(album);
        if (flag > 0) {
            return albumID;
        } else {
            return null;
        }
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

    @Override
    public List<Album> getUserAlbums(String singer_ID) {
        List<Album> userAlbums = albumMapper.getUserAlbums(singer_ID);
        return userAlbums;
    }


}
