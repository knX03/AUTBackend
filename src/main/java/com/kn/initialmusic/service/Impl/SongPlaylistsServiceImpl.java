package com.kn.initialmusic.service.Impl;

import com.kn.initialmusic.mapper.SongPlaylistsMapper;
import com.kn.initialmusic.pojo.PLTag;
import com.kn.initialmusic.pojo.Song;
import com.kn.initialmusic.pojo.SongPlaylists;
import com.kn.initialmusic.service.GenerateIDService;
import com.kn.initialmusic.service.SongPlaylistsService;
import com.kn.initialmusic.util.songUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class SongPlaylistsServiceImpl implements SongPlaylistsService {

    @Autowired
    private SongPlaylistsMapper songPlaylistsMapper;
    @Autowired
    private GenerateIDService generateIDService;

    private static final String DAILY_COVER = "src/photos/Daily/";
    private static final int DAILYLIST_ID = 1000;


    @Override
    public List<SongPlaylists> selectAllPlaylists() {
        List<SongPlaylists> songPlaylists = songPlaylistsMapper.selectAllPlaylists();
        return songPlaylists;
    }

    @Override
    public Boolean checkPlaylistName(String playlist_Name) {
        String checkFlag = songPlaylistsMapper.checkPlaylistName(playlist_Name);
        return checkFlag != null;
    }

    @Override
    public List<SongPlaylists> NineRandomPlaylist() {
        List<SongPlaylists> songPlaylists = songPlaylistsMapper.selectNineRandomPlaylist();

        return songPlaylists;
    }

    @Override
    public SongPlaylists selectDetailByID(String playlist_ID) {
        SongPlaylists songPlaylist = songPlaylistsMapper.selectDetailByID(playlist_ID);
        List<PLTag> plTags = songPlaylistsMapper.selectPlaylistTags(playlist_ID);
        String[] tags = new String[plTags.size()];
        for (int i = 0; i < plTags.size(); i++) {
            tags[i] = plTags.get(i).getTag_id();
        }
        songPlaylist.setPlaylist_Tag(tags);
        return songPlaylist;
    }

    @Override
    public List<PLTag> selectPlaylistTags(String playlist_ID) {
        List<PLTag> plTags = songPlaylistsMapper.selectPlaylistTags(playlist_ID);
        return plTags;
    }

    @Override
    public List<SongPlaylists> selectCreatePlaylist(String user_ID) {
        List<SongPlaylists> songPlaylists = songPlaylistsMapper.selectCreatePlaylist(user_ID);

        return songPlaylists;
    }

    @Override
    public List<SongPlaylists> selectLikePlaylist(String user_ID) {

        List<SongPlaylists> songPlaylists = songPlaylistsMapper.selectLikePlaylist(user_ID);

        return songPlaylists;
    }

    @Override
    public List<Song> selectSongByPlaylist(String playlist_ID) {
        List<Song> songs = songPlaylistsMapper.selectSongByPlaylist(playlist_ID);
        songUtil.formatSDirectory(songs);
        return songs;
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
    public Boolean changePlaylistInfo(SongPlaylists songPlaylists) {
        int flag = songPlaylistsMapper.changePlaylistInfo(songPlaylists);
        String playlistId = songPlaylists.getPlaylist_ID();
        String[] playlistTags = songPlaylists.getPlaylist_Tag();
        songPlaylistsMapper.deletePlaylistTag(playlistId);
        for (String playlistTag : playlistTags) {
            songPlaylistsMapper.insertPlaylistTag(playlistId, playlistTag);
        }
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
    public Boolean changeDailyList() {
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
    public List<PLTag> getAllPLTag() {
        return songPlaylistsMapper.getAllPLTag();
    }
}
