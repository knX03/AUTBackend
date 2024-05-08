package com.kn.initialmusic.service;

import com.kn.initialmusic.pojo.commentList;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SongCommentService {

    /**
     * @param ID 歌单或专辑或歌曲ID
     * @return commentList评论列表
     */
    List<commentList> getComments(String ID, String type);

    /**
     * @param user_ID
     * @param ID
     * @param comment
     * @return
     */
    boolean postComment(String user_ID, String ID, String comment , String type);
}
