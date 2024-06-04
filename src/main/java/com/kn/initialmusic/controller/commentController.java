package com.kn.initialmusic.controller;


import com.kn.initialmusic.pojo.Result;
import com.kn.initialmusic.pojo.User;
import com.kn.initialmusic.pojo.commentList;
import com.kn.initialmusic.service.SongCommentService;
import com.kn.initialmusic.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class commentController {


    @Autowired
    private SongCommentService songCommentService;

    //获取相应的评论
    @GetMapping("/getComments")
    public Result getComments(@RequestParam String ID, @RequestParam String type) {
        Result result = new Result();
        List<commentList> comments = songCommentService.getComments(ID, type);
        if (comments != null) {
            result.setCode(200);
            result.setData(comments);
        } else {
            result.setCode(500);
            result.setMsg("内部服务器错误！");
        }
        return result;
    }

    //发表评论
    @PostMapping("/postComment")
    public Result postComment(@RequestBody Map<String, String> commentForm) {
        User user = UserHolder.getUser();
        String user_ID = user.getUser_ID();
        String ID = commentForm.get("ID");
        String text = commentForm.get("text");
        String type = commentForm.get("type");
        Result result = new Result();
        boolean flag = songCommentService.postComment(user_ID, ID, text, type);
        if (flag) {
            result.setCode(200);
        } else {
            result.setCode(500);
            result.setMsg("内部服务器错误！");
        }
        UserHolder.removeUser();
        return result;
    }

}
