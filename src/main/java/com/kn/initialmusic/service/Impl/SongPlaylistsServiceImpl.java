package com.kn.initialmusic.service.Impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.kn.initialmusic.mapper.SongPlaylistsMapper;
import com.kn.initialmusic.pojo.PLTag;
import com.kn.initialmusic.pojo.Result;
import com.kn.initialmusic.pojo.Song;
import com.kn.initialmusic.pojo.SongPlaylists;
import com.kn.initialmusic.service.GenerateIDService;
import com.kn.initialmusic.service.SongPlaylistsService;
import com.kn.initialmusic.util.songUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.kn.initialmusic.controller.Code.SUCCESS;
import static com.kn.initialmusic.util.RedisConstants.*;

@Service
public class SongPlaylistsServiceImpl implements SongPlaylistsService {

    @Autowired
    private SongPlaylistsMapper songPlaylistsMapper;
    @Autowired
    private GenerateIDService generateIDService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final String DAILY_COVER = "src/photos/Daily/";
    private static final int DAILYLIST_ID = 1000;


    @Override
    public Result selectAllPlaylists() {
        Result result = new Result();
        //获取缓存
        String SPJson = stringRedisTemplate.opsForValue().get(CACHE_SPALL_KEY);
        if (SPJson != null) {
            //Json转List
            JSONArray array = JSONUtil.parseArray(SPJson);
            List<SongPlaylists> songPlaylists = JSONUtil.toList(array, SongPlaylists.class);
            result.setCode(SUCCESS);
            result.setData(songPlaylists);
            return result;
        }
        List<SongPlaylists> songPlaylists = songPlaylistsMapper.selectAllPlaylists();
        stringRedisTemplate.opsForValue().set(CACHE_SPALL_KEY, JSONUtil.toJsonStr(songPlaylists));
        result.setCode(SUCCESS);
        result.setData(songPlaylists);
        return result;
    }

    @Override
    public Boolean checkPlaylistName(String playlist_Name) {
        String checkFlag = songPlaylistsMapper.checkPlaylistName(playlist_Name);
        return checkFlag != null;
    }

    @Override
    //todo 考虑不从全部歌单随机获取
    public List<SongPlaylists> NineRandomPlaylist() {
        Result result = selectAllPlaylists();
        List<SongPlaylists> songPlaylists = (List<SongPlaylists>) result.getData();
        List<SongPlaylists> playlists = new ArrayList<>();
        HashSet<Integer> set = new HashSet<>();
        while (playlists.size() <= 8) {
            int index = (int) (Math.random() * songPlaylists.size());
            if (set.add(index)) {
                playlists.add(songPlaylists.get(index));
            }
        }
//       List<SongPlaylists> songPlaylists = songPlaylistsMapper.selectNineRandomPlaylist();
        return playlists;
    }

    @Override
    public Result selectDetailByID(String playlist_ID) {
        Result result = new Result();
        String key_SPDetail = CACHE_SP_KEY + playlist_ID;
        //1.获取缓存
        String spJson = stringRedisTemplate.opsForValue().get(key_SPDetail);
        //2.缓存是否存在
        if (spJson != null) {
            SongPlaylists songPlaylists = JSONUtil.toBean(spJson, SongPlaylists.class);
            stringRedisTemplate.expire(key_SPDetail, CACHE_SP_KEY_TTL, TimeUnit.MINUTES);
            result.setCode(SUCCESS);
            result.setData(songPlaylists);
            return result;
        }
        SongPlaylists songPlaylist = songPlaylistsMapper.selectDetailByID(playlist_ID);
        List<PLTag> plTags = songPlaylistsMapper.selectPlaylistTags(playlist_ID);
        String[] tags = new String[plTags.size()];
        for (int i = 0; i < plTags.size(); i++) {
            tags[i] = plTags.get(i).getTag_id();
        }
        songPlaylist.setPlaylist_Tag(tags);
        //3.存入缓存
        stringRedisTemplate.opsForValue().set(key_SPDetail, JSONUtil.toJsonStr(songPlaylist),
                CACHE_SP_KEY_TTL, TimeUnit.MINUTES);
//        stringRedisTemplate.expire(key_SPDetail, CACHE_SP_KEY_TTL, TimeUnit.MINUTES);
        result.setCode(SUCCESS);
        result.setData(songPlaylist);
        return result;
    }

    @Override
    public List<PLTag> selectPlaylistTags(String playlist_ID) {
        List<PLTag> plTags = songPlaylistsMapper.selectPlaylistTags(playlist_ID);
        return plTags;
    }

    @Override
    public Result selectCreatePlaylist(String user_ID) {
        Result result = new Result();
        String key_user_SP = CACHE_USERSP_KEY + user_ID;
        String userSPJson = stringRedisTemplate.opsForValue().get(key_user_SP);
        if (userSPJson != null) {
            //Json转List
            JSONArray array = JSONUtil.parseArray(userSPJson);
            List<SongPlaylists> songPlaylists = JSONUtil.toList(array, SongPlaylists.class);
            stringRedisTemplate.expire(key_user_SP, CACHE_USERSP_KEY_TTL, TimeUnit.MINUTES);
            result.setCode(SUCCESS);
            result.setData(songPlaylists);
            return result;
        }
        List<SongPlaylists> songPlaylists = songPlaylistsMapper.selectCreatePlaylist(user_ID);
        stringRedisTemplate.opsForValue().set(key_user_SP, JSONUtil.toJsonStr(songPlaylists),
                CACHE_USERSP_KEY_TTL, TimeUnit.MINUTES);
//        stringRedisTemplate.expire(key_user_SP, CACHE_USERSP_KEY_TTL, TimeUnit.MINUTES);
        result.setCode(SUCCESS);
        result.setData(songPlaylists);
        return result;
    }

    @Override
    public Result selectLikePlaylist(String user_ID) {
        Result result = new Result();
        String key_user_LISP = CACHE_USERLISP_KEY + user_ID;
        String userSPJson = stringRedisTemplate.opsForValue().get(key_user_LISP);
        if (userSPJson != null) {
            //Json转List
            JSONArray array = JSONUtil.parseArray(userSPJson);
            List<SongPlaylists> songPlaylists = JSONUtil.toList(array, SongPlaylists.class);
            stringRedisTemplate.expire(key_user_LISP, CACHE_USERSP_KEY_TTL, TimeUnit.MINUTES);
            result.setCode(SUCCESS);
            result.setData(songPlaylists);
            return result;
        }
        List<SongPlaylists> songPlaylists = songPlaylistsMapper.selectLikePlaylist(user_ID);
        stringRedisTemplate.opsForValue().set(key_user_LISP, JSONUtil.toJsonStr(songPlaylists),
                CACHE_USERSP_KEY_TTL, TimeUnit.MINUTES);
//        stringRedisTemplate.expire(key_user_LISP, CACHE_USERSP_KEY_TTL, TimeUnit.MINUTES);
        result.setCode(SUCCESS);
        result.setData(songPlaylists);
        return result;

    }

    @Override
    public Result selectSongByPlaylist(String playlist_ID) {
        Result result = new Result();
        String key_SP_song = CACHE_SP_SONG_KEY + playlist_ID;
        String SP_song_JSON = stringRedisTemplate.opsForValue().get(key_SP_song);
        if (SP_song_JSON != null) {
            //Json转List
            JSONArray array = JSONUtil.parseArray(SP_song_JSON);
            List<Song> songs = JSONUtil.toList(array, Song.class);
            stringRedisTemplate.expire(key_SP_song, CACHE_SP_KEY_TTL, TimeUnit.MINUTES);
            result.setCode(SUCCESS);
            result.setData(songs);
            return result;
        }
        List<Song> songs = songPlaylistsMapper.selectSongByPlaylist(playlist_ID);
        songUtil.formatSDirectory(songs);
        stringRedisTemplate.opsForValue().set(key_SP_song, JSONUtil.toJsonStr(songs),
                CACHE_SP_KEY_TTL, TimeUnit.MINUTES);
        result.setCode(SUCCESS);
        result.setData(songs);
        return result;
    }

    @Override
    public Boolean collectPlaylist(String playlist_ID, String user_ID) {
        int flag = songPlaylistsMapper.collectPlaylist(playlist_ID, user_ID);
        return flag > 0;
    }

    @Override
    public Boolean ifCollectPlaylist(String playlist_ID, String user_ID) {
        String flag = songPlaylistsMapper.ifCollectPlaylist(playlist_ID, user_ID);
        return flag != null;
    }

    @Override
    public Boolean ifMyPlaylist(String playlist_ID, String user_ID) {
        String flag = songPlaylistsMapper.ifMyPlaylist(playlist_ID, user_ID);
        return flag != null;
    }

    @Override
    @Transactional
    public Boolean changePlaylistInfo(SongPlaylists songPlaylists) {
        String key_SP = CACHE_SP_KEY + songPlaylists.getPlaylist_ID();

        int flag = songPlaylistsMapper.changePlaylistInfo(songPlaylists);
        String playlistId = songPlaylists.getPlaylist_ID();
        String[] playlistTags = songPlaylists.getPlaylist_Tag();
        songPlaylistsMapper.deletePlaylistTag(playlistId);
        for (String playlistTag : playlistTags) {
            songPlaylistsMapper.insertPlaylistTag(playlistId, playlistTag);
        }

        //删除缓存
        stringRedisTemplate.delete(key_SP);
        return flag > 0;
    }

    @Override
    public Boolean createNewPlaylist(SongPlaylists songPlaylists) {
        String playlist_ID = generateIDService.generateSongPlaylistsID();
        songPlaylists.setPlaylist_ID(playlist_ID);
        int flag = songPlaylistsMapper.createNewPlaylist(songPlaylists);
        String[] playlistTags = songPlaylists.getPlaylist_Tag();
        for (String playlistTag : playlistTags) {
            songPlaylistsMapper.insertPlaylistTag(playlist_ID, playlistTag);
        }
        return flag > 0;
    }

    @Override
    public Boolean ifExistSong(String playlist_ID, String song_ID) {
        String song = songPlaylistsMapper.ifExistSong(playlist_ID, song_ID);
        return song != null;
    }

    @Override
    public Boolean collectSongToPlaylist(String playlist_ID, String song_ID) {
        int flag = songPlaylistsMapper.collectSongToPlaylist(playlist_ID, song_ID);

        return flag > 0;
    }

    @Override
    public Boolean deleteSongByPlaylistID(String playlist_ID, String song_ID) {
        int flag = songPlaylistsMapper.deleteSongByPlaylistID(playlist_ID, song_ID);
        return flag > 0;
    }

    @Override
    public Boolean deletePlaylistByUser(String user_ID, String playlist_ID) {
        songPlaylistsMapper.deletePlaylistByUser(user_ID, playlist_ID);
        return true;
    }

    @Override
    public Boolean changeDailyList(String playlist_ID) {
        String key_SP_song = CACHE_SP_SONG_KEY + playlist_ID;
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;  // 月份从0开始，需要加1
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        java.sql.Date date = new Date(System.currentTimeMillis());
        String daily = month + "/" + day;
        String cover = DAILY_COVER + day + ".png";
        int temp = 1;
        int flag = songPlaylistsMapper.changeDailyListInfo(daily, date, cover);
        if (flag != 0) {
            List<String> dailySong = songPlaylistsMapper.getDailySong();
            for (String song_ID : dailySong) {
                songPlaylistsMapper.changeDailyList(song_ID, temp);
                temp++;
            }
            stringRedisTemplate.delete(key_SP_song);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public Boolean deleteLikePlaylist(String playlist_ID, String user_ID) {
        int flag = songPlaylistsMapper.deleteLikePlaylist(playlist_ID, user_ID);
        return flag > 0;
    }

    @Override
    public List<SongPlaylists> selectPlaylistByTag(String playlist_tag) {
        return songPlaylistsMapper.selectPlaylistByTag(playlist_tag);
    }

    @Override
    public Result getAllPLTag() {
        Result result = new Result();
        //获取缓存
        String tagJson = stringRedisTemplate.opsForValue().get(CACHE_PLTAG_KEY);
        if (tagJson != null) {
            //Json转List
            JSONArray array = JSONUtil.parseArray(tagJson);
            List<PLTag> allPLTag = JSONUtil.toList(array, PLTag.class);

            result.setCode(SUCCESS);
            result.setData(allPLTag);
            return result;
        }
        //缓存不存在
        List<PLTag> allPLTag = songPlaylistsMapper.getAllPLTag();
        stringRedisTemplate.opsForValue().set(CACHE_PLTAG_KEY, JSONUtil.toJsonStr(allPLTag));
        result.setCode(SUCCESS);
        result.setData(allPLTag);
        return result;
    }
}
