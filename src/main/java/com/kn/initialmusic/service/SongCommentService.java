package com.kn.initialmusic.service;

import com.kn.initialmusic.pojo.commentList;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SongCommentService {

    /**
     * 查询评论
     *
     * @param ID 歌单或专辑或歌曲ID
     * @return commentList评论列表
     */
    List<commentList> getComments(String ID, String type);


    /**
     * 发表评论
     *
     * @param user_ID 用户ID
     * @param ID      需发表评论的ID（例如是歌曲的ID，歌单的ID）
     * @param comment 评论内容
     * @param type    ID类型
     * @return 是否发表成功
     */
    boolean postComment(String user_ID, String ID, String comment, String type);
}
