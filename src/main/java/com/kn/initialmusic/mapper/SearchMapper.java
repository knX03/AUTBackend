package com.kn.initialmusic.mapper;

import com.kn.initialmusic.pojo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SearchMapper {

    /**
     * 搜索功能
     *
     * @return
     */
    List<List<String>> searchString();

//    List<String> comprehensiveSearch(String searchValue);

    List<Song> songSearch(String searchValue);

    List<Singer> singerSearch(String searchValue);

    List<Album> albumSearch(String searchValue);

    List<SongPlaylists> spSearch(String searchValue);

    List<User> userSearch(String searchValue);
}
