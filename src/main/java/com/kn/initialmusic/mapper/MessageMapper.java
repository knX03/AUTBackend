package com.kn.initialmusic.mapper;

import com.kn.initialmusic.pojo.Message;
import com.kn.initialmusic.pojo.User;
import com.kn.initialmusic.webSocket.pojo.FormatMess;
import com.kn.initialmusic.webSocket.pojo.MessUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper {


    /**
     * 获取当前用户的消息列表
     *
     * @param user_ID 用户ID
     * @return 消息列表
     */
    List<MessUser> getMessageUser(String user_ID);

    /**
     * 获取两个用户间的聊天消息列表
     *
     * @param fUser_ID 第一用户
     * @param sUser_ID 第二用户
     * @return 消息列表
     */
    List<FormatMess> getMessagesList(@Param("fUser_ID") String fUser_ID
            , @Param("sUser_ID") String sUser_ID);

}
