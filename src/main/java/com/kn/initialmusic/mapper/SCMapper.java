package com.kn.initialmusic.mapper;

import com.kn.initialmusic.pojo.commentList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SCMapper {

    /**
     * @param ID 歌单ID
     * @return commentList
     */
    List<commentList> getPlaylistComments(String ID);

    /**
     * @param ID 歌曲ID
     * @return commentList
     */
    List<commentList> getSongComments(String ID);

    /**
     * @param ID 专辑ID
     * @return commentList
     */
    List<commentList> getAlbumComments(String ID);


    /**
     * @param user_ID
     * @param ID
     * @param comment
     * @return
     */
    int postPlaylistComment(@Param("user_ID") String user_ID,
                            @Param("ID") String ID,
                            @Param("comment") String comment);

    /**
     * @param user_ID
     * @param ID
     * @param comment
     * @return
     */
    int postSongComment(@Param("user_ID") String user_ID,
                        @Param("ID") String ID,
                        @Param("comment") String comment);

    /**
     * @param user_ID
     * @param ID
     * @param comment
     * @return
     */
    int postAlbumComment(@Param("user_ID") String user_ID,
                         @Param("ID") String ID,
                         @Param("comment") String comment);
}
