package com.kn.initialmusic.mapper;

import com.kn.initialmusic.pojo.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper {

    /**
     * 查询id是否重复
     *
     * @param id 消息ID
     * @return 查询的消息ID
     */
    String isRepeatedByID(String id);

    /**
     * 创建消息
     *
     * @param message 消息对象
     * @return 是否创建成功
     */

    int createMessage(Message message);

    /**
     * 用户与消息绑定
     *
     * @param message_id 消息ID
     * @param user_ID    用户ID
     * @return 是否绑定成功
     */

    int insertUserMessage(@Param("message_id") String message_id, @Param("user_ID") String user_ID);


    /**
     * 获取用户的消息
     *
     * @param user_ID 用户ID
     * @return 消息列表
     */
    List<Message> getMessages(String user_ID);

    /**
     * 删除指定用户的指定消息
     *
     * @param user_ID 用户ID
     * @param mess_ID 消息ID
     * @return 是否删除成功
     */
    int delMess(@Param("user_ID")String user_ID, @Param("message_id")String mess_ID);
}
