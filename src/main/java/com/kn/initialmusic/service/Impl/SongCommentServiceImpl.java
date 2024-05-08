package com.kn.initialmusic.service.Impl;

import com.kn.initialmusic.mapper.SongCommentMapper;
import com.kn.initialmusic.pojo.commentList;
import com.kn.initialmusic.service.SongCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SongCommentServiceImpl implements SongCommentService {

    @Autowired
    private SongCommentMapper songCommentMapper;

    @Override
    public List<commentList> getComments(String ID, String type) {
        List<commentList> songComments = new ArrayList<>();
        switch (type) {
            case "song":
                songComments = songCommentMapper.getSongComments(ID);
                break;
            case "album":
                songComments = songCommentMapper.getAlbumComments(ID);
                break;
            case "playlist":
                songComments = songCommentMapper.getPlaylistComments(ID);
                break;
        }
        return songComments;
    }

    @Override
    public boolean postComment(String user_ID, String ID, String comment, String type) {
        int num = switch (type) {
            case "song" -> songCommentMapper.postSongComment(user_ID, ID, comment);
            case "album" -> songCommentMapper.postAlbumComment(user_ID, ID, comment);
            case "playlist" -> songCommentMapper.postPlaylistComment(user_ID, ID, comment);
            default -> 0;
        };
        return num > 0;
    }
}
