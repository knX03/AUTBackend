package com.kn.initialmusic.service.Impl;

import com.kn.initialmusic.mapper.SCMapper;
import com.kn.initialmusic.pojo.commentList;
import com.kn.initialmusic.service.SongCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SongCommentServiceImpl implements SongCommentService {

    @Autowired
    private SCMapper SCMapper;

    @Override
    public List<commentList> getComments(String ID, String type) {
        List<commentList> songComments = new ArrayList<>();
        switch (type) {
            case "song":
                songComments = SCMapper.getSongComments(ID);
                break;
            case "album":
                songComments = SCMapper.getAlbumComments(ID);
                break;
            case "playlist":
                songComments = SCMapper.getPlaylistComments(ID);
                break;
        }
        return songComments;
    }

    @Override
    public boolean postComment(String user_ID, String ID, String comment, String type) {
        int num = switch (type) {
            case "song" -> SCMapper.postSongComment(user_ID, ID, comment);
            case "album" -> SCMapper.postAlbumComment(user_ID, ID, comment);
            case "playlist" -> SCMapper.postPlaylistComment(user_ID, ID, comment);
            default -> 0;
        };
        return num > 0;
    }
}
